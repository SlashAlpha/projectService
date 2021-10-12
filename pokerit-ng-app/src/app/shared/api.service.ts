import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Play} from "../model/play";
import {PlayerDTO} from "../model/playerDTO";
import {Player} from "../model/player";
import {waitForAsync} from "@angular/core/testing";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL="http://localhost:8081/api/v1/poker";
  //launching game
  private NEW_GAME=this.BASE_URL+"/newgame";
  private NEW_PLAY=this.BASE_URL+"/newplay";

  //players
  private PLAYERS=this.BASE_URL+"/players";
  private  NEW_PLAYER=this.PLAYERS+"/newplayer";
  private BLINDS=this.PLAYERS+"/players/blinds";
  private play:Play=new Play("");
  players:PlayerDTO[]=[];
  Player = {
    id:'',
    name:'',
    age:0,
    bank:0
  };
  game:string="";




  constructor(private http:HttpClient) {

  }

  newPlay():String{
    this.http.get<string>(this.NEW_PLAY).subscribe(
     res=>{
       this.play=new Play(res);
       alert(this.play.id);
     },req=>{
       alert("error creating the play");
      }
   );


    return this.play.id;
  }



  newGame():void{
    let url=this.NEW_GAME;
    this.http.get<string>(this.NEW_GAME+this.play.id).subscribe(res=>{
      this.game=res;
      alert(this.game);

    },err => {
      alert("An error has occured");
    });
  }

  registerPlayer(player:Player):Observable<any>{
   return  this.http.post(this.NEW_PLAYER+this.play.id,player)

  }

  getPlayers():void{
    this.http.get<PlayerDTO[]>(this.PLAYERS+"/"+this.play.id).subscribe(res=>{
        this.players=res;


      },err => {
        alert("Players List not acquired");}

    );
  }

  setBlindToPlayer():void{
    let url="http://localhost:8081/api/v1/poker/players/blinds";
    this.http.get(this.BLINDS+this.play.id).subscribe(res=>{

      alert("blinds are set");
    },err => {

      alert("blinds not set");
    });

  }



}
