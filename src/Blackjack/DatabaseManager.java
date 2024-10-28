/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Blackjack;

/**
 *
 * @author kimir
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:derby:myDatabase;create=true"; // Use your database name

    // Method to connect to the Derby database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Method to save player data to the database
    public static void savePlayer(Player player) {
    String sql = "MERGE INTO players KEY (name) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, player.getName());
        pstmt.setInt(2, player.getBalance());
        pstmt.setInt(3, player.getGamesPlayed());
        pstmt.setInt(4, player.getGamesWon());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Method to load player data from the database
    public static Player loadPlayer(String name) {
        String sql = "SELECT * FROM players WHERE name = ?";
        Player player = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String playerName = rs.getString("name");
                int balance = rs.getInt("balance");
                int gamesPlayed = rs.getInt("games_played");
                int gamesWon = rs.getInt("games_won");
                
                player = new Player(playerName, balance, gamesPlayed, gamesWon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return player;
    }
}

