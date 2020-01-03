import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';

import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {routing} from './authentication.routing';
import {ReactiveFormsModule} from '@angular/forms';
import {AuthenticationComponent} from './authentication.component';
import {ModalComponent} from './modal/modal.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LogoutComponent} from './logout/logout.component';
import {ExpiredTokenComponent} from './expired-token/expired-token.component';

@NgModule({
    imports: [
        CommonModule,
        routing,
        ReactiveFormsModule,
        NgbModule.forRoot(),
    ],
    declarations: [
        LoginComponent,
        RegisterComponent,
        AuthenticationComponent,
        ModalComponent,
        LogoutComponent,
        ExpiredTokenComponent
    ],
    exports: [
        LoginComponent,
        RegisterComponent,
        AuthenticationComponent
    ],
    entryComponents: [
        ModalComponent,
    ]

})
export class AuthenticationModule {
}
