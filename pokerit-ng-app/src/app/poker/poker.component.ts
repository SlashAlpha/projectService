import {Component, OnInit} from '@angular/core';
import {Card} from "../model/Card";
import {Play} from "../model/play";
import {PlayerDTO} from "../model/playerDTO";
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-poker',
  templateUrl: './poker.component.html',
  styleUrls: ['./poker.component.css']
})
export class PokerComponent implements OnInit {
  players:PlayerDTO[]=[];

  player = {
    id: '',
    name: '',
    age: 0,
    bank: 0
  };

  playerDTO: PlayerDTO = new PlayerDTO("", "", 0, 0, 0, new Card("", 0, 0, "", 0), new Card("", 0, 0, "", 0));
  playId: Play = new Play("");
  playersId:PlayerDTO[]=[];





  constructor(private apiService:ApiService) {

}



  ngOnInit(): void {
  this.apiService.newPlay();


  }




  // public getPlayers(){
  //   let url="http:localhost:8081/api/v1/game/player";
  //  this.http.get<PlayerDTO[]>(url).subscribe(res=>{
  //     this.players=res;
  //
  //  },error => {
  //    alert("didn't go as expected")
  //  })
  //
  // }

  registerPlayer():void {
    this.apiService.registerPlayer(this.player).subscribe(res => {
      this.player = res;
      alert(res);

    }, err => {
      alert("An error has occured registering the player");
    });
    ;
  }




}
