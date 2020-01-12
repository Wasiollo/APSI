import {Injectable, Output, EventEmitter} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiService} from '../../core/api.service';
import {ApiResponse} from '../../model/api.response';
import {User} from '../../model/user.model';
import {environment} from '../../../environments/environment';

const baseUrl = environment.APIEndpoint;

@Injectable()
export class AuthenticationService {

    public ROLE_ADMIN = "ROLE_ADMIN";
    public ROLE_MOD = "ROLE_MOD";
    public ROLE_EMPL = "ROLE_EMPL";
    public ROLE_USER = "ROLE_USER";

    loggedUser: User;

    @Output() newTokenEmitter = new EventEmitter();
    @Output() userLogoutEmitter = new EventEmitter();

    constructor(private apiService: ApiService) {
    }

    authUrl = baseUrl + '/auth';
    signUpUrl = this.authUrl + '/signup';
    authenticateUrl = this.authUrl + '/authenticate';
    renewTokenUrl = this.authUrl + '/renew';
    currentUserUrl = this.authUrl + '/current';

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

    hasRole(role: string): boolean {
        if(this.loggedUser === undefined){
            this.loggedUser = JSON.parse(window.localStorage.getItem('loggedUser'));
        }
        if (this.loggedUser === null || this.loggedUser === undefined){
            return false;
        }
        return this.loggedUser.userRoles.some(r => r.roleName === role);
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

    getCurrentUser(){
        return this.apiService.get(this.currentUserUrl);
    }
}
