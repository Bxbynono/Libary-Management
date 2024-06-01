package ui;

import javax.swing.*;

import LibraryFramework.SignupHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import cor.*;

public class UserSignup extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel statusLabel;

    private SignupHandler signupHandlerChain;

    public UserSignup() {
        setTitle("User Signup");
        setSize(400, 320); // Adjusted size
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("User Signup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Reset gridwidth

        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx++;
        usernameField = new JTextField(20); // Adjusted width
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx++;
        emailField = new JTextField(20); // Adjusted width
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        passwordField = new JPasswordField(20); // Adjusted width
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx++;
        confirmPasswordField = new JPasswordField(20); // Adjusted width
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton signupButton = new JButton("Signup");
        panel.add(signupButton, gbc);

        gbc.gridy++;
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(statusLabel, gbc);

        add(panel);

        // Pass statusLabel to UsersExistenceHandler
        UsersExistenceHandler userExistenceHandler = new UsersExistenceHandler(this, statusLabel);

        // Build the handler chain
        buildSignupHandlerChain(userExistenceHandler);

        // Action listener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Pass the request to the first handler in the chain
                signupHandlerChain.handleRequest(username, email, password, confirmPassword);
            }
        });
    }

    private void buildSignupHandlerChain(UsersExistenceHandler userExistenceHandler) {
        SignupHandler createUserHandler = new CreateUserHandler();

        userExistenceHandler.setNextHandler(createUserHandler);

        signupHandlerChain = userExistenceHandler;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserSignup signupPage = new UserSignup();
            signupPage.setVisible(true);
        });
    }
}
