package cor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

import LibraryFramework.SignupHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import ui.UserLoginPage;

public class UsersExistenceHandler implements SignupHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private JLabel statusLabel;
    private JFrame parentFrame;

    public UsersExistenceHandler(JFrame parentFrame, JLabel statusLabel) {
        this.parentFrame = parentFrame;
        this.statusLabel = statusLabel;
    }

    @Override
    public void setNextHandler(SignupHandler handler) {
        // No next handler needed
    }

    @Override
    public void handleRequest(String username, String email, String password, String confirmPassword) {
        boolean success = createUser(username, email, password);
        if (success) {
            statusLabel.setText("User created successfully");
            clearFields();
            redirectToLoginPage();
        } else {
            statusLabel.setText("Error creating user");
        }
    }

    private boolean createUser(String username, String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                // Handle duplicate entry error here
                statusLabel.setText("Email already registered");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void clearFields() {
        // Assuming statusLabel is defined globally
        statusLabel.setText("");
    }

    private void redirectToLoginPage() {
        JOptionPane.showMessageDialog(null, "Signup successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        parentFrame.dispose(); // Close the current signup frame
        UserLoginPage loginPage = new UserLoginPage();
        loginPage.setVisible(true);
    }
}
