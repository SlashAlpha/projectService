package slash.code.dealer.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Dealer extends BaseEntity {

    boolean riverW1;
    boolean riverW2;
    boolean riverW3;
    int playerCount;
    boolean players;
    Integer blind;





}
