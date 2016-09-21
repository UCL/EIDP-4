import { Component } from '@angular/core';
@Component({
  templateUrl: 'app/dashboard.component.html',
  styleUrls: ['app/dashboard.component.css']
})
export class DashboardComponent {
    title = 'EIDP4';
    isCollapsed:boolean = true;

    onLogout() {
    }
}