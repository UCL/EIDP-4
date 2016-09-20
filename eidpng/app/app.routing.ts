import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoverComponent } from './cover.component';

const appRoutes: Routes = [
    {
        path: '/',
        component: CoverComponent
    }
];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);