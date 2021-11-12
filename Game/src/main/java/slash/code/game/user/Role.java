package slash.code.game.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import slash.code.game.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "role", schema = "auth")
public class Role extends BaseEntity {

    private String name;
}
