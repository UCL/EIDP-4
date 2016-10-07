import { Component, Directive, Input } from '@angular/core';

@Component({ selector: 'router-outlet', template: '' })
export class RouterOutletStubComponent { }

@Directive({
  selector: '[routerLink]'
})
export class RouterLinkStubDirective {
  private linkData: any[] = [];

  @Input('routerLink') linkTo: any[] | string;

  get linkUrlArray() {
    if (Array.isArray(this.linkTo)) {
      this.linkData = this.linkTo;
    } else {
      this.linkData = [this.linkTo];
    }
    return this.linkData;
  }
}