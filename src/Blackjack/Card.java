package Blackjack;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kimir
 */

public class Card {
    private final String suit; // Suit of Card
    private final String rank; // Rank of Card
    private final int value; // Value given to each Card

    // Constructs Card Object for suit, rank and value
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    // Get methods for suit, rank and value
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    
    // Returns string of card given
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

