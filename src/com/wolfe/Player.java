package com.wolfe;

import java.util.*;


/**
 * Created by Jeremy on 10/12/2016.
 *
 * A Player can be either Human or Computer
 *
 * This class will:
 *      - get player name and type of player
 *      - ask another player for a card
 *      - check for requested matching card
 *      - if no matching card, draw a card from pool
 *      - accept matching card(s) and put in hand
 *      - check for books at end of each round
 *
 *
 */
public class Player {

    //Create two scanners, one for Strings, and one for numbers - int and float values.

    //Use this scanner to read text data that will be stored in String variables
    static Scanner stringScanner = new Scanner(System.in);
    //Use this scanner to read in numerical data that will be stored in int or double variables
    static Scanner numberScanner = new Scanner(System.in);

    //Create a Random object - this is a random number generator object
    Random random = new Random();


    static String COMPUTER = "C";
    static String HUMAN = "H";

    private int pIndex;
    private String name;
    private String playerType;
    protected int numBooks;
    protected Hand hand;

    public Player(int pIndex, String name, String playerType) {

        this.pIndex = pIndex;
        this.name = name;
        this.playerType = playerType;
        this.numBooks = 0;
        this.hand = new Hand();

        hand.buildNewHand();
        hand.printHand();
    }


    protected void askForCard(ArrayList<Player> players) {

        boolean turnOver = false;
        boolean foundCard = false;
        Player opponent = null;
        String requestCard = null;
        

        if (hand.handMap.size() == 0 && Deck.cardCountRemaining == 0) {
            // if player has no cards and deck is empty, turn over
            turnOver = true;
        }

        while (!turnOver) {

            displayStartOfTurnInfo(players);

            if (hand.handMap.size() > 0) {

                int requestFrom;
                if (playerType.equals(HUMAN)) {
                    requestFrom = getPlayerToQuery(players);
                } else {
                    requestFrom = cGetPlayerToQuery(players);
                }


                if (playerType.equals(HUMAN)) {
                    requestCard = inputCardRequest();
                } else {
                    requestCard = cInputCardRequest();
                }

                opponent = players.get(requestFrom);         // get opponents hand from designated player
                foundCard = opponent.hand.checkForMatch(requestCard);

//            }

                if (foundCard) {

                    System.out.println();
                    System.out.println("Found Card Asked For!");
                    //ArrayList<Card> theCard = opponent.hand.getCardRequest(requestCard);
                    hand.transferFrom(opponent.hand, requestCard);

                } else {

                    System.out.println();
                    System.out.println("Going Fishing");
                    // go fish
                    Card newCard;
                    if (Deck.deck.size() > 0) {
                        newCard = Deck.getACard();
                        hand.addCardToMap(newCard);

                        turnOver = !newCard.getRank().equals(requestCard);
                    } else {
                        turnOver = true;
                    }
                }

            }   // testing location of end if


            boolean test = hand.checkForBook();
            if (test) {
                numBooks++;
            }

//            if (hand.handMap.size() == 0 && Deck.cardCountRemaining == 0) {
            if (hand.handMap.size() == 0) {
                // if player has no cards and deck is empty, turn over
                turnOver = true;
            }

        }

        boolean test = hand.checkForBook();
        if (test) {
            numBooks++;
        }
    }


    private String cInputCardRequest() {


        System.out.println("What card do you want to ask for? (rank only A, 2, 3,... J, Q, K):");
        String requestCard = computeCardRequest();

        System.out.println("Computer asks for: " + requestCard);

        return requestCard;
    }


    private String inputCardRequest() {

        System.out.println("What card do you want to ask for? (rank only A, 2, 3,... J, Q, K):");
        String requestCard = stringScanner.nextLine();

        return requestCard;
    }

    private int cGetPlayerToQuery(ArrayList<Player> players) {

        System.out.println();
        System.out.println("Which player do you want to query? (numeric 0, 1, 2, etc):");

        int randPicker = random.nextInt(2);
        Integer requestFrom;
        System.out.println("Random generator number 0 - 2 = " + randPicker);

        if (randPicker == 0) {
            System.out.println("Peeking first player in the queue");
            requestFrom = GoFishMgr.playerPlaysQueue.peekFirst();
        } else {
            System.out.println("Peeking last player in the queue");
            requestFrom = GoFishMgr.playerPlaysQueue.peekLast();
        }

        System.out.println("random player to ask = " + requestFrom);

        return requestFrom;
    }


    private int getPlayerToQuery(ArrayList<Player> players) {

        System.out.println("Which player do you want to query? (numeric 0, 1, 2, etc):");
        int requestFrom = numberScanner.nextInt();
        while (requestFrom == GoFishMgr.currentPlayerID || requestFrom > GoFishMgr.numberOfPlayers - 2) {
            System.out.print("Available players: ");

            for (Integer id : GoFishMgr.playerPlaysQueue) {
                System.out.print(id + ", ");
            }

            for (Integer id : GoFishMgr.playerPlaysQueue) {
                Player temp = players.get(id);
                System.out.println(temp.getName() + " " + temp.getPlayerType());
                temp.hand.printHand();
            }

            System.out.println("Which player do you want to query? (numeric 0, 1, 2, etc):");
            requestFrom = numberScanner.nextInt();

        }

    return requestFrom;
    }

    private void displayStartOfTurnInfo(ArrayList<Player> players) {

        System.out.println();
        System.out.println("************ " + name + "'s Turn **************");
        System.out.println("*** Number of cards remaining: " + Deck.cardCountRemaining + " ***");
        System.out.println("*** Number of Books remaining: " + GoFishMgr.numberOfBooksRemaining + " ***");
        System.out.print("*** Player's hand: "); hand.printHand();
        System.out.println();

        System.out.println("Size of playerPlaysQueue = " + GoFishMgr.playerPlaysQueue.size());

        System.out.print("Available players: ");

        for (Integer id : GoFishMgr.playerPlaysQueue) {
            System.out.print(id + ", ");
        }

        for (Integer id : GoFishMgr.playerPlaysQueue) {
            Player temp = players.get(id);
            System.out.println(temp.getName() + " " + temp.getPlayerType());
            temp.hand.printHand();
        }
    }


    private String computeCardRequest() {

        int randPick = random.nextInt(hand.handMap.size());
        int counter = 0;

        Integer key = 0;
        for (Map.Entry<Integer, ArrayList<Card>> entry : hand.handMap.entrySet()) {
            key = entry.getKey();
            ArrayList<Card> list = entry.getValue();
            counter++;

            if (counter == randPick) {
                return Card.convertRank(key);
            }
        }
        /*
        Integer key = 0;
        for (Map.Entry<Integer, ArrayList<Card>> entry : hand.handMap.entrySet()) {
            key = entry.getKey();
            ArrayList<Card> list = entry.getValue();

            if (list.size() > 1) {
                return Card.convertRank(key);
            }
        }
        */
        return Card.convertRank(key);
    }


    //************** Getters and Setters and toString ****************
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    @Override
    public String toString() {

        return pIndex + ": " + name +  ": type:" + playerType + ": player hand: " + hand;
    }

}

 // end class Player