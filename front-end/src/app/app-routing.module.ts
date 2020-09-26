import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/authentication/login/login.component';
import { SignUpComponent } from './components/authentication/sign-up/sign-up.component';
import { ErrorComponent } from './components/main/error/error.component';
import { IndexComponent } from './components/main/index/index.component';
import { MainComponent } from './components/main/main/main.component';

const routes: Routes = [
	{
		path: '',
		component: MainComponent,
		children: [
			{ path: '', component: IndexComponent },

			{ path: 'login', component: LoginComponent },
			{ path: 'sign-up', component: SignUpComponent },

			{ path: '**', component: ErrorComponent }
		]
	}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
