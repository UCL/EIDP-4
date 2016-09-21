import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  templateUrl: 'app/cover.component.html',
  styleUrls: ['app/cover.component.css']
})
export class CoverComponent {

  constructor(private _router: Router) { }

  onStart() {
    this._router.navigate(['/l']);
  }
}