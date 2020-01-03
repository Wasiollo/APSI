import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../model/user.model';
import {AdminService} from '../admin.service';
import {AuthenticationService} from '../../authentication/authentication.service';

@Component({
    selector: 'app-list-user',
    templateUrl: './list-user.component.html',
    styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit {

    users: User[];
    userIdToEdit: String;
    showEditUser: Boolean;
    page: number;
    pageSize: number;

    constructor(private router: Router, private adminService: AdminService, private authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.page = 1;
        this.pageSize = 9;
        const token: string = window.localStorage.getItem('token');
        this.authenticationService.checkAdminRole(token).toPromise().then(res => {
            const adminLogged: boolean = res.result;
            this.adminService.setAdmin(adminLogged);

            if (adminLogged) {
                this.getUsers();
            } else {
                this.router.navigate(['home']);
            }
        });
    }

    getUsers() {
        this.adminService.getUsers()
            .subscribe(data => {
                this.users = data.result;
            });
    }

    updateUserListOnUserChange(updatedUser: User) {
        const index = this.users.findIndex((user) => (user.id === updatedUser.id));
        this.users[index] = updatedUser;
    }

    deleteUser(user: User): void {
        if (confirm('Are you sure to delete ' + user.username + ' ?')) {
            this.adminService.deleteUser(user.id)
                .subscribe(() => {
                    this.users = this.users.filter(u => u !== user);
                });
        }
    }

    editUser(user: User): void {
        this.userIdToEdit = user.id.toString();
        this.showEditUser = true;
    }

    isUserAdmin(user: User): boolean {
        return user.userRoles.filter(r => r.roleName = 'ROLE_ADMIN').length > 0
    }
}
