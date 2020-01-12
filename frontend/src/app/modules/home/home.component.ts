import { Component, OnInit } from '@angular/core';
import {AdminService} from '../admin/admin.service';
import {AuthenticationService} from '../authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private adminService: AdminService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    const token = window.localStorage.getItem('token');
    if (token === '' || token === undefined || token === null) {
      this.router.navigate(['authenticate']);
    }
  }

}
