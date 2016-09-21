import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoverComponent } from './cover.component';
import { LoginComponent } from './login.component';
import { DashboardComponent } from './dashboard.component';

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
    }
];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);