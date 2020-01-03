import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../authentication.service';
import {CREATED} from "http-status-codes";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    @Input() modalRef;

    constructor(private formBuilder: FormBuilder, private router: Router, private authenticationService: AuthenticationService) {
    }

    registerForm: FormGroup;
    registerError: JSON;

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            id: [],
            username: ['', Validators.required],
            email: ['', Validators.required],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required],
        }, {validator: this.checkPasswords});
    }

    onSubmit() {
        if (this.registerForm.invalid) {
            return;
        }
        this.authenticationService.registerUser(this.registerForm.value)
            .subscribe(data => {
                if (data.status === CREATED) {
                    this.modalRef.componentInstance.switchLoginRegister();
                    alert('User registered: ' + data.result.username);
                } else {
                    console.log("Bad Status")
                }
            },(err) => {
                this.registerError = err.error;
            });
    }

    checkPasswords(group: FormGroup) {
        const pass = group.controls.password.value;
        const confirmPass = group.controls.confirmPassword.value;

        return pass === confirmPass ? null : {passwordsNotSame: true};
    }
}

