package slash.code.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Entity
    public class Card {

        @Id
        UUID id;
        Timestamp createdDate;
        String color;
        Integer value;
        Integer faceVal;
        String description;
        Integer number;




        public Card(String color,Timestamp createdDate, Integer value, Integer faceVal, String description) {
            this.color = color;
            this.value = value;
            this.faceVal = faceVal;
            this.description = description;
            this.createdDate=createdDate;

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
            String card = this.value + "/" + this.color + "/" + this.description + "/" + this.faceVal+"/"+this.createdDate+"/"+this.id;
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

