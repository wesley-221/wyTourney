import { Component, OnInit } from '@angular/core';
import { NavigationItem } from 'src/app/models/navigation-item';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
	selector: 'app-navigation-bar',
	templateUrl: './navigation-bar.component.html',
	styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {
	leftSideNavigation: NavigationItem[] = [
		new NavigationItem({ link: '/', name: 'home', classExactMatch: true })
	];

	rightSideNavigation: NavigationItem[] = [
		new NavigationItem({ link: '/login', name: 'login', showWhenLoggedIn: false }),
		new NavigationItem({ link: '/contact', name: 'contact' }),
		new NavigationItem({ icon: 'settings', hasToBeLoggedIn: true, showWhenLoggedIn: true, dropdown: true, dropdownItems: [
			new NavigationItem({ name: 'logout', execute: () => this.authenticateService.logout() })
		]})
	];

	constructor(public authenticateService: AuthenticateService) { }
	ngOnInit(): void { }
}
