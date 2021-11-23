export class User {

  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string
  age: number;


  constructor(email: string, password: string, firstName: string, lastName: string, phoneNumber: string, age: number) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.age = age;
  }
}
