/**
 * Created by Jeremy on 10/11/2016 - 10/15/2016
 *
 * Go Fish Card Game - uses rules from: http://www.bicyclecards.com/how-to-play/go-fish/
 *
 * Five Classes in this program:
 *      GoFishMgr - main program: sets up game and players, runs main player select for next turn loop
 *      Player - defines player variables and card "Hand", contains logic for playing out a player's turn
 *      Hand - contains data structures to hold a hand and completed books
 *      Card - defines a deck of cards
 *      Deck - builds a Deck of Cards
 *
 */
package com.wolfe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class GoFishMgr {

    //Create two scanners, one for Strings, and one for numbers - int and float values.
    //Use this scanner to read text data that will be stored in String variables
    static Scanner stringScanner = new Scanner(System.in);
    //Use this scanner to read in numerical data that will be stored in int or double variables
    static Scanner numberScanner = new Scanner(System.in);

    public static int dealCount = 7;  // default number of cards to deal to a player
    public static int numberOfPlayers;    // number of players in this game
    public static int numberOfBooksRemaining = 13;

    static String COMPUTER = "C";
    static String HUMAN = "H";

    static LinkedList<Integer> playerPlaysQueue = new LinkedList<>();   // circular index list of players
    static ArrayList<Player> players = new ArrayList<>();               // list of player objects
    static int currentPlayerID;                                         // player currently active

    public static void main(String[] args) {

        Deck.buildDeck();
        Deck.shuffleDeck();
        setupPlayers();

        // main play loop. Pulls a player from front of queue and puts into play. Returns player
        // to back of queue at end of turn.
        // Continues until all 13 books formed.
        do {

            int playerIndex = playerPlaysQueue.pop(); // get next player from queue
            currentPlayerID = playerIndex;
            Player currentPlayer = players.get(playerIndex);

            currentPlayer.askForCard(players);

            playerPlaysQueue.add(playerIndex); // put player back to end of queue

        } while (numberOfBooksRemaining > 0);


        System.out.println();
        System.out.println("************* G A M E   O V E R ***************");
        for (Player player : players) {
            System.out.println("Player " + player.getName() + " number of books: " + player.numBooks);
        }

        stringScanner.close();
        numberScanner.close();

    } // end class main


    // sets up game and players TODO needs input validations
    protected static void setupPlayers() {

        String morePlayers = "y";
        String name;
        String playerType;
        int pCount;
        int playerIndex = 0;

        System.out.println("Enter the number of players (2 thru 5): ");
        pCount = numberScanner.nextInt();

        do {

            System.out.println("Enter Player Name: ");
            name = stringScanner.nextLine();
            System.out.println("Enter Player Type (C for computer, H for human): ");
            playerType = stringScanner.nextLine();

            Player player = new Player(playerIndex, name, playerType);
            players.add(playerIndex, player);       // add player object to arraylist
            playerPlaysQueue.add(playerIndex);      // index(ID) of player added to round robin queue
            playerIndex++;

            System.out.println("Enter more players? (y or n):");
            morePlayers = stringScanner.nextLine();

        } while (morePlayers.equalsIgnoreCase("y") && playerIndex < 6);

        numberOfPlayers = pCount;
        dealCount = (numberOfPlayers < 5) ? 7 : 5;

    }

} //end class GoFishMgr
