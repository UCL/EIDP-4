import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'app/login.component.html',
  styleUrls: ['app/login.component.css']
})
export class LoginComponent {

  timeout = 600; // 10 minutes
  pgbValue = 1;
  heading = 'EIDP4 Database';

  constructor(private _router: Router) {}

  onLogin() {
    this._router.navigate(['d']);
  }

  resetProgressBar() {
    this.pgbValue = 1;
  }
}