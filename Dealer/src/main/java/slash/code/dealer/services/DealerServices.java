package slash.code.dealer.services;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import slash.code.dealer.deck.Card;
import slash.code.dealer.deck.Deck;
import slash.code.dealer.deck.DeckRepository;

@Service
public class DealerServices implements DealerService {
    Deck deck=new Deck();
    CardService cardService;
    DeckRepository deckRepository;

    public DealerServices(CardService cardService, DeckRepository deckRepository) {
        this.cardService = cardService;
        this.deckRepository = deckRepository;
    }

    @Override
    public Deck pokerDeck() {
        deck.setCards(cardService.pokerDeck(deck.getCards()));

        deck.shuffleCard(1);
        deck.setPoker(true);
        deck.setVerified(true);
        deckRepository.save(deck);

        return deck;
    }

    @Override
    public Deck blackJackDeck() {
        deck.setCards(cardService.blackJackDeck(deck.getCards()));
        deck.setBlackJack(true);
        deck.setVerified(true);
        deck.shuffleCard(1);
        deckRepository.save(deck);
        return deck;
    }

    @Override
    public Card dealOne() {
        return deck.getOneCardFromDeck();
    }

    @Override
    public Integer gameSet() {
        if(deck.getPoker()){return 1;}
        else if (deck.getBlackJack()){return 2;}
        return 0;
    }
}
