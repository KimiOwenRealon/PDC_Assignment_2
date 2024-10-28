/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blackjack;

/**
 *
 * @author kimir
 */
import java.util.LinkedList;

public class Player {
    private String name;
    private Hand hand;
    private int balance;
    private int gamesPlayed; // Track the number of games played
    private int gamesWon;    // Track the number of games won
    private int gamesLost;   // Track the number of games lost
    private int gamesTied;
    

    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new Hand();
        this.gamesPlayed = 0; // Initialize games played to 0
        this.gamesWon = 0;    // Initialize games won to 0
        this.gamesLost = 0;   // Initialize games lost to 0
        this.gamesTied = 0;
    }
    
    // Constructor for loading an existing player
    public Player(String name, int balance, int gamesPlayed, int gamesWon) {
        this.name = name;
        this.balance = balance;
        this.hand = new Hand();
        this.gamesPlayed = gamesPlayed; // Set from loaded data
        this.gamesWon = gamesWon;       // Set from loaded data
        this.gamesLost = gamesLost;     // Set from loaded data
        this.gamesTied = gamesTied;
    }

    public String getName() {
        return name;
    }
    
    public int getBalance() {
        return balance;
    }

    public int getGamesPlayed() {
        return gamesPlayed; // Added method to return games played
    }

    public int getGamesWon() {
        return gamesWon; // Added method to return games won
    }
    
    public int getGamesLost() {
        return gamesLost;
    }
    
    public int getGamesTied() {
        return gamesTied; // Added getter for games tied
    }
    
    public Hand getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public int getHandValue() {
        return hand.getTotalValue();
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public void surrender() {
        // Handle surrender logic (e.g., lose half the balance)
        balance /= 2;
    }
    
    // Increment the number of games played
    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    // Increment the number of games won
    public void incrementGamesWon() {
        gamesWon++;
    }
    
    public void incrementGamesLost() {
        gamesLost++;
    }
    
    public void incrementGamesTied() {
        gamesTied++; // Method to increment ties
    }
}


