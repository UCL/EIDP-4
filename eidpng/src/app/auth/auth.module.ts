import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';

import { AuthService } from './auth.service';
import { AuthenticateGuard } from './authenticate.guard';

@NgModule({
  imports: [
    CommonModule,
    HttpModule
  ],
  declarations: [],
  providers: [AuthService, AuthenticateGuard]
})
export class AuthModule { }
