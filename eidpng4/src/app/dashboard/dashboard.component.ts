import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  title = 'EIDP4';
  isCollapsed: boolean = true;

  constructor(private _router: Router) { }

  onLogout() {
    this._router.navigate(['/h']);
  }

}
