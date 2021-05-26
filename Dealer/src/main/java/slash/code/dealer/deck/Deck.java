package slash.code.dealer.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Deck {

    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;
    @OneToMany
    @JoinTable(name = "deck_cards",joinColumns = {@JoinColumn(name = "deck_id")},
            inverseJoinColumns = {@JoinColumn(name = "card_id")})
    private List<Card> cards;
    Integer count = 0;
    Integer turns = 0;
    Boolean blackJack=false;
    Boolean poker=false;
    @OneToMany
    @JoinTable(name = "used_deck_cards",joinColumns = {@JoinColumn(name = "deck_id")},
            inverseJoinColumns = {@JoinColumn(name = "card_id")})
    private List<Card> usedCard;
    StringBuffer sb = new StringBuffer();
    private Boolean verified;

    public Deck() {
        this.cards = new ArrayList<Card>();

        usedCard = new ArrayList<>();
        this.verified=false;



    }




    public void shuffleCard(int x) {
        for (int i = 0; i < x; i++) {
            Collections.shuffle(cards);
        }

    }

    public Card getOneCardFromDeck() {
        Card randomCard = this.cards.get(0);
        usedCard.add(randomCard);
        cards.remove(0);

        return randomCard;

    }
    public Boolean verifyCard(Card card){
        Boolean verification= false;
        for (Card card1:usedCard) {if(card1.id.equals(card.id)){verification=true;}

        }
        return verification;
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
        this.usedCard.add(cardA);
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
}
