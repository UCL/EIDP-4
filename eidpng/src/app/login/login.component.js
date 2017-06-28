"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require('@angular/core');
var LoginComponent = (function () {
    function LoginComponent(router) {
        this.router = router;
        this.timeout = 180; // 3 minutes
        this.pgbValue = 1;
        this.heading = 'EIDP4 Database';
    }
    LoginComponent.prototype.ngOnInit = function () {
        var _this = this;
        console.log('ngOnInit');
        setInterval(function () { return _this.addIncrement(); }, 1000);
    };
    LoginComponent.prototype.addIncrement = function () {
        if (this.pgbValue == this.timeout) {
            this.redirectToCover();
        }
        else {
            this.pgbValue += 1;
        }
    };
    LoginComponent.prototype.resetProgressBar = function () {
        this.pgbValue = 1;
    };
    LoginComponent.prototype.redirectToCover = function () {
        this.router.navigate(['/h']);
    };
    LoginComponent = __decorate([
        core_1.Component({
            selector: 'app-login',
            templateUrl: './login.component.html',
            styleUrls: ['./login.component.scss']
        })
    ], LoginComponent);
    return LoginComponent;
}());
exports.LoginComponent = LoginComponent;
