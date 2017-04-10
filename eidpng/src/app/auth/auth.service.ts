import { Injectable, Optional } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import * as models from './model/models';
//import { User } from './user';

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

  login(username: string, password: string): Observable<models.AuthToken> {
    let credentials = <models.UseridPassword>{};
    credentials.userid = username;
    credentials.password = password;

    let body = JSON.stringify(credentials);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http
      .post(this.authUrl, body, options)
      .map((response: Response) => {
        if (response.status === 204) {
          return undefined;
        } else {
          return response.json();
        }
      })
      .map((res) => {
        if (res.auth_token) {
          localStorage.setItem('auth_token', res.auth_token);
          this.loggedIn = true;
        }
        return res || {};
      })
      .catch(this.handleError);
  }

  logout() {
    localStorage.removeItem('auth_token');
    this.loggedIn = false;
  }

  isLoggedIn() {
    return this.loggedIn;
  }

  private handleError(error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

}