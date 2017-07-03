"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var canvas_component_1 = require("./canvas.component");
var cover_component_1 = require("./cover.component");
var dashboard_component_1 = require("./dashboard.component");
var entries_component_1 = require("./entries.component");
var logged_in_guard_service_1 = require("./auth/logged-in.guard.service");
var appRoutes = [
    {
        path: '',
        redirectTo: 'h',
        pathMatch: 'full'
    },
    {
        path: 'h',
        component: cover_component_1.CoverComponent
    },
    {
        path: 'd',
        component: dashboard_component_1.DashboardComponent,
        canActivate: [logged_in_guard_service_1.LoggedInGuard],
        children: [
            {
                path: '',
                component: canvas_component_1.CanvasComponent
            },
            {
                path: 'entries',
                component: entries_component_1.EntriesComponent
            }
        ]
    }
];
var AppRoutingModule = (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        core_1.NgModule({
            imports: [router_1.RouterModule.forRoot(appRoutes)],
            exports: [router_1.RouterModule]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());
exports.AppRoutingModule = AppRoutingModule;
//# sourceMappingURL=app.routing.module.js.map