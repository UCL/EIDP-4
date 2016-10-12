import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CanvasComponent } from './canvas.component';
import { CoverComponent } from './cover.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries.component';

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
        loadChildren: 'app/login/login.module#LoginModule'
    },
    {
        path: 'd',
        component: DashboardComponent,
        children: [
            {
                path: '',
                component: CanvasComponent
            },
            {
                path: 'entries',
                component: EntriesComponent
            }
        ]
    }

];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);