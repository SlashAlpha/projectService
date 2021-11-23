package slash.code.game.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import slash.code.game.config.security.SecurityUti;
import slash.code.game.model.Card;
import slash.code.game.model.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CardServices implements CardService{

    CardRepository cardRepository;
    RestTemplate restTemplate;
    public final static String CARD_PATH_V1 = "http://localhost:8080/api/v1/dealer/pokerCard";
    public final static String RIVER_PATH_1 = "http://localhost:8080/api/v1/dealer/riverW1";
    public final static String RIVER_PATH_2 = "http://localhost:8080/api/v1/dealer/riverW2";
    public final static String RIVER_PATH_3 = "http://localhost:8080/api/v1/dealer/riverW3";
    public final static String PLAYER_PATH= "http://localhost:8080/api/v1/dealer/player";

    JmsTemplate jmsTemplate;

    public CardServices(JmsTemplate jmsTemplate, CardRepository cardRepository, RestTemplateBuilder restTemplate) {
        this.cardRepository = cardRepository;
        this.restTemplate = restTemplate.build();
        this.jmsTemplate=jmsTemplate;

//        jmsTemplate.convertAndSend(JmsConfig.POKER_GAME,"poker");
    }


    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card getOnePokerCard() {
        Card card = restTemplate.exchange(RIVER_PATH_2, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenDto()), Card.class).getBody();

        //    restTemplate.getForObject(CARD_PATH_V1, Card.class);
        assert card != null;
        Card card1 = Card.builder().id(card.getId()).createdDate(card.getCreatedDate()).color(card.getColor()).description(card.getDescription()).value(card.getValue()).build();
        System.out.println(card.stringify());
        return cardRepository.save(card);
    }

    @Override
    public List<Card> firstWaveRiverCards() {
        List<Card>cardsList=new ArrayList<>();
        for(int i=0;i<3;i++){
            Card[] cards = restTemplate.exchange(RIVER_PATH_1, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenDto()), Card[].class).getBody();

            for (Card card : Objects.requireNonNull(cards)
            ) {
                cardRepository.save(card);
                cardsList.add(card);

            }

        }

        return cardsList;
    }

    @Override
    public Card secondWaveRiverCards() {

        Card card = restTemplate.exchange(RIVER_PATH_2, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenDto()), Card.class).getBody();

        restTemplate.getForObject(RIVER_PATH_2, Card.class);
        cardRepository.save(card);
        return card;

    }

    @Override
    public Card thirdWaveRiverCards() {
        Card card = restTemplate.exchange(RIVER_PATH_3, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenDto()), Card.class).getBody();

        cardRepository.save(card);
        return card;
    }

    @Override
    public List<Card> playerCards(UUID playerId) {
        System.out.println(playerId);
        List<Card> cardList = new ArrayList<>();
        String path = playerId.toString();
        ResponseEntity<Card[]> cards = restTemplate.exchange(PLAYER_PATH + path, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenDto()), Card[].class);
        restTemplate.getForObject(PLAYER_PATH + path, Card[].class);
        for (Card card : Objects.requireNonNull(cards.getBody())
        ) {
            Card newCard = Card.builder().id(card.getId()).faceVal(card.getFaceVal()).number(card.getNumber()).description(card.getDescription()).color(card.getColor()).value(card.getValue()).build();

            cardList.add(cardRepository.save(newCard));
        }
        return cardList;
    }

    @Override
    public Card getOneCard(UUID cardId) {
       return cardRepository.findById(cardId).get();
    }
}
