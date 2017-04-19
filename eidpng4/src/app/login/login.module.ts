import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AlertModule } from 'ngx-bootstrap/alert';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [
    AlertModule.forRoot(),
    CommonModule,
    FormsModule,
    LoginRoutingModule,
    ProgressbarModule.forRoot()
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
