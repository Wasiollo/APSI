import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {Injectable} from '@angular/core';
import {catchError, map} from 'rxjs/operators';
import {Router} from '@angular/router';
import {throwError} from 'rxjs';
import {UNAUTHORIZED} from 'http-status-codes';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = window.localStorage.getItem('token');
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
    }
    return next.handle(request)
      .pipe(map(res => res),
      catchError(error => {
        if (error instanceof HttpErrorResponse) {
          if ((<HttpErrorResponse>error).status === UNAUTHORIZED) {
            return this.handle401Error(error);
          }
        }
          return throwError(error);

      }));
  }

  handle401Error(error): Observable<HttpEvent<any>> {
    window.localStorage.removeItem('token');
    this.router.navigate(['authenticate']);
    return throwError(error);
  }

}
