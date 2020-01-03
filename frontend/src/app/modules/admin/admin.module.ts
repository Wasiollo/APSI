import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {routing} from './admin.routing';
import {ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {EditUserComponent} from './edit-user/edit-user.component';
import {ListUserComponent} from './list-user/list-user.component';

@NgModule({
    imports: [
        CommonModule,
        routing,
        ReactiveFormsModule,
        NgbModule.forRoot(),
    ],
    declarations: [
        EditUserComponent,
        ListUserComponent,
    ],
    exports: [
        EditUserComponent,
        ListUserComponent
    ],

})
export class AdminModule {
}
