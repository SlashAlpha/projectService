package slash.code.game.service;


import slash.code.game.model.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {
    Card getOnePokerCard();
    void saveCard(Card card);
    List<Card> firstWaveRiverCards();
    Card secondWaveRiverCards();
    Card thirdWaveRiverCards();
    List<Card> playerCards(UUID playerId);
    Card getOneCard(UUID cardId);
}
