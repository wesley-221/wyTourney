declare class OsuAuthenticateResponse {
	osuOauthHelper: {
		access_token: string;
		expires_in: number;
		refresh_token: string;
		token_type: string;
	}

	user: {
		avatarUrl: string;
		countryCode: string;
		coverUrl: string;
		id: number;
		lastUpdate: number;
		pp: number;
		rank: number;
		role: number;
		username: string;
	}
}

declare class OsuMeEndPointResponse {
	avatar_url: string;
	cover_url: string;
	flag: string;
	id: number;
	pp: number;
	rank: number;
	username: string;
}
