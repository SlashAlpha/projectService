package slash.code.game.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import slash.code.game.config.messaging.JmsConfig;
import slash.code.game.config.security.SecurityUti;
import slash.code.game.model.Card;
import slash.code.game.model.Game;
import slash.code.game.model.GameRepository;
import slash.code.game.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameServices implements GameService{

    GameRepository gameRepository;

    PlayerService playerService;
    RestTemplate restTemplate;
    JmsTemplate jmsTemplate;
    CardService cardService;
    AnalysisService analysisService;

    public static final String BLIND_PATH="http://localhost:8080/api/v1/dealer/blind";
    public final static String RIVER_PATH1 = "http://localhost:8080/api/v1/dealer/riverw1";
    public final static String RIVER_PATH2 = "http://localhost:8080/api/v1/dealer/riverw2";
    public final static String CARD_PATH3 = "http://localhost:8080/api/v1/dealer/riverw3";

    public GameServices(AnalysisService analysisService,GameRepository gameRepository, PlayerService playerService, RestTemplateBuilder restTemplate, JmsTemplate jmsTemplate, CardService cardService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.restTemplate = restTemplate.build();
        this.jmsTemplate = jmsTemplate;
        this.cardService = cardService;
        this.analysisService=analysisService;
    }

    @Override
    public Game newGame() {

       Game game=new Game();

        game.setCards(new ArrayList<Card>());
        game.setBetsBank(0);
        gameRepository.save(game);
        jmsTemplate.convertAndSend(JmsConfig.POKER_GAME,game.getId().toString());
        return game;
    }


    @Override
    public Game blindToPlayers(Game game, List<Player>players) {

        Integer blind = restTemplate.exchange(BLIND_PATH, HttpMethod.GET, SecurityUti.restEntityTokenedHeaders(SecurityUti.getTokenFromDealer()), Integer.class).getBody();

        restTemplate.getForObject(BLIND_PATH, Integer.class);
        playerService.setBlind(players, blind);
        game.setBetsBank(blind + (blind * 2));


        // playerService.cardDistribution(players);
        return game;
    }

    @Override
    public void playersBet(List<Player>players) {
        for (Player player:players) {
        }

    }

    @Override
    public List<UUID> riverCards1(Game game) {
        List<Card> cards= cardService.firstWaveRiverCards();
        List<UUID> cardList=new ArrayList<>();
        for (Card card:cards
        ) {
            cardList.add(card.getId());
        }
        game.setCards(cards);
        gameRepository.save(game);
        return cardList;
    }

    @Override
    public UUID riverCard2(Game game) {
        Card card=cardService.secondWaveRiverCards();
        game.getCards().add(card);
        gameRepository.save(game);
     return card.getId();
    }

    @Override
    public UUID riverCard3(Game game) {
        Card card=cardService.thirdWaveRiverCards();
        game.getCards().add(card);
        gameRepository.save(game);
        return card.getId();
    }

    @Override
    public List<Card> cardDispatch(UUID playerId) {
        Player player = playerService.getPlayer(playerId);
        List<Card> cards = cardService.playerCards(playerId);
        player.setCards(cards);
        playerService.savePlayer(player);
        return cards;
    }

//    public void analysisPlayersCards(Player player){
//    List<Card> cards=new ArrayList<>();
//        cards.addAll(player.getCards());
//   List<Card>countedCard=  analysisService.countPair(cards);
//        if (countedCard.size()>0){
//            System.out.println(player.getName()+" has a pair of "+player.getOne().toString());
//        }
//
//    }

}
