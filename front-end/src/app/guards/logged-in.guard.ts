import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticateService } from '../services/authenticate.service';

@Injectable({
	providedIn: 'root'
})
/**
 * Guard that will prevent users from going to the given url that aren't logged in
 */
export class LoggedInGuard implements CanActivate {
	constructor(private router: Router, private authenticateService: AuthenticateService) { }
	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
		if (this.authenticateService.isLoggedIn) {
			return true;
		}

		return this.router.navigate(['/']);
	}
}
