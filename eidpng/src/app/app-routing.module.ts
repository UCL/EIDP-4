import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoverComponent } from './cover/cover.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'h',
    pathMatch: 'full'
  },
  {
    path: 'h',
    component: CoverComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
