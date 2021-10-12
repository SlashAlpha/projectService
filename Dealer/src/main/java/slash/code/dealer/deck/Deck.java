package slash.code.dealer.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor

public class Deck extends BaseEntity {



    private List<Card> cards;
    Integer count = 0;
    Integer turns = 0;
    Boolean blackJack=false;
    Boolean poker=false;


    StringBuffer sb = new StringBuffer();
    private Boolean verified;

    public Deck() {
        this.cards = new ArrayList<Card>();
        this.verified=false;
    }


  public List<Card> buildNewGameDeck(){
         this.cards = new ArrayList<>();
        int count = 0;
        if (this.cards.isEmpty()) {
            count = 0;
          CardData baseCards = new CardData();
            for (String color : baseCards.color
            ) {
                for (Integer values : baseCards.value
                ) {
                    if (values == 1) {
                        count += 1;
                        Card ace = new Card(UUID.randomUUID(),color, 10, baseCards.faceValue[4], "Ace", count);
                        this.cards.add(ace);
                    } else {
                        count += 1;
                        Card card1 = new Card(UUID.randomUUID(),color, values, baseCards.faceValue[0], baseCards.description[0], count);
                        this.cards.add(card1);
                    }                }
                for (int i = 1; i < 4; i++) {
                    count += 1;
                    Card faceCard = new Card(UUID.randomUUID(),color, 10, baseCards.faceValue[i], baseCards.description[i], count);
                    this.cards.add(faceCard);
                }
            }
            if (count == 52) {
                System.out.println("Deck is full and verified");
            }
        }


        this.shuffleCard(1);
        this.setPoker(true);
        this.setVerified(true);

        return this.cards;
    }




    public void shuffleCard(int x) {
        for (int i = 0; i < x; i++) {
            Collections.shuffle(this.cards);
        }

    }

    public Card getOneCardFromDeck() {
        Card randomCard = this.cards.get(0);

        this.cards.remove(0);

        return randomCard;

    }



    public List<Card> getCards() {
        return cards;
    }

    private void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }



    public String riverFill() {
        Card cardA = this.getCards().get(0);
        sb.append(cardA.value + "//" + cardA.color + "//" + cardA.description + "//" + cardA.faceVal + "//£//");

        this.cards.remove(cardA);
        return sb.toString();
    }


    @Override
    public String toString() {
        for (Card card : this.cards) {
            System.out.println(card.toString());
        }
        return "deck has " + this.count + " cards";
    }



    public Boolean getBlackJack() {
        return blackJack;
    }

    public Boolean getPoker() {
        return poker;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }



    private class CardData {
        final String[] color = {"Diamond", "Spade", "Heart", "Club"};
        final Integer[] value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final Integer[] faceValue = {0, 1, 2, 3, 4};
        final String[] description = {"", "Jack", "Queen", "King"};

        public CardData() {
        }
    }
}
