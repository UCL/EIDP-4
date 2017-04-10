import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CollapseModule } from 'ngx-bootstrap';
import { ButtonsModule } from 'ngx-bootstrap';

import { AppComponent }  from './app.component';
import { CoverComponent } from './cover.component';
import { CanvasComponent } from './canvas.component';
import { DashboardComponent } from './dashboard.component';
import { EntriesComponent } from './entries.component';

import { AppRoutingModule } from './app.routing.module';
import { LoginModule } from './login/login.module';
import { AuthModule } from './auth/auth.module';

@NgModule({
  imports:      [ 
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    AuthModule,
    CollapseModule.forRoot(),
    ButtonsModule.forRoot()
  ],
  declarations: [ 
    AppComponent,
    CoverComponent,
    CanvasComponent,
    DashboardComponent,
    EntriesComponent
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
