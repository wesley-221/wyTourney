import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
	loginForm: FormGroup;

	loginErrors: string[] = [];
	loginSuccess: string[] = [];

	constructor(private authenticateService: AuthenticateService) {
		this.loginForm = new FormGroup({
			username: new FormControl('', [
				Validators.required
			]),
			password: new FormControl('', [
				Validators.required
			])
		});
	}

	ngOnInit(): void { }

	login(): void {
		if (this.loginForm.valid) {
			const username: AbstractControl = this.loginForm.get('username');
			const password: AbstractControl = this.loginForm.get('password');

			const user = new User();

			user.username = username.value;
			user.password = password.value;

			this.authenticateService.loginAccount(user).subscribe((result) => {
				this.loginErrors = [];
				this.loginSuccess = [];

				this.authenticateService.setCookie(this.authenticateService.cookieName, result.token);
				this.loginSuccess.push(result.message);
			}, (err: HttpErrorResponse) => {
				this.loginErrors = [];
				this.loginSuccess = [];

				this.loginErrors.push(err.error.message);
			});
		}
		else {
			this.loginForm.markAllAsTouched();
		}
	}
}
