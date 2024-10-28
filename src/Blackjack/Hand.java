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

import java.util.LinkedList;

public class Hand {
    private LinkedList<Card> cards;

    public Hand() {
        cards = new LinkedList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index); // Return the card at the specified index
        } else {
            return null; // Return null if index is out of bounds
        }
    }

    public int getTotalValue() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            total += card.getValue();
            if (card.getRank().equals("Ace")) {
                aces++;
            }
        }

        // Adjust for aces
        while (total > 21 && aces > 0) {
            total -= 10; // Count ace as 1 instead of 11
            aces--;
        }

        return total;
    }

    public void clear() {
        cards.clear();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(", "); // Append each card's string representation
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Remove the trailing comma and space
        }
        return sb.toString(); // Return the string representation of the hand
    }

    // Additional methods as needed
}
