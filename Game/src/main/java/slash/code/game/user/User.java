package slash.code.game.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import slash.code.game.model.BaseEntity;
import slash.code.game.model.Player;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
@Table(name = "user", schema = "auth")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    String firstName;
    String lastName;
    String email;
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


    @OneToMany
    private List<Player> UserPlayers;


}
