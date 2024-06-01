package cor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import LibraryFramework.LoginHandler;
import ui.UserHomePage;

public class UserExistenceHandler implements LoginHandler {
    private LoginHandler nextHandler;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void setNextHandler(LoginHandler handler) {
        this.nextHandler = handler;
    }

    public void handleRequest(String email, String password) {
        // Connect to the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE email=? AND password=?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Login successful
                        JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Open user's home page or perform any other action
                        UserHomePage userHomePage = new UserHomePage();
                        userHomePage.setVisible(true);
                        return; // Exit the method to prevent further handling
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no matching user is found, pass the request to the next handler
        if (nextHandler != null) {
            nextHandler.handleRequest(email, password);
        }
    }
}
