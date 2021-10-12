package slash.code.game.viewModel;


import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {


    String color;
    Integer value;
    Integer faceVal;
    String description;
    Integer number;
}
