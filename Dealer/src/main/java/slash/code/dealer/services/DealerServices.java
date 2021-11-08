package slash.code.dealer.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import slash.code.dealer.config.JmsConfig;
import slash.code.dealer.deck.Card;
import slash.code.dealer.deck.Dealer;
import slash.code.dealer.deck.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DealerServices implements DealerService {




   private Dealer dealer;
   private Deck deck;
    Integer playerCount=0;
    JmsTemplate jmsTemplate;
   private static UUID dealerId;


  private   ArrayList<UUID> playersId=new ArrayList<>();
   private Integer playerServed=0;


    public DealerServices(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.NEW_PLAYER)
    public void newPlayerListener(@Payload String message) {
        if(playerCount<7) {
            this.playersId.add(UUID.fromString(message));//
            this.playerCount += 1;
        System.out.println("new Player :" + message);

        }

    }


    @Override
    public Deck blackJackDeck() {

        dealer = new Dealer();

        Deck deck=new Deck();
         this.deck.buildNewGameDeck();
         return this.deck;
    }


    @JmsListener(destination = JmsConfig.POKER_GAME)
    private void pokerNewGameListener(@Payload String gameId) {
        if (this.dealer == null) {
            this.dealer = new Dealer();
        }
        this.deck = new Deck();
        this.deck.buildNewGameDeck();
        this.dealer.setRiverW1(false);
        this.dealer.setRiverW2(false);
        this.dealer.setRiverW3(false);

        jmsTemplate.convertAndSend(JmsConfig.BLIND, 5);
    }

    @Override
    public List<Card> riverCards(){
        List<Card> cards=new ArrayList<>();
        if (!this.dealer.isRiverW1() && !this.dealer.isRiverW2()) {
            for (int i = 0; i < 3; i++) {
                Card card = this.deck.getOneCardFromDeck();
                cards.add(card);
            }
        }
        this.dealer.setRiverW1(true);
        return  cards;
        }
        @Override
        public Card riverWS(Integer count) {
            if (count == 0 && !this.dealer.isRiverW2() && this.dealer.isRiverW1()) {
                this.dealer.setRiverW2(true);
                return this.deck.getOneCardFromDeck();
            } else if (count == 1 && !this.dealer.isRiverW3() && this.dealer.isRiverW2()) {
                this.dealer.setRiverW3(true);
                return this.deck.getOneCardFromDeck();

            }
            return null;
        }






//    @Override
//    public Integer gameSet() {
//
//        if(deck.getPoker()){return 1;}
//        else if (deck.getBlackJack()){return 2;}
//        return 0;
//    }

    @Override
    public Dealer getDealer() {
        return this.dealer;
    }




    @Override
    public List<Card> getPLayerCards(UUID playerId) {
        List<Card> cards= new ArrayList<>();


        for (UUID player:this.playersId
             ) {if(player.equals(playerId)){

            Card card=this.deck.getOneCardFromDeck();
            cards.add(card);
            card=this.deck.getOneCardFromDeck();
            cards.add(card);
            this.playerServed+=1;
            this.playersId.remove(player);
            return cards;}



        }
        System.out.println("player not found");
return null;

        }





}
