"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var Observable_1 = require("rxjs/Observable");
require("rxjs/add/operator/catch");
require("rxjs/add/operator/map");
//import { User } from './user';
var AuthService = (function () {
    function AuthService(http, basePath) {
        this.http = http;
        this.authUrl = 'http://localhost:3001/sessions/create';
        this.loggedIn = false;
        this.loggedIn = !!localStorage.getItem('auth_token');
        if (basePath) {
            this.authUrl = basePath;
        }
    }
    AuthService.prototype.login = function (username, password) {
        var _this = this;
        var credentials = {};
        credentials.userid = username;
        credentials.password = password;
        var body = JSON.stringify(credentials);
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http
            .post(this.authUrl, body, options)
            .map(function (response) {
            if (response.status === 204) {
                return undefined;
            }
            else {
                return response.json();
            }
        })
            .map(function (res) {
            if (res.auth_token) {
                localStorage.setItem('auth_token', res.auth_token);
                _this.loggedIn = true;
            }
            return res || {};
        })
            .catch(this.handleError);
    };
    AuthService.prototype.logout = function () {
        localStorage.removeItem('auth_token');
        this.loggedIn = false;
    };
    AuthService.prototype.isLoggedIn = function () {
        return this.loggedIn;
    };
    AuthService.prototype.handleError = function (error) {
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        var errMsg = (error.message) ? error.message :
            error.status ? error.status + " - " + error.statusText : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable_1.Observable.throw(errMsg);
    };
    AuthService = __decorate([
        core_1.Injectable(),
        __param(1, core_1.Optional()),
        __metadata("design:paramtypes", [http_1.Http, String])
    ], AuthService);
    return AuthService;
}());
exports.AuthService = AuthService;
//# sourceMappingURL=auth.service.js.map