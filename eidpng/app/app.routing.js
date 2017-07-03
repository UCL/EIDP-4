"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var router_1 = require("@angular/router");
var canvas_component_1 = require("./canvas.component");
var cover_component_1 = require("./cover.component");
var dashboard_component_1 = require("./dashboard.component");
var entries_component_1 = require("./entries.component");
var logged_in_guard_1 = require("./auth/logged-in.guard");
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
        path: 'l',
        loadChildren: 'app/login/login.module#LoginModule'
    },
    {
        path: 'd',
        component: dashboard_component_1.DashboardComponent,
        canActivate: [logged_in_guard_1.LoggedInGuard],
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
exports.routing = router_1.RouterModule.forRoot(appRoutes);
//# sourceMappingURL=app.routing.js.map