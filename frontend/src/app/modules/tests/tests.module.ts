import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TestsListComponent} from './tests-list/tests-list.component';
import {routing} from "./tests.routing";
import {ReactiveFormsModule} from "@angular/forms";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {TestService} from "./service/test.service";
import { TestsDetailsComponent } from './tests-details/tests-details.component';
import { AddTestComponent } from './add-test/add-test.component';

@NgModule({
    declarations: [TestsListComponent, TestsDetailsComponent, AddTestComponent],
    imports: [
        CommonModule,
        routing,
        ReactiveFormsModule,
        NgbModule.forRoot(),
    ],
    exports: [TestsListComponent],
    providers: [TestService],
    entryComponents: [AddTestComponent]
})
export class TestsModule {
}
