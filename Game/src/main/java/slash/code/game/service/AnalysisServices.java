package slash.code.game.service;


import lombok.Getter;
import org.springframework.stereotype.Service;
import slash.code.game.model.Card;
import slash.code.game.model.Player;

import java.util.*;

@Service
public class AnalysisServices implements AnalysisService {

    ArrayList<Card> cards;
    private final String[] combination = {"High Card", "Pair", "Two Pair", "Three Of A Kind", "Straight", "Flush", "Full house", "Four Of A Kind", "Straight Flush", "Royal Flush"};
    private final Integer[] combinationScore = {10, 30, 50, 70, 90, 110, 130, 150, 170, 190};


    public AnalysisServices() {
        this.cards = new ArrayList<Card>();
    }

    public void compareCards(ArrayList<Card> cards, Player player) {
        ArrayList<String> flushList = new ArrayList<>();
        ArrayList<Integer> valueList = new ArrayList<>();
        for (Card card : cards
        ) {
            valueList.add(cardValue(card));
            flushList.add(card.getColor());
        }
        Integer[] pairSys = pokerSystem(valueList, flushList, cards);
        System.out.println(combination[pairSys[0]] + " with cards " + pairSys[1] + " " + pairSys[2]);
        player.setScore(pairSys[1] + combinationScore[pairSys[0]]);
        System.out.println("Player score is :" + player.getScore());

    }

    private Integer cardValue(Card card) {
        Integer faceval = 0;
        if (card.getFaceVal() > 0) {
            faceval = card.getFaceVal();
        }
        Integer value = card.getValue() + faceval;
        return value;
    }


    private Integer[] pokerSystem(ArrayList<Integer> values, ArrayList<String> colors, ArrayList<Card> cards) {
        ArrayList<Integer> origval = values;
        ArrayList<String> origcol = colors;

        values.sort(Collections.reverseOrder());
        colors.sort(Collections.reverseOrder());
        Integer[] sortedVal = values.toArray(new Integer[0]);
        String[] color = colors.toArray(new String[0]);
        Integer[] count = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] results = {0, 0, 0};
        String prevColor = "";
        Integer[] firstAndLast = {0, 0, 0, 0};
        boolean aceEvent = false;
        boolean colorStraight = true;
        String colorFlush = "";
        int colorful = 0;

        for (int i : values) {

            if (count[0] == 0) {
                count[1] = i;
                prevColor = color[count[0]];
            } else {
                //Flush and Straight system
                if (count[5] < 5 || count[6] < 5) {
                    //flush
                    if (color[count[0]].equals(prevColor)) {
                        count[6] += 1;
                        colorFlush = prevColor;
                        if (count[6] >= 4) {
                            count[8] = 4;
                            int x = 0;
                            for (String col : origcol) {
                                if (color[count[0]].equals(col) && origval.get(x) > count[7]) {
                                    count[7] = origval.get(x);
                                    x += 1;
                                }
                            }
                            firstAndLast[3] = i;
                            firstAndLast[2] = count[7];
                        }
                    } else if (!color[count[0]].equals(prevColor)) {
                        count[6] = 0;
                        if (count[8] != 4) {
                            count[7] = 0;
                        }
                    }
                    prevColor = color[count[0]];
                    //straight

                    if (count[1] - i == 1 && count[5] < 4) {
                        count[5] += 1;

                        if (values.indexOf(i) < 6) {
                            int nextI = i - 1;
                            int countcol = 0;
                            if (!(values.indexOf(nextI) == values.indexOf(i) + 1) || values.indexOf(i) == 5) {
                                for (Card card : cards) {
                                    if (!(card.getColor() == color(cards)) && (card.getValue() + card.getFaceVal()) == i) {
                                        countcol += 1;
                                        if (countcol == 2) {
                                            colorStraight = false;
                                        }
                                    }
                                }
                            }
                        }

                        if (count[5] == 3 && i == 2 && values.contains(14)) {
                            aceEvent = true;
                            System.out.println(aceEvent);
                        } else if (count[5] == 4 && !aceEvent) {
                            firstAndLast[1] = sortedVal[count[0]];
                            firstAndLast[0] = sortedVal[count[0]] + 4;
                        }


                    } else if (count[1] - i != 1 && count[1] - i != 0 && count[5] < 4 && !aceEvent) {
                        count[5] = 0;
                    }

                    if (aceEvent) {
                        count[5] = 4;
                        firstAndLast[1] = 1;
                        firstAndLast[0] = 5;
                    }
                } //pairing System
                if (count[1] - i == 0) { //count[1]=previous i
                    if (results[1] <= i) {
                        results[0] += 1;//counter of pair
                        results[1] = i;//numero carte pair
                        count[3] += 1;
                    } else if (results[2] <= i) {
                        count[2] += 1;
                        results[0] += 1;
                        results[2] = i;
                        if (count[2] >= 3) {
                            results[1] = i;
                        }
                    }
                }
            }
            if (values.size() - count[0] == 1 && results[0] == 0) {
                results[1] = count[4];
            }
            count[1] = i;
            count[0] += 1;

        } //results system

        if (count[5] >= 4 && count[8] == 4 && firstAndLast[0] == 14) {
            results[0] = 9;
            results[1] = firstAndLast[0];
            results[2] = firstAndLast[1];
        } else if (count[5] >= 4 && count[8] == 4 && colorStraight) {
            results[0] = 8;
            results[1] = firstAndLast[0];
            results[2] = firstAndLast[1];
        } else if (results[0] >= 3 && (count[2] >= 3 || count[3] >= 3)) {
            results[0] = 7;
            results[2] = 0;
        } else if (results[1] > 0 && results[2] > 0 && results[0] >= 2 && (count[2] == 2 || count[3] == 2)) {
            results[0] = 6;
        } else if (count[8] == 4 && results[0] < 5) {
            results[0] = 5;
            results[1] = firstAndLast[2];
            results[2] = firstAndLast[2];
        } else if (count[5] == 4 && results[0] < 3) {
            results[0] = 4;
            results[1] = firstAndLast[0];
            results[2] = firstAndLast[1];
        } else if (results[1] > 0 && results[2] == 0 && results[0] == 2) {
            results[0] = 3;
        } else if (results[0] < 1) {
            results[1] = sortedVal[0];
        }

        return results;


    }

    public String color(ArrayList<Card> cards) {
        final String[] color = {"Diamond", "Spade", "Heart", "Club"};
        Integer[] results = {0, 0, 0, 0};
        for (Card card :
                cards) {
            if (card.getColor().equals(color[0])) {
                results[0] += 1;
                if (results[0] == 5) {
                    return color[0];
                }
            }
            if (card.getColor().equals(color[1])) {
                results[1] += 1;
                if (results[0] == 5) {
                    return color[1];
                }
            }
            if (card.getColor().equals(color[2])) {
                results[2] += 1;
                if (results[0] == 5) {
                    return color[2];
                }
            }
            if (card.getColor().equals(color[3])) {
                results[3] += 1;
                if (results[0] == 5) {
                    return color[3];
                }
            }
        }
        return null;
    }

    public ArrayList<Card> colorCard(ArrayList<Card> cards) {
        final String[] color = {"Diamond", "Spade", "Heart", "Club"};
        ArrayList<Card> colorDiamonds = new ArrayList<>();
        ArrayList<Card> colorSpade = new ArrayList<>();
        ArrayList<Card> colorHeart = new ArrayList<>();
        ArrayList<Card> colorClub = new ArrayList<>();
        int count = 0;
        for (Card card :
                cards) {
            if (card.getColor().equals(color[0])) {
                colorDiamonds.add(card);
                if (colorDiamonds.size() >= 5 && count == 6) {
                    return colorDiamonds;
                }
            }
            if (card.getColor().equals(color[1])) {
                colorSpade.add(card);
                ;
                if (colorSpade.size() >= 5 && count == 6) {
                    return colorSpade;
                }
            }
            if (card.getColor().equals(color[2])) {
                colorHeart.add(card);
                ;
                if (colorHeart.size() >= 5 && count == 6) {
                    return colorHeart;
                }
            }
            if (card.getColor().equals(color[3])) {
                colorClub.add(card);
                ;
                if (colorClub.size() >= 5 && count == 6) {
                    return colorClub;
                }
            }
            count += 1;
        }
        return null;
    }

    public ArrayList<Card> straightCard(ArrayList<Card> cards) {
        Card cardPrev = new Card(null,null,"", 0, 0, "",0);
        int count = 0;
        int straightCount = 0;
        ArrayList<Card> straightCards = new ArrayList<>();
        cards.stream().sorted(Comparator.comparingInt(Card::getValue).thenComparingInt(Card::getFaceVal));

        for (Card card : cards
        ) {
            if (count > 0) {

                if ((card.getValue() + card.getFaceVal()) - (cardPrev.getValue() + cardPrev.getFaceVal()) == 1) {
                    if (straightCards.contains(cardPrev)) {
                        straightCards.add(card);
                    } else {
                        straightCards.add(card);
                        straightCards.add(cardPrev);
                    }
                    straightCount += 1;
                }
            } else {
                cardPrev = card;
            }


            count += 1;
        }

        if (straightCount >= 4) {
            return straightCards;
        }
        return null;

    }

    @Override
    public List<Card> countPair(List<Card> cards) {
       List<Card> pairCards = new ArrayList<>();
       List<Card> comparingCards = cards;

        boolean pair = false;
        for (Card card : cards
        ) {
            comparingCards.remove(card);
            for (Card card1 : comparingCards) {
                if (card.getValue() + card.getFaceVal() == card1.getFaceVal() + card1.getValue()) {
                    if (!pairCards.contains(card1)) {
                        pairCards.add(card1);
                    }
                    if (!pairCards.contains(card)) {
                        pairCards.add(card);
                    }
                }

            }


        }
        return pairCards;

    }


}

