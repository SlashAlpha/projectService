package slash.code.game.service;

import slash.code.game.model.Card;
import slash.code.game.model.Game;
import slash.code.game.model.Player;

import java.util.List;
import java.util.UUID;

public interface GameService {

    Game newGame();
    Game blindToPlayers(Game game,List<Player> players);
    void playersBet(List<Player>players);
    List<Card> cardDispatch(UUID playerId);
    List<UUID> riverCards1(Game game);
    UUID riverCard2(Game game);
    UUID riverCard3(Game game);



}
