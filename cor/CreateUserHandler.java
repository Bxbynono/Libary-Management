package cor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// import javax.swing.JOptionPane;

import LibraryFramework.SignupHandler;
// import ui.UserHomePage;

public class CreateUserHandler implements SignupHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void setNextHandler(SignupHandler handler) {
        // No next handler needed
    }

    @Override
    public void handleRequest(String username, String email, String password, String confirmPassword) {
        // Attempt to create user in the database
        boolean success = createUser(username, email, password);
        
        if (success) {
            System.out.println("User created successfully"); // Or you can set status label or any other action
        } else {
            System.out.println("Error creating user"); // Or you can set status label or any other action
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
            e.printStackTrace();
        }
        return false;
    }
}
