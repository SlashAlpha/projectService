package slash.code.dealer.services;

import slash.code.dealer.deck.Card;
import slash.code.dealer.deck.Deck;

public interface DealerService {

    Deck pokerDeck();
    Deck blackJackDeck();
    Card dealOne();
    Integer gameSet();

}
