package com.wolfe;

/**
 * Created by Jeremy on 10/11/2016.
 */

public class Card {


    private int sequence;
    private String rank;
    private String suit;


    public Card(int sequence, String rank, String suit){

        this.sequence = sequence;       // 1=Ace... 13=King
        this.rank = rank;               // A, 2, 3,... J, Q, K
        this.suit = suit;               // Hearts=H, Clubs=C, Diamonds=D, Spades=S
    }


    protected static String convertRank(int i) {

        switch (i) {

            case 1: {
                return "A";
            }
            case 2: {
                return "2";
            }
            case 3: {
                return "3";
            }
            case 4: {
                return "4";
            }
            case 5: {
                return "5";
            }
            case 6: {
                return "6";
            }
            case 7: {
                return "7";
            }
            case 8: {
                return "8";
            }
            case 9: {
                return "9";
            }
            case 10: {
                return "10";
            }
            case 11: {
                return "J";
            }
            case 12: {
                return "Q";
            }
            case 13: {
                return "K";
            }
        }
        return null;  // should never be reached
    }


    @Override
    public String toString() {
        return sequence + ":" + rank +  " of " + suit;
    }




    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

}
