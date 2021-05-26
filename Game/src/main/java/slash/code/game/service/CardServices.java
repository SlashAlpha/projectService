package slash.code.game.service;

import lombok.Builder;
import org.springframework.stereotype.Service;
import slash.code.game.config.JmsConfig;
import slash.code.game.model.Card;
import slash.code.game.model.CardRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

@Service
public class CardServices implements CardService{

    CardRepository cardRepository;
    RestTemplate restTemplate;
    public final static String CARD_PATH_V1 = "http://localhost:8080/api/v1/dealer/pokerCard";

    JmsTemplate jmsTemplate;

    public CardServices(JmsTemplate jmsTemplate, CardRepository cardRepository, RestTemplateBuilder restTemplate) {
        this.cardRepository = cardRepository;
        this.restTemplate = restTemplate.build();
        this.jmsTemplate=jmsTemplate;
    }




    public void getOnePokerCard(){
        jmsTemplate.convertAndSend(JmsConfig.POKER_GAME,"poker");
        System.out.println("poker launched");

        Card card=restTemplate.getForObject(CARD_PATH_V1,Card.class);

        Card card1= Card.builder().id(card.getId()).color(card.getColor()).description(card.getDescription()).value(card.getValue()).build();

        cardRepository.save(card);
        System.out.println(card.stringify());
    }
}
