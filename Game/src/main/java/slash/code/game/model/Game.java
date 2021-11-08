package slash.code.game.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game extends BaseEntity {





    @OneToMany
    List<Card> cards;


    Integer betsBank;





}
