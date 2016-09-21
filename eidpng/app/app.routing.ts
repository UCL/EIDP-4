import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoverComponent } from './cover.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries.component';
import { LoginComponent } from './login.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/h',
        pathMatch: 'full'
    },
    {
        path: 'h',
        component: CoverComponent
    },
    {
        path: 'l',
        component: LoginComponent
    },
    {
        path: 'd',
        component: DashboardComponent
    },
    {
        path: 'd/entries',
        component: EntriesComponent
    }
];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);