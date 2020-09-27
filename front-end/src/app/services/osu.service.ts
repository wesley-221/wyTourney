import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
	providedIn: 'root'
})
export class OsuService {
	private apiUrl: string = environment.apiUrl;

	constructor(private httpClient: HttpClient) { }

	/**
	 * Get /me data from osu! api
	 */
	public getMeData(): Observable<OsuMeEndPointResponse> {
		return this.httpClient.get<OsuMeEndPointResponse>(`${this.apiUrl}osu/me`);
	}
}
