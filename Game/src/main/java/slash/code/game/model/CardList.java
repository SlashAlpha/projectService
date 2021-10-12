package slash.code.game.model;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CardList{
    List<Card> cardList;

    public CardList() {
        this.cardList = new ArrayList<>();
    }

    public List<Card> getCardList() {
        return cardList=new ArrayList<Card>();
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }
}
