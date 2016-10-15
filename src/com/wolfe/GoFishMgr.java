/**
 * Created by Jeremy on 10/11/2016.
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

        do {

            int playerIndex = playerPlaysQueue.pop(); // get next player from queue
            currentPlayerID = playerIndex;
            Player currentPlayer = players.get(playerIndex);

            currentPlayer.askForCard(players);

            playerPlaysQueue.add(playerIndex); // put player back to end of queue

        } while (numberOfBooksRemaining > 0);


        for (Player player : players) {
            System.out.println("Player " + player.getName() + " number of books: " + player.numBooks);
        }

        stringScanner.close();
        numberScanner.close();

    } // end class main

    protected static void setupPlayers() {

        String morePlayers = "y";
        String name;
        String playerType;
        int playerIndex = 0;

        do {

            System.out.println("Enter Player Name: ");
            name = stringScanner.nextLine();
            System.out.println("Enter Player Type (C for computer, H for human): ");
            playerType = stringScanner.nextLine();

            Player player = new Player(playerIndex, name, playerType);
            players.add(playerIndex, player);
            playerPlaysQueue.add(playerIndex);      // round robin queue
            playerIndex++;

            System.out.println("Enter more players? (y or n):");
            morePlayers = stringScanner.nextLine();

        } while (morePlayers.equalsIgnoreCase("y") && playerIndex < 10);

        numberOfPlayers = playerIndex + 1;
        dealCount = (numberOfPlayers < 5) ? 7 : 5;

    }

} //end class GoFishMgr
