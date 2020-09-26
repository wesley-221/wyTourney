import { Component, Input, OnInit } from '@angular/core';

export enum AlertTypes {
	Info = 'info',
	Success = 'success',
	Danger = 'danger',
	Warning = 'warning'
}

@Component({
	selector: 'app-alert',
	templateUrl: './alert.component.html',
	styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {
	@Input() alertType: AlertTypes;
	AlertTypes = AlertTypes;

	constructor() { }
	ngOnInit(): void { }
}
