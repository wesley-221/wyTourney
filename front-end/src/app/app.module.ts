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
import { LoginComponent } from './components/authentication/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AlertComponent } from './components/main/alert/alert.component';
import { AuthenticateService } from './services/authenticate.service';
import { NavigationItemComponent } from './components/main/navigation-item/navigation-item.component';
import { TokenInterceptor } from './guards/token.interceptor';
import { ProfileComponent } from './components/authentication/profile/profile.component';

@NgModule({
	declarations: [
		AppComponent,
		IndexComponent,
		ErrorComponent,
		MainComponent,
		NavigationBarComponent,
		LoginComponent,
		AlertComponent,
		NavigationItemComponent,
		ProfileComponent
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
	providers: [
		{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
	],
	bootstrap: [AppComponent]
})
export class AppModule {
	constructor(private authenticateService: AuthenticateService) { }
}
