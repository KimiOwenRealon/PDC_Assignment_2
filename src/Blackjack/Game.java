/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blackjack;

/**
 *
 * @author kimir
 */
import java.util.Random;

public class Game {
    private Deck deck;
    private Player player;
    private Player dealer;

    public Game(Player player) {
        this.player = player;
        this.dealer = new Player("Dealer", 0); // Dealer has no balance
        this.deck = new Deck();
    }

    public void startGame() {
        deck.shuffle();
        player.getHand().clear(); // Clear previous hand
        dealer.getHand().clear(); // Clear dealer hand

        // Deal initial cards
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
    }

    
    public void playerHits() {
        player.addCard(deck.dealCard());
    }

    public void dealerPlays() {
        while (dealer.getHandValue() < 17) {
            dealer.addCard(deck.dealCard());
        }
    }
    
    public String getDealerVisibleCard() {
        return dealer.getHand().getCard(0).toString(); // Return the dealer's first card (visible)
    }

    public String getGameResult() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (playerValue > 21) {
            return "You busted! Dealer wins.";
        } else if (dealerValue > 21) {
            return "Dealer busted! You win.";
        } else if (playerValue > dealerValue) {
            return "You win!";
        } else if (playerValue < dealerValue) {
            return "Dealer wins!";
        } else {
            return "It's a tie!";
        }
    }
}
