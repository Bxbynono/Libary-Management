package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import LibraryFramework.StatusObserver;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import state.*;
import observer.*;

public class UserHomePage extends JFrame implements StatusObserver, BookFormObserver {
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public UserHomePage() {
        setTitle("User Home Page");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("User Home Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Title", "Author", "Description", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton bookButton = new JButton("Reserve Book");
        panel.add(bookButton, BorderLayout.SOUTH);

        bookButton.addActionListener(e -> {
            BookForm bookForm = new BookForm(this);
            bookForm.setVisible(true);
        });

        add(panel);
        fetchBookingDetails();
    }
    
    @Override
    public void statusChanged(String newStatus) {
        // Display message in the console
        System.out.println("Booking status changed to: " + newStatus);
    }

    @Override
    public void bookingAdded() {
        fetchBookingDetails();
    }


    // Modify fetchBookingDetails method to register as observer
    public void fetchBookingDetails() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT title, author, description, status FROM books");
             ResultSet rs = pstmt.executeQuery()) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                BookingStatus status = rs.getString("status").equals("Accepted") ? new AcceptedStatus() : new PendingStatus();
                status.addObserver(this); // Register as observer
                Vector<String> row = new Vector<>();
                row.add(rs.getString("title"));
                row.add(rs.getString("author"));
                row.add(rs.getString("description"));
                row.add(status.getStatus()); // Use the status object
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching booking details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserHomePage homePage = new UserHomePage();
            homePage.setVisible(true);
        });
    }
}
