import {Component, OnInit} from '@angular/core';
import {User} from "../model/User";
import {LoginUser} from "../model/login-user";
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  login: LoginUser;


  constructor(private apiService: ApiService) {
    this.user = new User("", "", "", "", "", new Date());
    this.login = new LoginUser("", "");
  }

  ngOnInit(): void {
  }

  registerUser() {
    this.apiService.registerUser(this.user);
  }

}
