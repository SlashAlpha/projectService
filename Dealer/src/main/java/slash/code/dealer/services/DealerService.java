package slash.code.dealer.services;

import slash.code.dealer.deck.Card;
import slash.code.dealer.deck.Dealer;
import slash.code.dealer.deck.Deck;

import java.util.List;
import java.util.UUID;

public interface DealerService {


    Deck blackJackDeck();

//    Integer gameSet();
    List<Card> riverCards();
    Card riverWS(Integer count);
    Dealer getDealer();

    List<Card> getPLayerCards(UUID player );


}
