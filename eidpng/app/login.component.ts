import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'app/login.component.html',
  styleUrls: ['app/login.component.css']
})
export class LoginComponent implements OnInit {

  timeout = 180; // 3 minutes
  pgbValue = 1;
  heading = 'EIDP4 Database';

  constructor(private _router: Router) {}

  ngOnInit() {
    console.log('ngOnInit');
    setInterval(() => this.addIncrement(), 1000);
  }

  onLogin() {
    this._router.navigate(['d']);
  }

  addIncrement() {
        if (this.pgbValue == this.timeout) {
            this.redirectToCover();
        } else {
            this.pgbValue += 1;
        }
    }

  resetProgressBar() {
    this.pgbValue = 1;
  }

  redirectToCover() {
        this._router.navigate(['/h'])
  }
}