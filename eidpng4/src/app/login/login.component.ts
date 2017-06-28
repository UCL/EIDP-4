import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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

  authForm: FormGroup;
  username: string;
  password: string;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    this.buildForm();
    setInterval(() => this.addIncrement(), 1000);
  }

  buildForm(): void {
    this.authForm = this.formBuilder.group({
      'inputUsername' : [this.username, Validators.required],
      'inputPassword' : [this.password, Validators.required]
    });
  }

  onLogin(username, password) {
    this.authService.login(username, password).subscribe((result) => {
      if (result) {
        this.router.navigate(['d']);
      } else {
        console.log("went for else");
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

  validationMessages = {
    'required': 'Field value is required.',
    'minlength': 'Field value must be at least 4 characters long.',
    'maxlength': 'Field value cannot be more than 24 characters long.'
  }
}
