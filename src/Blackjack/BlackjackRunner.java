/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blackjack;

/**
 *
 * @author kimir
 */
public class BlackjackRunner {
    private static Game currentGame;
    private static Player currentPlayer;

    public static void main(String[] args) {
        new BlackjackGUI(); // Initialize the GUI
    }

    public static void startNewGame(String playerName) {
        currentPlayer = new Player(playerName, 1000);
        currentGame = new Game(currentPlayer);
        playRound();
    }

    public static void loadGame(String playerName) {
        currentPlayer = DatabaseManager.loadPlayer(playerName);
        if (currentPlayer != null) {
            currentGame = new Game(currentPlayer);
            playRound();
        }
    }

    private static void playRound() {
        currentGame.startGame();
        // Implement round logic
    }

    private static void determineWinner() {
        // Determine and display the winner
    }
}


