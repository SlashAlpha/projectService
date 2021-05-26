package slash.code.dealer.deck;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;

     String color;
     Integer value;
     Integer faceVal;
     String description;




    public Card(String color, Integer value, Integer faceVal, String description) {
        this.color = color;
        this.value = value;
        this.faceVal = faceVal;
        this.description = description;

    }

    @Override
    public String toString() {
        if (description != "") {
            return description + " of " + color;
        } else {
            return value + " of " + color;
        }
    }

    public String stringify() {
        String card = this.value + "/" + this.color + "/" + this.description + "/" + this.faceVal;
        return card;
    }

    public Integer getValue() {
        return value;
    }


    public Integer getFaceVal() {
        return faceVal;
    }


    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }


}
