import {Component, OnInit} from '@angular/core';
import {Router, NavigationExtras} from '@angular/router';
import {AuthenticationService} from '../authentication.service';

const navigationExtras: NavigationExtras = {
    state:{
        authenticationReason: 'Your session has expired. You have to login to use application.'
    }
}

@Component({
    selector: 'app-expired-token',
    template: ''
})
export class ExpiredTokenComponent implements OnInit {

    constructor(private router: Router, private authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.authenticationService.logoutUser();
        this.router.navigate(['authenticate'], navigationExtras);
    }

}
