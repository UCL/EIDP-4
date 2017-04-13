import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  
  timeout = 180; // 3 minutes
  pgbValue = 1;
  heading = 'EIDP4 Database';

  constructor(
    private authService: AuthService,
    private router: Router  
  ) { }

  ngOnInit() {
    console.log('ngOnInit');
    setInterval(() => this.addIncrement(), 1000);
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
