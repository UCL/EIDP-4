import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CanvasComponent } from './canvas/canvas.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries/entries.component';

const routes: Routes = [
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

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
