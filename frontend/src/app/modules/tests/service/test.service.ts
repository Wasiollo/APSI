import {EventEmitter, Injectable, Output} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from "../../../../environments/environment";
import {ApiService} from "../../../core/api.service";
import {ApiResponse} from "../../../model/api.response";
import {Test} from "../model/test.model";

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

    getTestStatuses(): Observable<ApiResponse>  {
        return this.apiService.get(this.testStatusesUrl);
    }

    getTest(testId: number): Observable<ApiResponse>  {
        return this.apiService.get(this.testsUrl + '/' + testId);
    }

    addedTest(test: Test) {
        this.testAdded.emit(test);
    }

    getUnAcceptedTests(): Observable<ApiResponse> {
        return this.apiService.get(this.unacceptedTestsUrl);
    }

    acceptTest(testId: number) {
        return this.apiService.post(this.acceptTestUrl + '/' + testId, null);
    }
}
