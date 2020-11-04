import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomepageComponent} from './components/homepage/homepage.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SubscribeComponent} from './components/modals/subscribe/subscribe.component';
import {FormsModule} from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import {HttpClientModule} from '@angular/common/http';
import {MatFormFieldModule} from '@angular/material/form-field';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    SubscribeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    MatFormFieldModule
  ],
  providers: [],
  entryComponents: [
    SubscribeComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
