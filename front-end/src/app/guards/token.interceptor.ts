import { Injectable } from '@angular/core';
import {
	HttpRequest,
	HttpHandler,
	HttpEvent,
	HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
	constructor(private cookieService: CookieService) { }

	intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
		if (request.url.includes(environment.apiUrl)) {
			const token: string = this.cookieService.get('osu_oauth_access_token');

			if (token) {
				request = request.clone({
					setHeaders: {
						Authorization: `Bearer ${token}`
					}, withCredentials: true
				});
			}
		}

		return next.handle(request).pipe(catchError((error: HttpErrorResponse): Observable<any> => {
			return throwError(error);
		}));
	}
}
