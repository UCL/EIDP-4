import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterOutletStubComponent } from './router-stubs';

describe('AppComponent', () => {

  let comp: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        RouterOutletStubComponent
      ]
    }).compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(AppComponent);
        comp = fixture.componentInstance;
      });
  }));

  it('can instantiate it', () => {
    expect(comp).not.toBeNull();
  });

  it('has a .navbar-fixed-bottom with a span for messages', () => {
    const de = fixture.debugElement.query(By.css('.navbar-fixed-bottom'));
    const el : HTMLElement = de.nativeElement;
    expect(el.children.item(0).tagName.toLowerCase()).toBe('span');
  });

});