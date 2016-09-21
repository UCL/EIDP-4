import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AlertModule } from 'ng2-bootstrap/ng2-bootstrap';

import { AppComponent }   from './app.component';
import { CoverComponent } from './cover.component';
import { LoginComponent } from './login.component';
import { DashboardComponent } from './dashboard.component';

import { routing } from './app.routing';

@NgModule({
  imports: [ 
    BrowserModule,
    AlertModule,
    routing 
    ],
  declarations: [
     AppComponent,
     CoverComponent,
     LoginComponent,
     DashboardComponent
     ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }