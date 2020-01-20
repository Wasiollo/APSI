import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {TestsListComponent} from './tests-list/tests-list.component';
import {TestsDetailsComponent} from "./tests-details/tests-details.component";


const routes: Routes = [
    {path: '', component: TestsListComponent},
    {path: 'test-details', component: TestsDetailsComponent}
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);
