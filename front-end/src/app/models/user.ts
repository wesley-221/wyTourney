export class User {
	id: number;
	username: string;
	avatarUrl: string;
	coverUrl: string;
	pp: number;
	rank: number;
	countryCode: string;
	lastUpdate: Date;
	role: number;

	constructor(init?: Partial<User>) {
		Object.assign(this, init);
	}

	/**
	 * Get the link for the flag
	 */
	getFlagLink(): string {
		// Temporary, will change with local files soon
		return `https://github.com/ppy/osu-wiki/blob/master/wiki/shared/flag/${this.countryCode}.gif?raw=true`;
	}
}
