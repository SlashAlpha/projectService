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
    @GetMapping("pokerCard")
    public ResponseEntity<Card> pokerCardToGame(){
        if (dealerService.gameSet()==1){
            return new ResponseEntity<Card>(dealerService.dealOne(), HttpStatus.OK);}


        return new ResponseEntity<Card>(HttpStatus.CONFLICT);

    }
    @GetMapping("blackJackCard")
    public ResponseEntity<Card> blackJackCardToGame(@PathVariable UUID playerId,@PathVariable Integer numberOfPlayer,@PathVariable Integer attempt,@PathVariable Integer wave){
        if (dealerService.gameSet()==2){
            return new ResponseEntity<Card>(dealerService.dealOne(), HttpStatus.OK);

        }
        return new ResponseEntity<Card>(HttpStatus.BAD_REQUEST);
    }

}
