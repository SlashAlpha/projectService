package slash.code.dealer.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import slash.code.dealer.config.JmsConfig;

@Service
public class GameService {

    DealerService dealerService;
    JmsTemplate jmsTemplate;

    public GameService(DealerService dealerService, JmsTemplate jmsTemplate) {
        this.dealerService = dealerService;
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.POKER_GAME)
    public void pokerGameListener(){
        dealerService.pokerDeck();
        System.out.println("poker game launched");

    }
    @JmsListener(destination = JmsConfig.BLACKJACK_GAME)
    public void blackJackGameListener(){

        dealerService.blackJackDeck();
        System.out.println("Black Jack game launched");
            }



}
