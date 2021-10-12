export class Player {
  id:String=new String("");
  name:string;
  age:number;
  bank:number;


  constructor(name: string, age: number, bank: number) {
    this.name = name;
    this.age = age;
    this.bank = bank;
  }
}
