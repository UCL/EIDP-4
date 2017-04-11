import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';

import { AuthService } from './auth.service';
import { LoggedInGuard } from './logged-in.guard.service';

@NgModule({
    imports: [CommonModule, HttpModule],
    providers: [AuthService, LoggedInGuard]
})
export class AuthModule { }