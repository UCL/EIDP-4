import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollapseModule } from 'ngx-bootstrap/collapse';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';

@NgModule({
  imports: [
    CollapseModule.forRoot(),
    CommonModule,
    DashboardRoutingModule
  ],
  declarations: [DashboardComponent]
})
export class DashboardModule { }
