export class NavigationItem {
	link: string;
	name: string;
	icon: string;
	dropdown = false;
	hasToBeLoggedIn = false;
	hasToBeAdmin = false;
	classExactMatch = false;
	showWhenLoggedIn: boolean;
	dropdownItems: NavigationItem[] = [];
	execute: () => void;

	constructor(init?: Partial<NavigationItem>) {
		Object.assign(this, init);
	}
}
