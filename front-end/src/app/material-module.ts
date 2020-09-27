import { NgModule } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DomSanitizer } from '@angular/platform-browser';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

const modules: any[] = [
	MatButtonModule,
	MatToolbarModule,
	MatIconModule,
	MatFormFieldModule,
	MatInputModule,
	MatMenuModule,
	MatProgressSpinnerModule
];

@NgModule({
	imports: [...modules],
	exports: [...modules]
})
export class MaterialModule {
	constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
		this.matIconRegistry
			.addSvgIcon('user', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/user.svg'))
			.addSvgIcon('lock', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/padlock.svg'))
			.addSvgIcon('info', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/info.svg'))
			.addSvgIcon('exclamation', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/exclamation.svg'));
	}
}
