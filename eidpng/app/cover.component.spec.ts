import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { CoverComponent } from './cover.component';
import { RouterLinkStubDirective } from './router-stubs';

describe('CoverComponent', () => {

  let comp: CoverComponent;
  let fixture: ComponentFixture<CoverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CoverComponent,
        RouterLinkStubDirective
      ]
    }).compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(CoverComponent);
        comp = fixture.componentInstance;
      });
  }));

  it('can instantiate it', () => {
    expect(comp).not.toBeNull();
  });

  it('has a routerLink pointing to /l', () => {
    fixture.detectChanges();
    // find DebugElements with an attached RouterLinkStubDirective
    const linkDes: DebugElement = fixture.debugElement
      .query(By.directive(RouterLinkStubDirective));

    // get the attached link directive instances using the DebugElement injectors
    const links: RouterLinkStubDirective = linkDes.injector
      .get(RouterLinkStubDirective) as RouterLinkStubDirective;

    expect(links).not.toBeNull('should have a link');

    expect(links.linkUrlArray[0]).toBe('/l', 'should provide a link to Login Component');

  });

});