import {Component, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {User} from "../model/User";
import {LoginUser} from "../model/login-user";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User;

  login: LoginUser;


  constructor(private apiService: ApiService) {
    this.user = new User("", "", "", "", "", new Date());
    this.login = new LoginUser("", "");
  }

  ngOnInit(): void {

  }


  loginUser() {

    this.apiService.newLogin(this.login);

  }

}
