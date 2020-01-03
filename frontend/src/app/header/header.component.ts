import {Component, Input, ViewChild,  OnInit} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {CounterComponent} from "../core/counter.component";
import {Router} from "@angular/router";
import {AuthenticationService} from "../modules/authentication/authentication.service";
import {OK} from 'http-status-codes';

const helper = new JwtHelperService();

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

    jwtHelper = helper;
    collapsed = true;


    @ViewChild('counter', {read:CounterComponent, static: true}) counter: CounterComponent;

    constructor(private router: Router, private authenticationService: AuthenticationService ) {
    }

    ngOnInit() {
        if (this.tokenGetter() !== null){
            this.runExpirationTimer();
        }
        this.authenticationService.newTokenEmitter.subscribe(d => {
            this.counter.stop();
            this.runExpirationTimer();
        });
        this.authenticationService.userLogoutEmitter.subscribe(d => {
            this.counter.stop();
        })
    }

    runExpirationTimer() {
        this.counter.startAt = this.getTimeToExpire();
        this.counter.counterState.subscribe((msg) => {
            if (msg === 'COMPLETE') {
                this.router.navigate(['authenticate/expired-token'])
            }
        });
        this.counter.start();
    }

    tokenGetter(): string {
        return window.localStorage.getItem('token');
    }

    getTimeToExpire(): number {
        const millisecondsToExpire = this.jwtHelper.getTokenExpirationDate(this.tokenGetter()).getTime() - new Date().getTime();
        return parseInt(String(millisecondsToExpire/1000));
    }

    toggleCollapsed(): void {
        this.collapsed = !this.collapsed;
    }

    extendSession(): void {
        this.authenticationService.renewToken(this.tokenGetter()).subscribe(data => {
            if(data.status === OK){
                window.localStorage.setItem('token', data.result.token);
                this.counter.stop();
                this.runExpirationTimer();
            }
        });
    }

}
