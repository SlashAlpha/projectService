package slash.code.game.user;

import lombok.Getter;
import lombok.Setter;
import slash.code.game.model.BaseEntity;
import slash.code.game.model.Player;

import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
public class User extends BaseEntity {

    String firstName;
    String lastName;
    String password;


    @OneToMany
    private List<Player> UserPlayers;


}
