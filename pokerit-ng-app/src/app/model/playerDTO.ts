import {Deserializable} from "./Deserializable";
import {Card} from "./Card";

export class PlayerDTO implements Deserializable {
  id: string;
  name :string;
  age:number;
  bank:number;
  bet:number;
  one:Card;
  two:Card;


  constructor(id: string, name: string, age: number, bank: number, bet: number, one: Card, two: Card) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.bank = bank;
    this.bet = bet;
    this.one = one;
    this.two = two;
  }

  deserialize(input: any): this {
    Object.assign(this,input);
    return this;
  }

}
