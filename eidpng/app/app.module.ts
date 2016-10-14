import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CollapseModule } from 'ng2-bootstrap/ng2-bootstrap';

import { AppComponent }   from './app.component';
import { CoverComponent } from './cover.component';
import { CanvasComponent } from './canvas.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries.component';

import { LoginModule } from './login/login.module';
import { AuthModule } from './auth/auth.module';

import { routing } from './app.routing';

@NgModule({
  imports: [ 
    BrowserModule,
    CollapseModule,
    LoginModule,
    AuthModule,
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