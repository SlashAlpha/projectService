package slash.code.dealer.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import slash.code.dealer.config.JmsConfig;
import slash.code.dealer.deck.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealerServices implements DealerService {




   private Dealer dealer;
   private Deck deck;

    DealerRepository dealerRepository;

    Integer playerCount=0;
    JmsTemplate jmsTemplate;
   private static UUID dealerId;


  private   ArrayList<UUID> playersId=new ArrayList<>();
   private Integer playerServed=0;


    public DealerServices( DealerRepository dealerRepository, JmsTemplate jmsTemplate) {

        this.dealerRepository = dealerRepository;

        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.NEW_PLAYER)
    public UUID newPlayerListener(@Payload String message) {
        if(playerCount<7) {
            playersId.add(UUID.fromString(message));

//
            playerCount += 1;
//        System.out.println("new Player :" + message);
            //  jmsTemplate.convertAndSend(JmsConfig.BLIND,5);
        }
        return null;
    }


    @Override
    public Deck blackJackDeck() {

        dealer = new Dealer();
        dealerRepository.save(dealer);
        Deck deck=new Deck();
         this.deck.buildNewGameDeck();
         return this.deck;
    }



    @JmsListener(destination = JmsConfig.POKER_GAME)
    public String pokerGameListener(@Payload String gameId) {
            this.deck=new Deck();
            this.deck.buildNewGameDeck();



       return gameId;

 }







    @Override
    public List<Card> riverCards(){
        List<Card> cards=new ArrayList<>();
        if (!dealer.isRiverW1() &&!dealer.isRiverW2()){
            for (int i=0;i<3;i++){
                Card card=this.deck.getOneCardFromDeck();
                cards.add(card);


            }}
        dealer.setRiverW1(true);
        dealerRepository.save(dealer);
        return  cards;

        }
        @Override
        public Card riverWS(Integer count){
        if (count==0 && !dealer.isRiverW2() && dealer.isRiverW1()){
            dealer.setRiverW2(true);
            dealerRepository.save(dealer);
            return this.deck.getOneCardFromDeck();

        }
         else if (count==1 && !dealer.isRiverW3() && dealer.isRiverW2()){
                dealer.setRiverW3(true);
            dealerRepository.save(dealer);
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
        return dealer;
    }




    @Override
    public List<Card> getPLayerCards(UUID playerId) {
        List<Card> cards= new ArrayList<>();


            if(this.playersId.stream().anyMatch( player->player.equals(playerId)? playersId.remove(player):false) && playerServed<=playerCount){

                Card card=this.deck.getOneCardFromDeck();
                cards.add(card);
                card=this.deck.getOneCardFromDeck();
                cards.add(card);
                this.playerServed+=1;
                return cards;


            }


return null;

        }





}
