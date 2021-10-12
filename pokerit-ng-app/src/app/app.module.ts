import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { CardsComponent } from './cards/cards.component';
import {Router, RouterModule, Routes} from "@angular/router";
import { GameComponent } from './game/game.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { PokerComponent } from './poker/poker.component';
import { BlackJackComponent } from './black-jack/black-jack.component';
import { IndexComponent } from './index/index.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

const appRoutes: Routes = [
  {
    path: "index",
    component: IndexComponent
  }, {
    path: "poker",
    component: PokerComponent
  },{
    path: "blackjack",
    component: BlackJackComponent
  },{
    path: "game",
    component: GameComponent
  },{
    path:'',
    component:IndexComponent
  },{path:'**',
  component:NotFoundComponent},
  ]
;

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    CardsComponent,
    GameComponent,
    NotFoundComponent,
    PokerComponent,
    BlackJackComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes,{enableTracing:true}),
    FormsModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
