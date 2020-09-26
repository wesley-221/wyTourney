import { Injectable } from '@angular/core';
import {
	HttpRequest,
	HttpHandler,
	HttpEvent,
	HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AuthenticateService } from '../services/authenticate.service';
import { CookieService } from 'ngx-cookie-service';
import { catchError } from 'rxjs/operators';

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {
	constructor(private authenticateService: AuthenticateService, private cookieService: CookieService) { }

	intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
		let token: string = this.cookieService.get(this.authenticateService.cookieName);

		if (token) {
			request = request.clone({ setHeaders: { Authorization: token }, withCredentials: true });
		}

		return next.handle(request).pipe(catchError((error: HttpErrorResponse): Observable<any> => {
			return throwError(error);
		}));
	}
}
