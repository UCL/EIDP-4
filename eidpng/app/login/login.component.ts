import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../auth/auth.service';
import { User } from '../auth/user';

@Component({
  templateUrl: 'app/login/login.component.html',
  styleUrls: ['app/login/login.component.css']
})
export class LoginComponent implements OnInit {

  timeout = 180; // 3 minutes
  pgbValue = 1;
  heading = 'EIDP4 Database';

  user = new User(-1, '', '');

  constructor(
    private authService: AuthService, 
    private router: Router
  ) { }

  ngOnInit() {
    console.log('ngOnInit');
    setInterval(() => this.addIncrement(), 1000);
  }

  onLogin() {
    this.authService.login(this.user).subscribe( (result) => {
      if (result) {
        this.router.navigate(['d']);
      }
    });
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
    this.router.navigate(['/h']);
  }
}