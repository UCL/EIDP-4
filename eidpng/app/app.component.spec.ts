import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';

import { AppComponent } from './app.component';

describe('AppComponent', () => {

  let comp: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let de: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [AppComponent]
    }).compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(AppComponent);
        comp = fixture.componentInstance;
      });
  }));

  it('can instantiate it', () => {
    expect(comp).not.toBeNull();
  });

});