package slash.code.game.viewModel;

import org.springframework.stereotype.Component;
import slash.code.game.model.Card;
import slash.code.game.model.Player;
import slash.code.game.model.PlayerRepository;
import slash.code.game.service.PlayerService;

import java.util.Optional;
import java.util.UUID;

@Component
public class Mapper {

    PlayerRepository playerRepository;
    PlayerService playerService;

    public Mapper(PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }



  public   CardDTO convertToCardDTO(Card card){
        CardDTO cardDTO=CardDTO.builder()
                .value(card.getValue())
                .color(card.getColor())
                .faceVal(card.getFaceVal())
                .description(card.getDescription())
                .number(card.getNumber())
                .build();
            return cardDTO;

    }

  public   PlayerDTO convertToPlayerDTO(Player player){
        PlayerDTO playerDTO=PlayerDTO.builder()
                .id(player.getId().toString())
                .age(player.getAge())
                .bank(player.getBank())
                .bet(player.getBet())
                .cardDTO1(convertToCardDTO(player.getOne()))
                .cardDTO2(convertToCardDTO(player.getTwo()))
                .build();
    return playerDTO;
    }


  public   Player convertNewPlayerDTOToPlayer(PlayerDTO playerDTO){
       return playerService.newPlayer(playerDTO.getName(), playerDTO.getAge(), playerDTO.getBank());

    };
  public   Player convertToPlayer(PlayerDTO playerDTO){
        Optional<Player> playerOpt=playerRepository.findById(UUID.fromString(playerDTO.getId()));
        Player player=playerOpt.get();
        player.setBet(player.betting(playerDTO.getBet()));
        playerRepository.save(player);
        return player;
    }

}
