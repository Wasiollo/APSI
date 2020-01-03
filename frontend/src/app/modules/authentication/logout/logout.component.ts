import {Component, OnInit} from '@angular/core';
import {Router, NavigationExtras} from '@angular/router';
import {AuthenticationService} from '../authentication.service';

const navigationExtras: NavigationExtras = {
    state:{
        authenticationReason: 'You have successfully logged out.'
    }
}

@Component({
    selector: 'app-logout',
    template: ''
})
export class LogoutComponent implements OnInit {

    constructor(private router: Router, private authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.authenticationService.logoutUser();
        this.router.navigate(['authenticate'], navigationExtras);
    }

}
