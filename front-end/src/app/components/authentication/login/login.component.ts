import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticateService } from 'src/app/services/authenticate.service';
import { environment } from 'src/environments/environment';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
	hasBeenAuthenticated = false;

	constructor(
		public authenticateService: AuthenticateService,
		private route: ActivatedRoute,
		private httpClient: HttpClient,
		private router: Router) {
		route.queryParams.subscribe((token: { code: string }) => {
			// User was redirected from osu oauth
			if (token.code !== undefined) {
				this.httpClient.post<OsuAuthenticateResponse>(`${environment.apiUrl}osu-authenticate`, token.code,
					{ observe: 'response' }).subscribe(response => {
						this.authenticateService.setOsuOauthToken(response.body.osuOauthHelper.access_token, response.body.osuOauthHelper.refresh_token);
						this.authenticateService.setCookie(this.authenticateService.WY_TOURNEY_TOKEN, response.headers.get('Authorization'));

						authenticateService.getAuthenticatedUser();

						this.router.navigate(['profile']);
					}, (err: HttpErrorResponse) => {
						console.log(err);
					});
			}
		});

		route.params.subscribe((params) => {
			if (Object.keys(params).length > 0) {
				this.hasBeenAuthenticated = true;
			}
		});
	}

	ngOnInit(): void { }
}
