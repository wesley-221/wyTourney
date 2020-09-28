import { Component, Input, OnInit } from '@angular/core';
import { NavigationItem } from 'src/app/models/navigation-item';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
	selector: 'app-navigation-item',
	templateUrl: './navigation-item.component.html',
	styleUrls: ['./navigation-item.component.scss']
})
export class NavigationItemComponent implements OnInit {
	@Input() navItem: NavigationItem;

	constructor(private authenticateService: AuthenticateService) { }
	ngOnInit(): void { }

	/**
	 * Check if you have the appropriate access to the navigation item
	 * @param navItem the navigation item to get access to
	 */
	canAccess(navItem: NavigationItem): boolean {
		if (navItem.hasToBeLoggedIn === true) {
			if (this.authenticateService.isLoggedIn === false) {
				return false;
			}
		}

		if (navItem.showWhenLoggedIn === false) {
			if (this.authenticateService.isLoggedIn === true) {
				return false;
			}
		}

		return true;
	}
}
