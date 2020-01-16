import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TestsListComponent} from './tests-list/tests-list.component';
import {routing} from "./tests.routing";
import {ReactiveFormsModule} from "@angular/forms";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
    declarations: [TestsListComponent],
    imports: [
        CommonModule,
        routing,
        ReactiveFormsModule,
        NgbModule.forRoot(),
    ],
    exports: [TestsListComponent]
})
export class TestsModule {
}
