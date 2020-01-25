import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TestService} from "../service/test.service";
import {TestDocument, Test} from "../model/test.model";
import {Validators} from "@angular/forms";
import {OK} from "http-status-codes";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-tests-details',
    templateUrl: './tests-details.component.html',
    styleUrls: ['./tests-details.component.scss']
})
export class TestsDetailsComponent implements OnInit {

    currentTest: Test;
    testId: number;
    files: File[] = [];
    filesToUpload: TestDocument[];

    constructor(private router: Router, private testService: TestService, private toastr: ToastrService) {
        if (this.router.getCurrentNavigation !== undefined){
            this.testId = this.router.getCurrentNavigation().extras.state.testId;
        } else {
            this.testId = undefined;
        }
    }

    ngOnInit() {
        if (this.testId === null || this.testId === undefined) {
            this.router.navigate(['tests']);
        }
        this.currentTest = new Test();
        this.filesToUpload = [];
        this.testService.getTest(this.testId).subscribe(data => {
            this.currentTest = data.result;
        })
    }

    returnToTestsList() {
        this.router.navigate(['tests'])
    }

    toBase64 = file => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });

    public async uploadFile(event: any) {
        for (let i = 0; i < event.target.files.length; i++) {
            const file = event.target.files[i];
            const baseFile = await this.toBase64(file);
            let doc = new TestDocument();
            doc.filename = file.name;
            doc.data = baseFile;
            console.log(doc);
            this.filesToUpload.push(doc);
        }
    }

    saveUploadedFiles() {
        this.testService.uploadFiles(this.testId, this.filesToUpload).subscribe(data =>{
            if (data.status === OK){
                this.currentTest.documents.concat(this.filesToUpload);
                this.toastr.success("File uploaded successfully");
                this.filesToUpload = [];
            } else {
                this.toastr.error("Error occured uploading files")
            }
        })
    }
}
