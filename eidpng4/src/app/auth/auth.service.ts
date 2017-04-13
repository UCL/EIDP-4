import { Injectable, Optional } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import * as models from './model/authmodel';

@Injectable()
export class AuthService {

  private authUrl = 'http://localhost:3001/sessions/create';
  private loggedIn = false;

  constructor(private http: Http, @Optional() basePath: string) {
    this.loggedIn = !!localStorage.getItem('auth_token');
    if (basePath) {
      this.authUrl = basePath;
    }
  }

}
