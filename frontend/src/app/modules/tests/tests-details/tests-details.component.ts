import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TestService} from "../service/test.service";
import {Test, TestDocument} from "../model/test.model";
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
        if (this.router.getCurrentNavigation().extras.state !== undefined && this.router.getCurrentNavigation().extras.state !== null) {
            this.testId = this.router.getCurrentNavigation().extras.state.testId;
        } else {
            this.testId = null;
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
        this.testService.uploadFiles(this.testId, this.filesToUpload).subscribe(data => {
            if (data.status === OK) {
                for (let file of data.result) {
                    this.currentTest.documents.push(file);
                }
                this.toastr.success("File uploaded successfully");
                this.filesToUpload = [];
            } else {
                this.toastr.error("Error occured uploading files")
            }
        })
    }

    deleteDocument(doc: TestDocument) {
        if (confirm('Are you sure to delete ' + doc.filename + ' ?')) {
            this.testService.deleteTestDocument(this.currentTest.id, doc.id)
                .subscribe(() => {
                    this.currentTest.documents = this.currentTest.documents.filter(d => d !== doc);
                });
        }
    }

    downloadDoc(doc: TestDocument) {
        this.testService.downloadDocumentContent(this.currentTest.id, doc.id).subscribe(
            data => {
                if (data.status === OK) {
                    let a = document.createElement('a');
                    let e = document.createEvent('MouseEvents');
                    a.download = doc.filename;
                    a.href = data.result.data;
                    e.initEvent('click', true, false);
                    a.dispatchEvent(e);
                } else {
                    this.toastr.error("Error occurred downloading file");
                }
            }
        );
    }
}
