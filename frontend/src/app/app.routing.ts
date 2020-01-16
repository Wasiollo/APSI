import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';

const routes: Routes = [
    {path: 'authenticate', loadChildren: () => import('./modules/authentication/authentication.module').then(m => m.AuthenticationModule)},
    {path: 'home', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)},
    {path: 'admin', loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule)},
    {path: 'tests', loadChildren: () => import('./modules/tests/tests.module').then(m => m.TestsModule)},
    {path: '', pathMatch: 'full', redirectTo: 'home'},
    {path: '**', redirectTo: 'home'}
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
