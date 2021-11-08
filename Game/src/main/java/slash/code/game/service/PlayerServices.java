package slash.code.game.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import slash.code.game.config.JmsConfig;
import slash.code.game.model.Player;
import slash.code.game.model.PlayerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServices implements PlayerService {

    PlayerRepository playerRepository;
    RestTemplate restTemplate;
    JmsTemplate jmsTemplate;
    Integer playerCount = 0;
    private CardService cardService;


    public PlayerServices(PlayerRepository playerRepository, RestTemplateBuilder restTemplate, JmsTemplate jmsTemplate, CardService cardService) {
        this.playerRepository = playerRepository;
        this.restTemplate = restTemplate.build();
        this.jmsTemplate = jmsTemplate;
        this.cardService = cardService;
    }

    @Override
    public Player newPlayer(String name, Integer age, Integer bank) {
        Player player=new Player();
        player.setGamePlayed(0);
        this.playerCount=playerCount+1;

        player=Player.builder().number(playerCount).age(age).bank(bank).name(name).build();

        playerRepository.save(player);

        return playerRepository.save(player);

    }

    @Override
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }


    @Override
    public List<Player> setBlind(List<Player> players,  Integer blind) {

        Integer bigBlind = blind * 2;
        int count = 0;
        int playerNumber = 0;
        boolean sb = false;
        boolean bb = false;

        for (Player player1 : players) {
            if(player1.isSb()==true && sb==false){player1.setSb(false);
                player1.setBet(0);
            playerRepository.save(player1);
            }
            count += 1;
            if (players.stream().count() != count) {

                //Si le joueur est big blind ,il deviendra small blind
                if (player1.isBb()) {

                    playerNumber = player1.getNumber() + 1;
                    player1.setSb(true);
                    player1.setBb(false);
                    player1.betting(blind);
                    playerRepository.save(player1);
                    sb = true;
                }
                //si le joueur precedent est small blind, le suivant est big blind
                else if (player1.getNumber() == playerNumber) {
                    player1.setSb(false);
                    player1.setBb(true);
                    player1.betting(bigBlind);
                    playerRepository.save(player1);
                    bb = true;

                }}

                //si a la fin de la loop , le precedent est sb ,le suivant est bb


                 else if (players.stream().count() == count && sb && !bb) {
                    player1.setSb(false);
                    player1.setBb(true);
                    player1.betting(bigBlind);
                    playerRepository.save(player1);
                    bb = true;
                }

                //si le dernier de la boucle est bb, il devient sb et le premier est bb
                else if (players.stream().count() == count && player1.isBb() && !bb) {
                    player1.setSb(true);
                    player1.setBb(false);
                    player1.betting(blind);
                    playerRepository.save(player1);
                    sb = true;
                    for (Player player2 : players) {
                        if (player2.getNumber() == 1) {
                            player2.setBb(true);
                            player2.betting(bigBlind);
                            playerRepository.save(player2);
                            bb = true;


                    }
                }


            }
        }
        //si aucune blind n'a ete attribué(nouveau jeu), les 2 premiers joueurs sont designés
        if (sb==false && bb==false) {
            for (Player player1 : players) {
                if (player1.getNumber() == 1) {

                    player1.setSb(true);
                    player1.setBb(false);
                    player1.betting(blind);
                    playerRepository.save(player1);
                    sb = true;
                } else if (player1.getNumber() == 2) {
                    player1.setSb(false);
                    player1.setBb(true);
                    player1.betting(bigBlind);
                    playerRepository.save(player1);
                    bb = true;
                }

            }
        }
        return players;

    }


//    @Override
//    public void cardDistribution(List<Player>players) {
//        for (Player player:players){
//            player.setOne(cardServices.getOnePokerCard());
//            player.setTwo(cardServices.getOnePokerCard());
//            playerRepository.save(player);
//        }


    @Override
    public Player getPlayer(UUID playerId) {
        return playerRepository.getById(playerId);
    }

    @Override
    public void sendIdToDealer(UUID playerId) {
        jmsTemplate.convertAndSend(JmsConfig.NEW_PLAYER, playerId.toString());
    }

    @Override
    public List<Player> getPlayers() {
      return   playerRepository.findAll();
    }
}

