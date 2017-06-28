import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollapseModule } from 'ngx-bootstrap/collapse';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { CanvasComponent } from './canvas/canvas.component';
import { EntriesComponent } from './entries/entries.component';

@NgModule({
  imports: [
    CollapseModule.forRoot(),
    CommonModule,
    DashboardRoutingModule
  ],
  declarations: [DashboardComponent, CanvasComponent, EntriesComponent]
})
export class DashboardModule { }
