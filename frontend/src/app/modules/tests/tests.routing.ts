import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {TestsListComponent} from './tests-list/tests-list.component';


const routes: Routes = [
    {path: '', component: TestsListComponent}
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
