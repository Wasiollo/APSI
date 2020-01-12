import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiService} from '../../../core/api.service';
import {AdminService} from '../../admin/admin.service';
import {AuthenticationService} from '../authentication.service';
import {User} from '../../../model/user.model';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    @Input() modalRef;
    @Input() navigationReason;

    loginForm: FormGroup;
    invalidLogin = false; //TODO rename to invalidCredentials

    constructor(private formBuilder: FormBuilder, private router: Router, private adminService: AdminService, private authenticationService: AuthenticationService) {
    }

    onSubmit() {
        if (this.loginForm.invalid) {
            return;
        }
        const loginPayload = {
            username: this.loginForm.controls.username.value,
            password: this.loginForm.controls.password.value
        };
        this.authenticationService.login(loginPayload).subscribe(data => {
            if (data.status === 200) {
                window.localStorage.setItem('token', data.result.token);
                this.modalRef.close();
                this.authenticationService.getCurrentUser().subscribe(
                    res => {
                        this.authenticationService.setLoggedUser(res.result);
                        this.authenticationService.emitNewToken();
                        this.router.navigate(['home']);
                    }
                );
            } else {
                this.invalidLogin = true;
            }
        }, (err) => {this.invalidLogin = true;});
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.compose([Validators.required])],
            password: ['', Validators.required]
        });
    }

}
