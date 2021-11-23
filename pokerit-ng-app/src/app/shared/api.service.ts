import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Play} from "../model/play";
import {PlayerDTO} from "../model/playerDTO";
import {Player} from "../model/player";
import {Token} from "../model/Token";

import {LoginUser} from "../model/login-user";


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  players: PlayerDTO[] = [];
  Player = {
    id: '',
    name: '',
    age: 0,
    bank: 0
  };
  game: string = "";
  tokens: Token;
  private BASE_URL = "http://localhost:8081/api/v1";
  //launching game
  private NEW_GAME = this.BASE_URL + "/poker/newgame";

  private NEW_PLAYER = this.BASE_URL + "/players/newplayer/";
  private BLINDS = this.BASE_URL + "/players/blinds";
  private play: Play = new Play("");
  private NEW_PLAY = this.BASE_URL + "/poker/newplay";
  //user
  private LOGIN = "http://localhost:8081/api/login"
  private NEW_USER = this.BASE_URL + "/auth/newuser"
  //players
  private PLAYERS = this.BASE_URL + "/players/getplayers/";

  constructor(private http: HttpClient) {
    this.tokens = new Token("", "");
  }


  newPlay(): String {
    let tokenAuthor = localStorage.getItem('Authorization');
    // @ts-ignore
    let headers = new HttpHeaders({'Authorization': tokenAuthor});


    // @ts-ignore


    this.http.get<string>(this.NEW_PLAY, {headers: headers}).subscribe(
      res => {
        this.play = new Play(res);
        alert(this.play.id);
      }, req => {
        alert("error creating the play");
      }
    );


    return this.play.id;
  }


  newGame(): void {
    let url = this.NEW_GAME;
    this.http.get<string>(this.NEW_GAME + this.play.id).subscribe(res => {
      this.game = res;


    }, err => {
      alert("An error has occured");
    });
  }

  registerPlayer(player: Player): Observable<any> {

    return this.http.post(this.NEW_PLAYER + this.play.id, player)


  }

  // registerUser(user: User) {
  //
  //   return this.http.post<any>(this.NEW_USER, user).subscribe(res => {
  //
  //       this.tokens = res.body;
  //     }, error => {
  //       alert("no token received");
  //     }
  //   );
  //
  //
  // }

  tokenHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    headers.set("Authorization", this.tokens.accessToken);
    headers.set('Access-Control-Allow-Origin', 'http://localhost:8081');
    headers.set('Access-Control-Allow-Methods', 'POST');
    headers.set('Access-Control-Allow-Credentials', 'true');
    return headers;

  }

  headersUrlencoded(headers: HttpHeaders): HttpHeaders {

    headers.set('Content-Type', 'application/x-www-form-urlencoded');
    headers.set('Response-Type', 'application/json');
    // Website you wish to allow to connect
    headers.set('Access-Control-Allow-Origin', 'http://localhost:8081');

    // Request methods you wish to allow
    headers.set('Access-Control-Allow-Methods', 'POST');

    // Request headers you wish to allow
    headers.set('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    headers.set('Access-Control-Allow-Credentials', 'true');
    return headers;
  }

  newLogin(login: LoginUser) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    headers.set('Response-Type', 'application/json');
    // Website you wish to allow to connect
    headers.set('Access-Control-Allow-Origin', 'http://localhost:8081');

    // Request methods you wish to allow
    headers.set('Access-Control-Allow-Methods', 'POST');

    // Request headers you wish to allow
    headers.set('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    headers.set('Access-Control-Allow-Credentials', 'true');


    let body = new URLSearchParams();
    body.set('email', login.email);
    body.set('password', login.password);
//    return this.many(body,"",2,3,this.headersUrlencoded())
    this.http.post<any>(this.LOGIN, body, {headers: headers})
      .subscribe(
        (res: Token) => {

          this.tokens = res;
          localStorage.setItem('Authorization', 'Bearer ' + this.tokens.accessToken);

          //   var string1=JSON.stringify(res);

          //   this.tokens=JSON.parse(string1)


        }
      );
    alert(localStorage.getItem('Authorization'))
  }

  getPlayers(): void {
    alert(this.PLAYERS)
    this.http.get<PlayerDTO[]>(this.PLAYERS + this.play.id).subscribe(res => {
        this.players = res;
        alert(this.players)
      }, err => {
        alert("Players List not acquired");
      }
    );
  }

  setBlindToPlayer(): void {
    let url = "http://localhost:8081/api/v1/poker/players/blinds";
    this.http.get(this.BLINDS + this.play.id).subscribe(res => {

      alert("blinds are set");
    }, err => {

      alert("blinds not set");
    });

  }

  many(data: any, objectId: string, getPostDel: number, path: number, headers: HttpHeaders): any {
    let url: string = "";
    switch (path) {
      case 1:
        url = this.NEW_PLAY;
        break;
      case 2:
        url = this.NEW_GAME;
        break;
      case 3:
        url = this.LOGIN;
        break;
      case 4:
        url = this.NEW_USER;
        break;
      case 5:
        url = this.NEW_PLAYER;
        break;
      case 6:
        url = this.BLINDS;
        break;
      case 7:
        url = "";
        break;
      case 8:
        url = "";
        break;
    }
    if (getPostDel == 1) {
      if (objectId == "") {
        this.http.get<any>(url + objectId, {headers: headers}).subscribe();
      }
      return this.http.get<any>(url + objectId, {headers: headers});
    } else if (getPostDel == 2) {
      return this.http.post<any>(url, data, {headers: headers});
    } else {
      return this.http.delete(url + objectId, {headers: headers})
    }
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }


}
