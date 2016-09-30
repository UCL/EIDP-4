import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppComponent } from './app.component';

let comp: AppComponent;
let fixture: ComponentFixture<AppComponent>;

describe('AppComponent', () => {
  beforeEach( () => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ]
    })
    .compileComponents()
    .then(() => {
      fixture = TestBed.createComponent(AppComponent);
      comp    = fixture.componentInstance;
    });
  });
  tests();
});

function tests() {

  it('can instantiate it', () => {
    expect(comp).not.toBeNull();
  });

}