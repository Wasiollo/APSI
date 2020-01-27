import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TestsListComponent} from './tests-list/tests-list.component';
import {routing} from "./tests.routing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {TestService} from "./service/test.service";
import { TestsDetailsComponent } from './tests-details/tests-details.component';
import { AddTestComponent } from './add-test/add-test.component';
import {MaterialFileInputModule} from "ngx-material-file-input";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";

@NgModule({
    declarations: [TestsListComponent, TestsDetailsComponent, AddTestComponent],
    imports: [
        CommonModule,
        routing,
        ReactiveFormsModule,
        NgbModule.forRoot(),
        FormsModule,
        MaterialFileInputModule,
        MatIconModule,
        MatFormFieldModule,
    ],
    exports: [TestsListComponent],
    providers: [TestService],
    entryComponents: [AddTestComponent]
})
export class TestsModule {
}
