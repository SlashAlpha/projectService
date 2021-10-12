package slash.code.game.viewModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PlayerDTO {

    String id;

    String name;
    Integer age;

    Integer bet;
    Integer bank;

    CardDTO cardDTO1;
    CardDTO cardDTO2;



}
