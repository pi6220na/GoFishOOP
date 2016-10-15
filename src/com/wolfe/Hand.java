package com.wolfe;

import java.util.*;

/**
 * Created by Jeremy on 10/12/2016.
 *
 * holds a hand of player cards in a treemap (sorted map)
 *
 * builds a new hand at start of game from cards dealt from deck
 *
 * adds a card or cards to hand from deck or other player
 *
 * removes a card from hand
 *
 * removes all suits of a given rank for:
 *      transfer to another player
 *      transfer to player's book
 *
 * prints out a hand
 *
 */
public class Hand {

    protected TreeMap<Integer, ArrayList<Card>> handMap;
    protected HashMap<Integer, ArrayList<Card>> bookMap;

    public Hand() {

        this.handMap = new TreeMap<Integer, ArrayList<Card>>();
        this.bookMap = new HashMap<>();

    }

    protected boolean checkForMatch(String requestedCard) {

        int index = convertRankToIndex(requestedCard);

        boolean hasCard = handMap.containsKey(index);

        return hasCard;
    }

    protected ArrayList<Card> getCardRequest(String requestedCard) {

        int index = convertRankToIndex(requestedCard);

        ArrayList<Card> theCard = handMap.get(index);

        return theCard;
    }

    protected boolean checkForBook() {

        System.out.println("entering checkForBook");

        Iterator<Map.Entry<Integer, ArrayList<Card>>> iter = handMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, ArrayList<Card>> entry = iter.next();

            ArrayList<Card> newList = new ArrayList<Card>();
            Integer key = entry.getKey();
            ArrayList<Card> list = entry.getValue();

            if (list.size() == 4) {
                makeBook(entry);
                iter.remove();
                return true;
            }
        }
        return false;
    }

    private void makeBook(Map.Entry<Integer, ArrayList<Card>> entry) {

        Integer key = entry.getKey();
        ArrayList<Card> list = entry.getValue();

        System.out.println("in Hand.makeBook");
        System.out.println("   key = " + key + " ArrayList = " + list);

        bookMap.put(key, list);
        GoFishMgr.numberOfBooksRemaining--;

    }


    protected void transferFrom(Hand sourceHand, String card) {

        int index = convertRankToIndex(card);
        // attempt to get existing key in map
        ArrayList<Card> suitArray = sourceHand.handMap.get(index);

        if (suitArray == null) {
            System.out.println("Hand:transferFrom ... suitArray is null");
            System.out.println("card = " + card + " index = " + index);
        }

        for (Card item : suitArray) {
            addCardToMap(item);
        }

        sourceHand.handMap.remove(index);

    }



    protected void buildNewHand() {

        System.out.println("entering buildNewHand");
        System.out.println("   GoFishMgr.dealCount = " + GoFishMgr.dealCount);

        for (int i = 0; i < GoFishMgr.dealCount; i++) {

            Card card = Deck.getACard();
            System.out.println(card);
            addCardToMap(card);

        }
    }

    protected void addCardToMap(Card card) {

        // attempt to get existing key in map
        ArrayList<Card> suitArray = handMap.get(card.getSequence());

        // if key not found, create a new ArrayList and add item
        if (suitArray == null) {
            ArrayList<Card> myArray = new ArrayList<Card>();
            myArray.add(card);
            handMap.put(card.getSequence(), myArray);   // add new key, value pair to TreeMap
        } else {  // add item to existing ArrayList if not a duplicate
            if (!suitArray.contains(card)) {
                suitArray.add(card);
            }
        }

    }


    protected void printHand() {

        System.out.println("entering printHand");

        Iterator<Map.Entry<Integer, ArrayList<Card>>> iter = handMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, ArrayList<Card>> entry = iter.next();

            ArrayList<Card> newList = new ArrayList<Card>();
            Integer key = entry.getKey();
            ArrayList<Card> list = entry.getValue();

            String pRank = Card.convertRank(key);

            System.out.print(pRank + ":");
            for (Card card : list) {
                System.out.print(card.getSuit());
            }
            System.out.print("  ");

        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "player's hand goes here";
    }

    protected static int convertRankToIndex(String i) {

        switch (i) {

            case "A": {
                return 1;
            }
            case "2": {
                return 2;
            }
            case "3": {
                return 3;
            }
            case "4": {
                return 4;
            }
            case "5": {
                return 5;
            }
            case "6": {
                return 6;
            }
            case "7": {
                return 7;
            }
            case "8": {
                return 8;
            }
            case "9": {
                return 9;
            }
            case "10": {
                return 10;
            }
            case "J": {
                return 11;
            }
            case "Q": {
                return 12;
            }
            case "K": {
                return 13;
            }
        }
        return 0;  // should never be reached
    }


} // end class Hand
