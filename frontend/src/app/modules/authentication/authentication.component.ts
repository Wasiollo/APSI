import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalComponent} from './modal/modal.component';
import {Router} from '@angular/router';

@Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.scss']
})
export class AuthenticationComponent implements OnInit {

    navigationState: any;

    constructor(private modalService: NgbModal, private router: Router) {
        this.navigationState = this.router.getCurrentNavigation().extras.state;
    }

    ngOnInit() {
        const modalRef = this.modalService.open(ModalComponent, {
            backdrop: 'static',
            keyboard: false
        });
        modalRef.componentInstance.modalReference = modalRef;
        modalRef.componentInstance.navigationReason = this.navigationState;
    }

}
