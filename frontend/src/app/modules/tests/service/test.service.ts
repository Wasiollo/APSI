import {EventEmitter, Injectable, Output} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from "../../../../environments/environment";
import {ApiService} from "../../../core/api.service";
import {ApiResponse} from "../../../model/api.response";
import {Test, TestDocument} from "../model/test.model";

const baseUrl = environment.APIEndpoint;

@Injectable()
export class TestService {

    @Output() testAdded: EventEmitter<any> = new EventEmitter();

    constructor(private apiService: ApiService) {
    }

    testsUrl = baseUrl + '/tests';
    testsAllUrl = this.testsUrl + '/all';
    testStatusesUrl = this.testsUrl + '/statuses';
    unacceptedTestsUrl = this.testsUrl + '/unaccepted';
    acceptTestUrl = this.testsUrl + '/accept';
    deleteTestUrl = this.testsUrl;

    getTests(): Observable<ApiResponse> {
        return this.apiService.get(this.testsUrl);
    }

    getAllTests(): Observable<ApiResponse> {
        return this.apiService.get(this.testsAllUrl);
    }

    createTest(test: Test): Observable<ApiResponse> {
        return this.apiService.post(this.testsUrl, test);
    }

    setTestStatus(test: Test): Observable<ApiResponse> {
        return this.apiService.put(this.testsUrl, test);
    }

    getTestStatuses(): Observable<ApiResponse> {
        return this.apiService.get(this.testStatusesUrl);
    }

    getTest(testId: number): Observable<ApiResponse> {
        return this.apiService.get(this.testsUrl + '/' + testId);
    }

    addedTest(test: Test) {
        this.testAdded.emit(test);
    }

    getUnAcceptedTests(): Observable<ApiResponse> {
        return this.apiService.get(this.unacceptedTestsUrl);
    }

    acceptTest(testId: number): Observable<ApiResponse> {
        return this.apiService.post(this.acceptTestUrl + '/' + testId, null);
    }

    deleteTest(testId: number): Observable<ApiResponse> {
        return this.apiService.delete(this.deleteTestUrl + '/' + testId);
    }

    uploadFiles(testId: number, filesToUpload: TestDocument[]): Observable<ApiResponse> {
        return this.apiService.post(this.testsUrl + '/' + testId + '/documents', filesToUpload);
    }

    deleteTestDocument(testId: number, id: number): Observable<ApiResponse> {
        return this.apiService.delete(this.testsUrl + '/' + testId + '/documents/' + id);
    }

    downloadDocumentContent(testId: number, documentId: number): Observable<ApiResponse> {
        return this.apiService.get(this.testsUrl + '/' + testId + '/documents/' + documentId);
    }
}
