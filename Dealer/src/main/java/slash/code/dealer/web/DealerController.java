package slash.code.dealer.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import slash.code.dealer.deck.Card;
import slash.code.dealer.services.DealerService;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RequestMapping("/api/v1/dealer/")
@RestController
public class DealerController {
    int count=0;
    List<UUID> players;
    Boolean firstWave=true;
    

DealerService dealerService;

    public DealerController(DealerService dealerService) {
        this.dealerService = dealerService;
    }


//    @GetMapping("pokerCard")
//    public ResponseEntity<Card> pokerCardToGame(){
//        if (dealerService.gameSet()==1){
//            return new ResponseEntity<Card>(dealerService.dealOne(), HttpStatus.OK);}
//
//
//        return new ResponseEntity<Card>(HttpStatus.CONFLICT);
//
//    }
@GetMapping("riverW1")
private ResponseEntity<List<Card>> riverCardsToGame() {
    return new ResponseEntity<List<Card>>(dealerService.riverCards(), HttpStatus.OK);
}

    @GetMapping("riverW2")
    private ResponseEntity<Card> riverCardsToGame2() {
        return new ResponseEntity<Card>(dealerService.riverWS(0), HttpStatus.OK);

    }

    @GetMapping("riverW3")
    private ResponseEntity<Card> riverCardsToGame3() {
        return new ResponseEntity<Card>(dealerService.riverWS(1), HttpStatus.OK);

    }

    @GetMapping("player{playerId}")
    private ResponseEntity<List<Card>> cardsToPlayer(String version, @PathVariable String playerId) {
        List<Card> cards = dealerService.getPLayerCards(UUID.fromString(playerId));
        if (cards != null) {
            return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Card>>(HttpStatus.BAD_REQUEST);
        }
    }


//    @GetMapping("blackJackCard")
//    public ResponseEntity<Card> blackJackCardToGame(@PathVariable UUID playerId,@PathVariable Integer numberOfPlayer,@PathVariable Integer attempt,@PathVariable Integer wave){
//        if (dealerService.gameSet()==2){
//            return null;
//
//        }
//        return new ResponseEntity<Card>(HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("blind")
    public ResponseEntity<Integer> blinds(){
        return new ResponseEntity<Integer>(5,HttpStatus.OK);


    }

}
