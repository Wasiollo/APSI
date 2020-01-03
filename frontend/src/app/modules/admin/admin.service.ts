import {EventEmitter, Injectable, Output} from '@angular/core';
import {ApiService} from '../../core/api.service';
import {Observable} from 'rxjs';
import {ApiResponse} from '../../model/api.response';
import {User} from '../../model/user.model';
import {environment} from '../../../environments/environment';
import {HttpResponse} from "@angular/common/http";

const baseUrl = environment.APIEndpoint;

@Injectable()
export class AdminService {

    isAdmin = false;

    @Output() change: EventEmitter<boolean> = new EventEmitter();

    constructor(private apiService: ApiService) {
    }

    usersUrl = baseUrl + '/user';
    listUsersUrl = this.usersUrl + '/list';
    grantAdminUrl = this.usersUrl + '/grant-admin?userId=';
    revokeAdminUrl = this.usersUrl + '/revoke-admin?userId=';
    updateUserPasswordUrl = this.usersUrl + '/update_password';

    setAdmin(isAdmin) {
        this.isAdmin = isAdmin;
        this.change.emit(this.isAdmin);
    }

    getAdmin() {
        return this.isAdmin;
    }

    getUsers(): Observable<ApiResponse> {
        return this.apiService.get(this.listUsersUrl);
    }

    getUserById(id: number): Observable<ApiResponse> {
        return this.apiService.get(this.usersUrl + '/' + id);
    }

    updateUser(user: User): Observable<ApiResponse> {
        return this.apiService.put(this.usersUrl, user);
    }

    updateUserPassword(user: User): Observable<ApiResponse> {
        return this.apiService.put(this.updateUserPasswordUrl, user);
    }

    deleteUser(id: number): Observable<ApiResponse> {
        return this.apiService.delete(this.usersUrl + '/' + id);
    }

    grantAdmin(id: number): Observable<ApiResponse> {
        return this.apiService.put(this.grantAdminUrl + id, null);
    }

    revokeAdmin(id: number): Observable<ApiResponse> {
        return this.apiService.put(this.revokeAdminUrl + id, null);
    }

}
