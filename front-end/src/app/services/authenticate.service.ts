import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import * as jwtDecode from 'jwt-decode';

@Injectable({
	providedIn: 'root'
})
export class AuthenticateService {
	private readonly apiUrl: string = environment.apiUrl;
	public readonly cookieName: string = 'auth';
	public user: User = null;
	public isLoggedIn = false;
	public isAdmin = false;

	constructor(private httpClient: HttpClient, private cookieService: CookieService) {
		const authCookie: string = this.getCookie(this.cookieName);

		if (authCookie !== '') {
			const loggedInUser: User = new User();
			const cookieValue = jwtDecode(authCookie);

			loggedInUser.id = cookieValue.id;
			loggedInUser.username = cookieValue.username;
			loggedInUser.admin = cookieValue.admin;
			loggedInUser.token = cookieValue;

			this.user = loggedInUser;
			this.isLoggedIn = true;
			this.isAdmin = cookieValue.admin;
		}
	}

	/**
	 * Endpoint to register an account
	 * @param user the user to register
	 */
	public registerNewAccount(user: User): Observable<any> {
		return this.httpClient.post<User>(`${this.apiUrl}register`, user);
	}

	/**
	 * Endpoint to login an account
	 * @param user the user to login
	 */
	public loginAccount(user: User): Observable<any> {
		return this.httpClient.post<User>(`${this.apiUrl}login`, user);
	}

	/**
	 * Logout the user
	 */
	public logout(): void {
		this.user = null;
		this.isLoggedIn = false;
		this.isAdmin = false;

		this.cookieService.delete(this.cookieName);
	}

	/**
	 * Get the value of a cookie
	 * @param name the name of the cookie
	 */
	public getCookie(name: string): string {
		return this.cookieService.get(name);
	}

	/**
	 * Create a cookie
	 * @param name the name of the cookie
	 * @param value the value of the cookie
	 */
	public setCookie(name: string, value: string): void {
		this.cookieService.set(name, value, new Date().setDate(new Date().getDate() + 7), '/', environment.domain, false, 'Strict');
	}
}
