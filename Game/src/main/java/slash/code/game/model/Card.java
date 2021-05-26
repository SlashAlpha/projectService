package slash.code.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;


    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Entity
    public class Card {

        @Id
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

