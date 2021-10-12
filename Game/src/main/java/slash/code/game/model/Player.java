package slash.code.game.model;


import lombok.*;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Setter
public class Player extends BaseEntity {



    String name;
    Integer age;
    Integer number;
    Integer gamePlayed;
    Integer bet;
    Integer success;

    Integer bank;
    Integer score;
    boolean dealer = false;
    boolean sb = false;
    boolean bb = false;

    @OneToMany
    List<Card>cards;

    @OneToOne(cascade = CascadeType.ALL)
    Card one;
    @OneToOne(cascade = CascadeType.ALL)
    Card two;

    @ManyToOne
    Play plays;




    public Integer betting(Integer bet) {
        this.bank = bank - bet;
        this.bet = bet;
        return bet;
    }

}
