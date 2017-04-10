import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'app/dashboard.component.html',
  styleUrls: ['app/dashboard.component.css']
})
export class DashboardComponent {
  title = 'EIDP4';
  isCollapsed: boolean = true;

  constructor(private _router: Router) { }

  onLogout() {
    this._router.navigate(['/h']);
  }
}