import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../model/user.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AdminService} from '../admin.service';
import {OK} from 'http-status-codes';

import {ToastrService} from 'ngx-toastr';
import {UserRole} from "../../../model/user.role.model";

@Component({
    selector: 'app-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit, OnChanges {
    @Input() userId;
    @Output() userChange = new EventEmitter<User>();
    user: User;
    editForm: FormGroup;
    allRoles: UserRole[];

    constructor(private formBuilder: FormBuilder, private router: Router, private adminService: AdminService, private toastr: ToastrService) {
    }

    ngOnInit() {

        this.adminService.getAllRoles().subscribe(data => this.allRoles = data.result);

        this.editForm = this.formBuilder.group({
            id: [''],
            username: ['', Validators.required],
            email: ['', Validators.required],
            userRoles: [],
            points: [],
            password: []
        });
        this.adminService.getUserById(+this.userId)
            .subscribe(data => {
                this.user = data.result;
                this.editForm.setValue(data.result);
            });
    }

    ngOnChanges(changes: SimpleChanges) {
        const userId: SimpleChange = changes.userId;
        this.adminService.getUserById(+userId.currentValue)
            .subscribe(data => {
                this.user = data.result;
                this.editForm.setValue(data.result);
            });
    }

    onSubmit() {
        const editedUser = this.editForm.value;
        editedUser.password = '';
        editedUser.userRoles = this.user.userRoles;
        this.adminService.updateUser(editedUser)
            .subscribe(
                data => {
                    if (data.status === OK) {
                        this.toastr.success('User updated successfully.');
                        this.userChange.emit(editedUser);
                        this.user = editedUser;
                    } else {
                        this.toastr.error(data.message);
                    }
                },
                error => {
                    this.toastr.error(error);
                });
    }

    generatePassword(): void {
        const newPassword = prompt('Write new password for this user');
        if (newPassword !== null && newPassword !== undefined && newPassword !== '') {
            this.user.password = newPassword;
            this.adminService.updateUserPassword(this.user).subscribe(
                data => {
                    if (data.status === OK) {
                        this.toastr.success('Password changed successfully');
                    } else {
                        this.toastr.error(data.message);
                    }
                },(err) => {
                    this.toastr.error(err.error.error);
                }
            );
        }
    }

    grantAdmin(): void {
        this.adminService.grantAdmin(this.user.id)
            .subscribe(
                data => {
                    if (data.status === OK) {
                        this.user.userRoles = data.result.userRoles;
                        this.userChange.emit(this.user);
                    } else {
                        this.toastr.error(data.message);
                    }
                }
            );
    }

    revokeAdmin(): void {
        this.adminService.revokeAdmin(this.user.id)
            .subscribe(
                data => {
                    if (data.status === OK) {
                        this.user.userRoles = data.result.userRoles;
                        this.userChange.emit(this.user);
                    } else {
                        this.toastr.error(data.message);
                    }
                }
            );
    }

    isUserAdmin(): boolean {
        if (this.user === undefined) {
            return false;
        }
        return this.user.userRoles.some(r => r.roleName === 'ROLE_ADMIN');
    }

    isRoleChecked(role: UserRole): boolean {
        if (this.user === undefined) {
            return false;
        }
        return this.user.userRoles.some(r => r.roleName === role.roleName);
    }

    changeRoleStatus(roleName: string, $event: Event) {
        if(this.userHasRole(roleName)){
            this.user.userRoles = this.user.userRoles.filter(r => r.roleName !== roleName);
        } else {
            this.user.userRoles.push(this.allRoles.find(r=> r.roleName === roleName));
        }
    }

    userHasRole(roleName: string): boolean {
        return this.user.userRoles.some(r => r.roleName === roleName)
    }
}
