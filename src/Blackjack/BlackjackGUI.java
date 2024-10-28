/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blackjack;

/**
 *
 * @author kimir
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI {
    private JFrame frame;
    private JPanel mainMenuPanel;
    private JPanel gamePanel;
    private JTextField playerNameField;
    private JTextArea gameTextArea;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton quitButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton resetGameButton; // Changed from surrender to reset
    private JButton mainMenuButton;   // New button to return to the main menu

    private Game currentGame;
    private Player currentPlayer;

    public BlackjackGUI() {
        // Initialize the main frame
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create a CardLayout for the frame's content pane
        CardLayout cardLayout = new CardLayout();
        JPanel contentPane = new JPanel(cardLayout);
        frame.setContentPane(contentPane);

        // Create and set up the main menu panel
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(4, 1));
        playerNameField = new JTextField();
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        quitButton = new JButton("Quit");

        mainMenuPanel.add(new JLabel("Enter Player Name:"));
        mainMenuPanel.add(playerNameField);
        mainMenuPanel.add(newGameButton);
        mainMenuPanel.add(loadGameButton);
        mainMenuPanel.add(quitButton);

        // Create and set up the game panel
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gameTextArea = new JTextArea();
        gameTextArea.setEditable(false);
        gameTextArea.setLineWrap(true);
        gameTextArea.setWrapStyleWord(true);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        resetGameButton = new JButton("Reset Round"); // Reset button instead of surrender
        mainMenuButton = new JButton("Main Menu"); // New button to go to the main menu

        JPanel actionPanel = new JPanel();
        actionPanel.add(hitButton);
        actionPanel.add(standButton);
        actionPanel.add(resetGameButton); // Use the reset button here
        actionPanel.add(mainMenuButton);    // Add the main menu button

        // Add components to the game panel
        gamePanel.add(new JScrollPane(gameTextArea), BorderLayout.CENTER);
        gamePanel.add(actionPanel, BorderLayout.SOUTH);

        // Add panels to the content pane
        contentPane.add(mainMenuPanel, "Main Menu");
        contentPane.add(gamePanel, "Game Panel");

        // Show the main menu at start
        cardLayout.show(contentPane, "Main Menu");

        // Set up button actions
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentGame.playerHits();
                if (currentPlayer.isBusted()) {
                    // Player busted
                    gameTextArea.append("You hit and busted! Dealer wins.\n");
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);
                    determineWinner(); // Call to determine the winner after busting
                } else {
                    // Update display with current hand
                    updateGameDisplay();
                }
            }
        });

        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentGame.dealerPlays();
                determineWinner(); // Check the result after the dealer plays
            }
        });

        // Reset round button action
        resetGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame(); // Call the reset game method
            }
        });

        // Main menu button action
        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMainMenu(); // Call the method to show the main menu
            }
        });

        frame.setVisible(true); // Show the frame after setting up
    }

    private void startNewGame() {
        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid name.");
            return;
        }
        currentPlayer = new Player(playerName, 1000); // Initialize player with a balance of 1000
        currentGame = new Game(currentPlayer); // Initialize a new game with the current player
        currentGame.startGame(); // Start the game by dealing cards
        showGamePanel();
        updateGameDisplay(); // Update the display with the player's hand
    }

    private void loadGame() {
        String playerName = playerNameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid name.");
            return;
        }
        currentPlayer = DatabaseManager.loadPlayer(playerName); // Load player from database
        if (currentPlayer != null) {
            currentGame = new Game(currentPlayer); // Initialize a new game with the loaded player
            currentGame.startGame(); // Start the game by dealing cards
            showGamePanel();
            updateGameDisplay();
        } else {
            JOptionPane.showMessageDialog(frame, "Player not found.");
        }
    }

    private void showGamePanel() {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), "Game Panel");
    }

    private void updateGameDisplay() {
        String playerHand = currentPlayer.getHand().toString();
        String dealerVisibleCard = currentGame.getDealerVisibleCard();
        gameTextArea.setText("Your hand: " + playerHand + "\n" +
                             "Dealer's visible card: " + dealerVisibleCard + "\n");
        if (currentPlayer.isBusted()) {
            gameTextArea.append("You busted! Dealer wins.\n");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
    }

    private void determineWinner() {
        String result = currentGame.getGameResult();
        JOptionPane.showMessageDialog(frame, result);
        
        // Update player statistics based on result
        currentPlayer.incrementGamesPlayed(); // Increment games played

        if (result.contains("win")) {
            currentPlayer.incrementGamesWon(); // Increment wins
        } else if (result.contains("lose")) {
            currentPlayer.incrementGamesLost(); // Increment losses
        } else if (result.contains("tie")) {
        currentPlayer.incrementGamesTied(); // Increment ties
        }

        // Save player stats to database
        DatabaseManager.savePlayer(currentPlayer);
        
        // Reset for the next round
        resetGame();
    }

    private void resetGame() {
        // Start a new round
        if (currentGame != null) {
            currentGame.startGame(); // Restart the game with the same player
        }
        updateGameDisplay(); // Update the display for the new round
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void showMainMenu() {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), "Main Menu"); // Show the main menu
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackGUI());
    }
}



