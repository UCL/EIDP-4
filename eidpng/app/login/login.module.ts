import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlertModule } from 'ng2-bootstrap/ng2-bootstrap';

import { LoginComponent } from './login.component';
import { LoginRoutingModule } from './login.routing';

@NgModule({
    imports: [ CommonModule, LoginRoutingModule, AlertModule ],
    declarations: [ LoginComponent ]
})
export class LoginModule {

}