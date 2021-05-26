package slash.code.dealer.services;

import org.springframework.stereotype.Service;
import slash.code.dealer.deck.Card;
import slash.code.dealer.deck.CardRepository;
import slash.code.dealer.deck.Deck;

import java.util.List;

@Service
public class CardServices implements CardService {
    CardRepository cardRepository;

    public CardServices(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> pokerDeck(List<Card> deckCards) {
      int count=0;
            if (deckCards.isEmpty() ) {
                count = 0;

               CardData baseCards = new CardData();
                for (String color : baseCards.color
                ) {
                    for (Integer values : baseCards.value
                    ) {
                        if (values == 1) {
                            Card ace = new Card(color, 10, baseCards.faceValue[4], "Ace");
                            cardRepository.save(ace);
                            deckCards.add(ace);
                            count += 1;

                        } else {
                            Card card1 = new Card(color, values, baseCards.faceValue[0], baseCards.description[0]);
                            cardRepository.save(card1);
                            deckCards.add(card1);
                            count += 1;
                        }
                    }
                    for (int i = 1; i < 4; i++) {

                        Card faceCard = new Card(color, 10, baseCards.faceValue[i], baseCards.description[i]);
                        cardRepository.save(faceCard);
                        deckCards.add(faceCard);
                        count += 1;

                    }

                }
                if (count == 52) {
                    System.out.println("Deck is full and verified");


                }
            }

            return deckCards;

        }

    @Override
    public List<Card> blackJackDeck(List<Card> deckCards) {
        int count=0;
        if (deckCards.isEmpty() ) {
            count = 0;

            CardData baseCards = new CardData();
            for (String color : baseCards.color
            ) {
                for (Integer values : baseCards.value
                ) {
                    if (values == 1) {
                        Card ace = new Card(color, 1, 10, "Ace");
                        cardRepository.save(ace);
                        deckCards.add(ace);
                        count += 1;
                    } else {
                        Card card1 = new Card(color, values, baseCards.faceValue[0], baseCards.description[0]);
                        cardRepository.save(card1);
                        deckCards.add(card1);
                        count += 1;
                    }
                }
                for (int i = 1; i < 4; i++) {

                    Card faceCard = new Card(color, 10, baseCards.faceValue[0], baseCards.description[i]);
                    cardRepository.save(faceCard);
                    deckCards.add(faceCard);
                    count += 1;

                }

            }
            if (count == 52) {
                System.out.println("Deck is full and verified");

            }
        }
        return deckCards;
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
