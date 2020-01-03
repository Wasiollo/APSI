import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {AuthenticationComponent} from './authentication.component';
import {LogoutComponent} from './logout/logout.component';
import {ExpiredTokenComponent} from './expired-token/expired-token.component';

const routes: Routes = [
    {path: '', component: AuthenticationComponent},
    {path: 'logout-user', component: LogoutComponent},
    {path: 'expired-token', component: ExpiredTokenComponent}
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
