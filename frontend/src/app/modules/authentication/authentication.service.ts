import {Injectable, Output, EventEmitter} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiService} from '../../core/api.service';
import {ApiResponse} from '../../model/api.response';
import {User} from '../../model/user.model';
import {environment} from '../../../environments/environment';

const baseUrl = environment.APIEndpoint;

@Injectable()
export class AuthenticationService {

    loggedUser: User;

    @Output() newTokenEmitter = new EventEmitter();
    @Output() userLogoutEmitter = new EventEmitter();

    constructor(private apiService: ApiService) {
    }

    authUrl = baseUrl + '/auth';
    signUpUrl = this.authUrl + '/signup';
    authenticateUrl = this.authUrl + '/authenticate';
    checkAdminRoleUrl = this.authUrl + '/checkAdminRole';
    renewTokenUrl = this.authUrl + '/renew';

    login(loginPayload): Observable<ApiResponse> {
        return this.apiService.post(this.authenticateUrl, loginPayload);
    }

    setLoggedUser(loggedUser) {
        this.loggedUser = loggedUser;
        window.localStorage.setItem('loggedUser', JSON.stringify(loggedUser));
    }

    getLoggedUserId() {
        if (this.loggedUser === undefined) {
            this.loggedUser = JSON.parse(window.localStorage.getItem('loggedUser'));
        }
        return this.loggedUser.id;
    }

    registerUser(user: User): Observable<ApiResponse> {
        return this.apiService.post(this.signUpUrl, user);
    }

    checkAdminRole(token: string): Observable<ApiResponse> {
        return this.apiService.get(this.checkAdminRoleUrl + "/" + token);
    }

    renewToken(token: string): Observable<ApiResponse> {
        return this.apiService.post(this.renewTokenUrl, token);
    }

    emitNewToken(){
        this.newTokenEmitter.emit();
    }

    logoutUser(){
        window.localStorage.removeItem('token');
        window.localStorage.removeItem('loggedUser');
        this.userLogoutEmitter.emit();
    }
}
