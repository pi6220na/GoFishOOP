package com.wolfe;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jeremy on 10/11/2016.
 */
public class Deck {

    static String HEARTS = "H";
    static String CLUBS = "C";
    static String DIAMONDS = "D";
    static String SPADES = "S";

    static int cardCountRemaining = 52;

    //static HashMap<Integer,Card> deck = new TreeMap<>();
    static ArrayList<Card> deck = new ArrayList<>();

    public static void buildDeck() {
        for (int i = 1; i < 14; i++) {
            String j = Card.convertRank(i);
            Card card1 = new Card(i, j, HEARTS);
            Card card2 = new Card(i, j, CLUBS);
            Card card3 = new Card(i, j, DIAMONDS);
            Card card4 = new Card(i, j, SPADES);
            deck.add(card1);
            deck.add(card2);
            deck.add(card3);
            deck.add(card4);
            }
        }

    public static void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public static Card getACard() {

        if (deck.size() > 0) {
            cardCountRemaining--;
            return deck.remove(0);
        }

        return null;
    }

    static void printDeck() {

        for ( Card card : deck ) {
            System.out.print(card.getRank() + card.getSuit() + " ");
        }
        System.out.println();
    }

    static void printVerboseDeck() {

        String pString;

        int i = 1;

        for ( Card card : deck ) {


            if (card.getRank().equals("A")) {
                pString = "Ace"; }
            else if (card.getRank().equals("J")) {
                pString = "Jack"; }
            else if (card.getRank().equals("Q")) {
                pString = "Queen"; }
            else if (card.getRank().equals("K")) {
                pString = "King"; }
            else {
                pString = card.getRank();
            }

            if (card.getSuit().equals("H")) {
                pString += " of Hearts"; }
            else if (card.getSuit().equals("C")) {
                pString += " of Clubs"; }
            else if (card.getSuit().equals("D")) {
                pString += " of Diamonds"; }
            else if (card.getSuit().equals("S")) {
                pString += " of Spades"; }

            System.out.println(i + ": " + pString);
            i++;
        }
    }
}


