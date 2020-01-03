import {ChangeDetectorRef, Component, Input, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
    @Input() modalReference;
    toLogin = true;
    @Input() navigationReason;
    @ViewChild('modalTitle', { static: false }) modalTitleDiv;

    constructor(public activeModal: NgbActiveModal, private cdRef: ChangeDetectorRef) {
    }

    switchLoginRegister() {
        this.toLogin = !this.toLogin;
    }

    ngOnInit(): void {
    }


}
