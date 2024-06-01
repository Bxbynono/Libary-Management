package ui;

import javax.swing.*;

import LibraryFramework.LoginHandler;
import LibraryFramework.LoginObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cor.*;

// Client Code
public class UserLoginPage extends JFrame {
    private LoginHandler loginHandlerChain;

    private final List<LoginObserver> observers = new ArrayList<>();

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public UserLoginPage() {
        setTitle("User Login");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("User Login");
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

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);
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

        add(panel);

        // Build the handler chain
        buildLoginHandlerChain();

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Pass the request to the first handler in the chain
                loginHandlerChain.handleRequest(email, password);
            }
        });
    }

    // Method to add observer
    public void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    // Method to remove observer
    public void removeObserver(LoginObserver observer) {
        observers.remove(observer);
    }

    // Method to notify observers
    private void notifyObservers(String userType) {
        for (LoginObserver observer : observers) {
            if (userType != null) {
                observer.onLoginSuccess(userType);
            } else {
                observer.onLoginFailure();
            }
        }
    }

    private void buildLoginHandlerChain() {
        LoginHandler inputValidationHandler = new InputValidationHandler();
        LoginHandler userExistenceHandler = new UserExistenceHandler();

        inputValidationHandler.setNextHandler(userExistenceHandler);

        loginHandlerChain = inputValidationHandler;
    }

    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserLoginPage loginPage = new UserLoginPage();

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

            loginPage.setVisible(true);
        });
    }
}