export class User {

  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string
  dateOfBirth: Date;


  constructor(email: string, password: string, firstName: string, lastName: string, phoneNumber: string, dateOfBirth: Date) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
  }


}
