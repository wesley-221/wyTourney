import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { User } from 'src/app/models/user';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
	selector: 'app-sign-up',
	templateUrl: './sign-up.component.html',
	styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
	registrationForm: FormGroup;

	registerErrors: string[] = [];
	registerSuccess: string[] = [];

	constructor(private authenticateService: AuthenticateService) {
		this.registrationForm = new FormGroup({
			username: new FormControl('', [
				Validators.required
			]),
			password: new FormControl('', [
				Validators.required
			]),
			'password-confirmation': new FormControl('', [
				Validators.required
			])
		});

		this.registrationForm.setValidators(this.isEqualTo('password', 'password-confirmation'));
	}

	ngOnInit(): void { }

	register(): void {
		if (this.registrationForm.valid) {
			const username: AbstractControl = this.registrationForm.get('username');
			const password: AbstractControl = this.registrationForm.get('password');
			const passwordConfirm: AbstractControl = this.registrationForm.get('password-confirmation');

			const newUser = new User();

			newUser.username = username.value;
			newUser.password = password.value;
			newUser.passwordConfirm = passwordConfirm.value;

			this.authenticateService.registerNewAccount(newUser).subscribe((result) => {
				this.registerSuccess = [];
				this.registerErrors = [];

				this.registerSuccess.push(result.message);
			}, (err: HttpErrorResponse) => {
				this.registerSuccess = [];
				this.registerErrors = [];

				this.registerErrors.push(err.error.message);
			});
		}
		else {
			this.registrationForm.markAllAsTouched();
		}
	}

	private isEqualTo(type1: any, type2: any): ValidatorFn {
		return (checkForm: FormGroup): any => {
			const v1 = checkForm.controls[type1];
			const v2 = checkForm.controls[type2];

			return v1.value === v2.value ? null : v2.setErrors({ notEquivalent: true });
		};
	}
}
