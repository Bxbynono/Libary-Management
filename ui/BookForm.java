package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import observer.BookFormObserver;

public class BookForm extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextArea descriptionArea;
    private List<BookFormObserver> observers = new ArrayList<>();
    private UserHomePage userHomePage;

    public BookForm(UserHomePage userHomePage) {
        this.userHomePage = userHomePage;
        setTitle("Book Form");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Book Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(titleField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(authorLabel, gbc);

        authorField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(authorField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(descriptionLabel, gbc);

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String description = descriptionArea.getText();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(BookForm.this, "Please fill in title and author fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = insertBooking(title, author, description);
                if (success) {
                    JOptionPane.showMessageDialog(BookForm.this, "Booking details added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    notifyObservers();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(BookForm.this, "Error adding booking details", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }

    private boolean insertBooking(String title, String author, String description) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books (title, author, description) VALUES (?, ?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, description);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void addObserver(BookFormObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BookFormObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (BookFormObserver observer : observers) {
            observer.bookingAdded();
        }
        // Notify UserHomePage
        userHomePage.bookingAdded();
    }
}
