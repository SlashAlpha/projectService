import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PlayerDTO} from "../model/playerDTO";

import {PokerComponent} from "../poker/poker.component";
import {Play} from "../model/play";
import {ApiService} from "../shared/api.service";




@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

   Player = {
    id:'',
    name:'',
    age:0,
    bank:0
  };






  constructor(private http:HttpClient,private apiService:ApiService) {


  }

  ngOnInit(): void {

    this.apiService.newGame();
   // this.apiService.getPlayers();

  // this.setBlindToPlayer();

  }

  setBlindToPlayer():void{
    this.apiService.setBlindToPlayer();

    }
    newGame():void{
    this.apiService.newGame();

    }





}

