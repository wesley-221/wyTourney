import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './components/main/index/index.component';
import { ErrorComponent } from './components/main/error/error.component';
import { MainComponent } from './components/main/main/main.component';
import { NavigationBarComponent } from './components/main/navigation-bar/navigation-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';
import { ContactComponent } from './components/contact/contact.component';
import { LoginComponent } from './components/authentication/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { SignUpComponent } from './components/authentication/sign-up/sign-up.component';
import { AlertComponent } from './components/main/alert/alert.component';
import { AuthenticateService } from './services/authenticate.service';
import { NavigationItemComponent } from './components/main/navigation-item/navigation-item.component';

@NgModule({
	declarations: [
		AppComponent,
		IndexComponent,
		ErrorComponent,
		MainComponent,
		NavigationBarComponent,
		ContactComponent,
		LoginComponent,
		SignUpComponent,
		AlertComponent,
		NavigationItemComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		BrowserAnimationsModule,
		MaterialModule,
		HttpClientModule,
		FormsModule,
		ReactiveFormsModule
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule {
	constructor(private authenticateService: AuthenticateService) { }
}
