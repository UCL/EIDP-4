"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var ngx_bootstrap_2 = require("ngx-bootstrap");
var app_component_1 = require("./app.component");
var cover_component_1 = require("./cover.component");
var canvas_component_1 = require("./canvas.component");
var dashboard_component_1 = require("./dashboard.component");
var entries_component_1 = require("./entries.component");
var app_routing_module_1 = require("./app.routing.module");
var login_module_1 = require("./login/login.module");
var auth_module_1 = require("./auth/auth.module");
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                app_routing_module_1.AppRoutingModule,
                login_module_1.LoginModule,
                auth_module_1.AuthModule,
                ngx_bootstrap_1.CollapseModule.forRoot(),
                ngx_bootstrap_2.ButtonsModule.forRoot()
            ],
            declarations: [
                app_component_1.AppComponent,
                cover_component_1.CoverComponent,
                canvas_component_1.CanvasComponent,
                dashboard_component_1.DashboardComponent,
                entries_component_1.EntriesComponent
            ],
            bootstrap: [app_component_1.AppComponent]
        })
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map