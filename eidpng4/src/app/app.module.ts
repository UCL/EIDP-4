import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CoverComponent } from './cover/cover.component';

import { AppRoutingModule } from './app-routing.module';

import { AuthModule } from './auth/auth.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { LoginModule } from './login/login.module';

@NgModule({
  declarations: [
    AppComponent,
    CoverComponent
  ],
  imports: [
    AppRoutingModule,
    AuthModule,
    BrowserModule,
    DashboardModule,
    FormsModule,
    HttpModule,
    LoginModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
