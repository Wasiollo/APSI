import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TestService} from "../service/test.service";
import {CREATED} from "http-status-codes";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-add-test',
    templateUrl: './add-test.component.html',
    styleUrls: ['./add-test.component.scss']
})
export class AddTestComponent implements OnInit {

    @Input() modalRef;

    constructor(private formBuilder: FormBuilder, private testService: TestService, private toastr: ToastrService) {
    }

    testForm: FormGroup;

    ngOnInit() {
        this.buildTestForm();
    }

    private buildTestForm() {
        this.testForm = this.formBuilder.group(
            {
                name: ['', Validators.required],
                description: ['', Validators.required]
            }
        );
    }

    onSubmit() {
        if (this.testForm.invalid) {
            return;
        }
        this.addTest();
    }

    private addTest() {
        const testData = this.testForm.value;

        this.testService.createTest(testData)
            .subscribe(data => {
                if (data.status === CREATED) {
                    this.modalRef.close();
                    this.testService.addedTest(data.result);
                } else {
                    this.toastr.error(data.result);
                }
            });
    }
}
