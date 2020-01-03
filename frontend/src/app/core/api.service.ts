import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import { map } from 'rxjs/operators';

@Injectable()
export class ApiService {

    constructor(private http: HttpClient) {
    }

    post(postUrl, postData): Observable<ApiResponse> {
        return this.http.post(postUrl, postData, {observe: "response"}).pipe(map( res => new ApiResponse(res)));
    }

    get(getUrl): Observable<ApiResponse> {
        return this.http.get(getUrl, {observe: "response"}).pipe(map( res => new ApiResponse(res)));
    }

    put(putUrl, putData): Observable<ApiResponse> {
        return this.http.put(putUrl, putData, {observe: "response"}).pipe(map( res => new ApiResponse(res)));
    }

    delete(deleteUrl): Observable<ApiResponse> {
        return this.http.delete(deleteUrl, {observe: "response"}).pipe(map( res => new ApiResponse(res)));
    }

}
