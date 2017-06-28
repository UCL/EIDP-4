import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { AlertComponent, AlertConfig } from 'ngx-bootstrap/alert';

import { AuthService } from '../auth/auth.service';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceStub: {};

  beforeEach(async(() => {

    authServiceStub = {
    };

    TestBed.configureTestingModule({
      declarations: [ AlertComponent, LoginComponent ],
      imports: [ ReactiveFormsModule, RouterTestingModule ],
      providers: [
        AlertConfig,
        {provide: AuthService, useValue: authServiceStub}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
