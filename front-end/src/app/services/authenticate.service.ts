import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { OsuService } from './osu.service';

@Injectable({
	providedIn: 'root'
})
export class AuthenticateService {
	private readonly apiUrl: string = environment.apiUrl;
	public readonly OSU_ACCESS_TOKEN = 'osu_oauth_access_token';
	public readonly OSU_REFRESH_TOKEN = 'osu_oauth_refresh_token';
	public readonly WY_TOURNEY_TOKEN = 'wy_tourney_token';

	public user: User = null;
	public isLoggedIn = false;
	public userRole = 0;
	public osuAccessToken: string;
	public osuRefreshToken: string;

	constructor(private cookieService: CookieService, private osuService: OsuService, private router: Router, private httpClient: HttpClient) {
		const accessToken: string = this.getCookie(this.OSU_ACCESS_TOKEN);
		const refreshToken: string = this.getCookie(this.OSU_REFRESH_TOKEN);

		if (accessToken !== '') {
			this.osuAccessToken = accessToken;

			if (this.osuRefreshToken !== '') {
				this.osuRefreshToken = refreshToken;
			}

			this.getAuthenticatedUser();
		}
	}

	/**
	 * Get data from the authenticated users
	 */
	getAuthenticatedUser(): void {
		this.httpClient.get<User>(`${this.apiUrl}user`).subscribe((result: User) => {
			const newUser = new User({
				id: result.id,
				username: result.username,
				avatarUrl: result.avatarUrl,
				coverUrl: result.coverUrl,
				pp: result.pp,
				rank: result.rank,
				countryCode: result.countryCode
			});

			this.user = newUser;
			this.isLoggedIn = true;
			this.userRole = result.role;
		});
	}

	/**
	 * Logout the user
	 */
	public logout(): void {
		this.user = null;
		this.isLoggedIn = false;
		this.userRole = 0;

		this.cookieService.delete(this.OSU_ACCESS_TOKEN, null, environment.domain);
		this.cookieService.delete(this.OSU_REFRESH_TOKEN, null, environment.domain);
		this.cookieService.delete(this.WY_TOURNEY_TOKEN, null, environment.domain);

		this.router.navigate(['/login']);
	}

	/**
	 * Get the authorization url
	 * @returns authorization url
	 */
	public getOsuOauthUrl(): string {
		const parameters = [
			{ parameterName: 'client_id', value: environment.osu.client_id },
			{ parameterName: 'redirect_uri', value: environment.osu.redirect_uri },
			{ parameterName: 'response_type', value: 'code' },
			{ parameterName: 'scope', value: 'identify' }
		];

		let finalLink = 'https://osu.ppy.sh/oauth/authorize?';

		if (parameters != null) {
			parameters.forEach(parameter => {
				finalLink += `${parameter.parameterName}=${parameter.value}&`;
			});

			finalLink = finalLink.substring(0, finalLink.length - 1);
		}

		return finalLink;
	}

	/**
	 * Set the cookies with the osu oauth tokens
	 * @param accessToken the access token
	 * @param refreshToken the refresh token
	 */
	public setOsuOauthToken(accessToken: string, refreshToken: string): void {
		this.setCookie(this.OSU_ACCESS_TOKEN, accessToken);
		this.setCookie(this.OSU_REFRESH_TOKEN, refreshToken);
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
