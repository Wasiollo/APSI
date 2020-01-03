import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../model/user.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AdminService} from '../admin.service';
import {OK} from 'http-status-codes';

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

    constructor(private formBuilder: FormBuilder, private router: Router, private adminService: AdminService) {
    }

    ngOnInit() {

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
        this.adminService.updateUser(editedUser)
            .subscribe(
                data => {
                    if (data.status === OK) {
                        alert('User updated successfully.');
                        this.userChange.emit(editedUser);
                        this.user = editedUser;
                    } else {
                        alert(data.message);
                    }
                },
                error => {
                    alert(error);
                });
    }

    generatePassword(): void {
        const newPassword = prompt('Write new password for this user');
        if (newPassword !== null && newPassword !== undefined && newPassword !== '') {
            this.user.password = newPassword;
            this.adminService.updateUserPassword(this.user).subscribe(
                data => {
                    if (data.status === OK) {
                        alert('Password changed successfully');
                    }
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
                        alert(data.message);
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
                        alert(data.message);
                    }
                }
            );
    }

    isUserAdmin(): boolean {
        return this.user.userRoles.filter(r => r.roleName = 'ROLE_ADMIN').length > 0
    }

}
