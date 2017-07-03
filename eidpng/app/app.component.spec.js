"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var platform_browser_1 = require("@angular/platform-browser");
var app_component_1 = require("./app.component");
var router_stubs_1 = require("./router-stubs");
describe('AppComponent', function () {
    var comp;
    var fixture;
    beforeEach(testing_1.async(function () {
        testing_1.TestBed.configureTestingModule({
            declarations: [
                app_component_1.AppComponent,
                router_stubs_1.RouterOutletStubComponent
            ]
        }).compileComponents()
            .then(function () {
            fixture = testing_1.TestBed.createComponent(app_component_1.AppComponent);
            comp = fixture.componentInstance;
        });
    }));
    it('can instantiate it', function () {
        expect(comp).not.toBeNull();
    });
    it('has a .navbar-fixed-bottom with a span for messages', function () {
        var de = fixture.debugElement.query(platform_browser_1.By.css('.navbar-fixed-bottom'));
        var el = de.nativeElement;
        expect(el.children.item(0).tagName.toLowerCase()).toBe('span');
    });
});
//# sourceMappingURL=app.component.spec.js.map