package slash.code.game.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import slash.code.game.config.JmsConfig;
import slash.code.game.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
public class PlayServices implements PlayService {

    GameRepository gameRepository;
    GameService gameService;
    PlayRepository playRepository;
    PlayerService playerService;
    Integer blind=0;


    public PlayServices(GameRepository gameRepository, GameService gameService, PlayRepository playRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.playRepository = playRepository;
        this.playerService = playerService;
    }

    @Override
    public void addPlayers(Play play) {


        play.setPlayers((playerService.getPlayers()));
                    playRepository.save(play);   }


    @Override
    public Player addPLayerToPlay(UUID playId, UUID playerId) {
        Play play=playRepository.findById(playId).get();
     Player player=playerService.getPlayer(playerId);
     play.getPlayers().add(player);
     playRepository.save(play);
     return player;
    }


   @Override
    public UUID newPlay(){

        Play play = new Play(new ArrayList<Game>(), new ArrayList<Player>(),this.blind);

        return   savePlay(play).getId();
    }

    @Override
    public Game newGame(UUID playId) {

        Play play= getPLay(playId);
        play.setSmallBlind(this.blind);

        Game game = gameService.newGame();
        System.out.println("GAME SAVED");
        play.getGames().add(game);
        savePlay(play);
        return game;


    }

    @JmsListener(destination = JmsConfig.BLIND)
    @Override
    public void dealerBlind( @Payload Integer smallBlind) {
       this.blind=smallBlind;   }

    @Override
    public Game saveGame(Game game,Play play) {
      ;
        play.getGames().add(game);

        return game;
    }

    @Override
    public Play getPLay(UUID playId) {
        return playRepository.findById(playId).get();
    }

    @Override
    public Play savePlay(Play play) {
       return playRepository.save(play);
    }

    @Override
    public Integer getBlind() {
        return blind;
    }

    @Override
    public List<Player> getPlayPlayers(UUID playId) {
        Play play=playRepository.findById(playId).get();
        return play.getPlayers();
    }
}


