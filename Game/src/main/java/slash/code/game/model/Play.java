package slash.code.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "play")
public class Play extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    List<Game> games;

    @OneToMany
    List<Player> players;


    Integer smallBlind;

}
