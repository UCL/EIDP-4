import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { AlertModule } from 'ngx-bootstrap/alert';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [
    AlertModule.forRoot(),
    ButtonsModule.forRoot(),
    CommonModule,
    ReactiveFormsModule,
    LoginRoutingModule,
    ProgressbarModule.forRoot()
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
