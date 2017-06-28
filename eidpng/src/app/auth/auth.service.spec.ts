import { TestBed, inject } from '@angular/core/testing';
import { ConnectionBackend, HttpModule } from '@angular/http';
import { MockBackend } from '@angular/http/testing';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpModule ],
      providers: [
        AuthService,
        { provide: ConnectionBackend, useClass: MockBackend }
      ]
    });
  });

  it('should ...', inject([AuthService], (service: AuthService) => {
    expect(service).toBeTruthy();
  }));
});
