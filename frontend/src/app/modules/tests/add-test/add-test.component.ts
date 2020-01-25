import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
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
    testStepNumber = 1;
    public maxFileSize = 20971520;

    ngOnInit() {
        this.buildTestForm();
    }

    private buildTestForm() {
        this.testForm = this.formBuilder.group(
            {
                name: ['', Validators.required],
                description: ['', Validators.required],
                specifications: new FormArray([]),
                documents: new FormArray([])
            }
        );
    }

    get specs(): FormArray {
        return <FormArray>this.testForm.controls.specifications;
    }

    get docs(): FormArray {
        return <FormArray>this.testForm.controls.documents;
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
                    this.toastr.success("Test sent to verification");
                    this.testService.addedTest(data.result);
                } else {
                    this.toastr.error(data.result);
                }
            });
    }

    deleteStep(testStep: AbstractControl) {
        this.specs.removeAt(this.specs.value.findIndex(spec => spec.number === testStep.value.number));
    }

    addTestStep() {
        this.specs.push(this.formBuilder.group({
            number: [this.testStepNumber, Validators.required],
            userAction: ['', Validators.required],
            systemReaction: ['', Validators.required]
        }));
        ++this.testStepNumber;
    }

    toBase64 = file => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });

    public async uploadFile(event: any) {
        // Iterate through all uploaded files.
        for (let i = 0; i < event.target.files.length; i++) {
            const file = event.target.files[i];
            const baseFile = await this.toBase64(file);
            this.docs.push(this.formBuilder.group({
                fileName: [file.name, Validators.required],
                data: [baseFile, Validators.required]
            }))
        }
    }
}
