package slash.code.game.service;

import slash.code.game.model.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    Player newPlayer(String name,Integer age,Integer bank);
    List<Player> setBlind(List<Player> player,Integer blind);
    List<Player> getPlayers();
    void savePlayer(Player player);
    Player getPlayer(UUID playerId);

 public void sendIdToDealer(UUID playerId);
    void cardDistribution(List<Player> players);


}
