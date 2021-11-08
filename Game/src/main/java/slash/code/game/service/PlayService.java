package slash.code.game.service;

import slash.code.game.model.Game;
import slash.code.game.model.Play;
import slash.code.game.model.Player;

import java.util.List;
import java.util.UUID;

public interface PlayService extends ServiceBaseInterface<Play, UUID> {

    //temporary
    void addPlayers(Play play);

    Player addPLayerToPlay(UUID playId, UUID PlayerId);


    Game newGame(UUID play) throws InterruptedException;

    Game saveGame(Game game, Play play);

     Play getPLay(UUID playId);

     Play savePlay(Play play);
     void dealerBlind(Integer smallBlind);

     UUID newPlay();

     Integer getBlind();
     List<Player> getPlayPlayers(UUID playId);






}
