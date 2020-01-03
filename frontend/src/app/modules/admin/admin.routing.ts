import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListUserComponent} from './list-user/list-user.component';


const routes: Routes = [
    {path: '', component: ListUserComponent}
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
