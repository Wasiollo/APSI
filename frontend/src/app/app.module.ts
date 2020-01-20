import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ApiService} from './core/api.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {routing} from './app.routing';
import {TokenInterceptor} from './core/interceptor';
import {HeaderComponent} from './header/header.component';
import {CounterComponent} from './core/counter.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AdminService} from './modules/admin/admin.service';
import {HIGHLIGHT_OPTIONS} from 'ngx-highlightjs';

import sql from 'highlight.js/lib/languages/sql';
import {AuthenticationService} from './modules/authentication/authentication.service';
import {AngularFontAwesomeModule} from 'angular-font-awesome';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';

import {ToastrModule} from 'ngx-toastr';

export function hljsLanguages() {
    return [
        {name: 'sql', func: sql}
    ];
}

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        CounterComponent
    ],
    imports: [
        CommonModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        BrowserAnimationsModule,
        routing,
        ReactiveFormsModule,
        AngularFontAwesomeModule,
        HttpClientModule,
        NgbModule.forRoot()
    ],
    exports: [
        HeaderComponent,
        CounterComponent
    ],
    providers: [ApiService, {
        provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi: true
    }, AdminService,
        {
            provide: HIGHLIGHT_OPTIONS,
            useValue: {
                languages: hljsLanguages
            }
        },
        AuthenticationService],

    bootstrap: [AppComponent]
})
export class AppModule {
}
