package slash.code.dealer.services;

import slash.code.dealer.deck.Card;

import java.util.List;

public interface CardService {

    List<Card> pokerDeck(List<Card> deckCards);
    List<Card> blackJackDeck(List<Card> deckCards);
}
