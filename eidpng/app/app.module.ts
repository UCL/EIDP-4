import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AlertModule } from 'ng2-bootstrap/ng2-bootstrap';
import { CollapseModule } from 'ng2-bootstrap/ng2-bootstrap';

import { AppComponent }   from './app.component';
import { CoverComponent } from './cover.component';
import { CanvasComponent } from './canvas.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries.component';

import { LoginModule } from './login/login.module';

import { routing } from './app.routing';

@NgModule({
  imports: [ 
    BrowserModule,
    AlertModule,
    CollapseModule,
    LoginModule,
    routing 
    ],
  declarations: [
     AppComponent,
     CanvasComponent,
     CoverComponent,
     DashboardComponent,
     EntriesComponent
     ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }