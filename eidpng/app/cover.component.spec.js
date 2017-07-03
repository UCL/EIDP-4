"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var platform_browser_1 = require("@angular/platform-browser");
var cover_component_1 = require("./cover.component");
var router_stubs_1 = require("./router-stubs");
describe('CoverComponent', function () {
    var comp;
    var fixture;
    beforeEach(testing_1.async(function () {
        testing_1.TestBed.configureTestingModule({
            declarations: [
                cover_component_1.CoverComponent,
                router_stubs_1.RouterLinkStubDirective
            ]
        }).compileComponents()
            .then(function () {
            fixture = testing_1.TestBed.createComponent(cover_component_1.CoverComponent);
            comp = fixture.componentInstance;
        });
    }));
    it('can instantiate it', function () {
        expect(comp).not.toBeNull();
    });
    it('has a routerLink pointing to /l', function () {
        fixture.detectChanges();
        // find DebugElements with an attached RouterLinkStubDirective
        var linkDes = fixture.debugElement
            .query(platform_browser_1.By.directive(router_stubs_1.RouterLinkStubDirective));
        // get the attached link directive instances using the DebugElement injectors
        var links = linkDes.injector
            .get(router_stubs_1.RouterLinkStubDirective);
        expect(links).not.toBeNull('should have a link');
        expect(links.linkUrlArray[0]).toBe('/l', 'should provide a link to Login Component');
    });
});
//# sourceMappingURL=cover.component.spec.js.map