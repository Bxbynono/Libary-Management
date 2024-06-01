package ui;

import javax.swing.*;

import LibraryFramework.LoginObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import observer.*;

/**
 * The {@code LoginPage} class represents the login page of the application.
 * It uses the Observer pattern to notify observers about the success or failure of login attempts.
 */
public class LoginPage extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // List of observers to be notified of login events
    private final List<LoginObserver> observers = new ArrayList<>();

    private JLabel statusLabel;

    /**
     * Constructor to set up the login page UI.
     */
    public LoginPage() {
        setTitle("Library Management System - Login");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Library Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Reset gridwidth
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Validate input
                if (email.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Please fill in all fields");
                    return;
                }

                // Perform login operation
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String librarianQuery = "SELECT * FROM librarian WHERE email=? AND password=?";
                    PreparedStatement librarianPstmt = conn.prepareStatement(librarianQuery);

                    // Set parameters for librarian query
                    librarianPstmt.setString(1, email);
                    librarianPstmt.setString(2, password);

                    ResultSet librarianRs = librarianPstmt.executeQuery();

                    if (librarianRs.next()) {
                        // Login successful as librarian
                        notifyObservers("Librarian");
                        JOptionPane.showMessageDialog(LoginPage.this, "Login successful as librarian", "Success", JOptionPane.INFORMATION_MESSAGE);
                        LibrarianHomePage librarianHomePage = new LibrarianHomePage(email); // Pass email to the home page
                        librarianHomePage.setVisible(true);
                        dispose(); // Close the login page
                    } else {
                        // Login failed
                        JOptionPane.showMessageDialog(LoginPage.this, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                        notifyObservers(null);
                    }

                    librarianRs.close();
                    librarianPstmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        add(panel);
    }

    public void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LoginObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String userType) {
        for (LoginObserver observer : observers) {
            if (userType != null) {
                observer.onLoginSuccess(userType);
            } else {
                observer.onLoginFailure();
            }
        }
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();

        // Example observer
        LoginObserver observer = new LoginObserver() {
            @Override
            public void onLoginSuccess(String userType) {
                System.out.println("Login successful as " + userType);
            }

            @Override
            public void onLoginFailure() {
                System.out.println("Login failed");
            }
        };

        loginPage.addObserver(observer);

        SwingUtilities.invokeLater(() -> loginPage.setVisible(true));
    }
}
