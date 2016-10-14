import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login.component';

@NgModule({
  imports: [RouterModule.forChild([
    { path: 'l', component: LoginComponent }
  ])],
  exports: [RouterModule]
})
export class LoginRoutingModule {}