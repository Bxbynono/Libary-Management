package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import LibraryFramework.Command;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import command.*;

public class LibrarianHomePage extends JFrame {

    private String email;
    private JTable booksTable;
    private DefaultTableModel tableModel;

    public LibrarianHomePage(String email) {
        this.email = email;

        setTitle("Librarian Home Page");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + email + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Initialize table model with column names
        String[] columnNames = {"ID", "Title", "Author", "Description", "Status", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        booksTable = new JTable(tableModel);
        booksTable.setRowHeight(60); // Set the row height to 40 pixels
        booksTable.getColumnModel().getColumn(5).setCellRenderer(new ActionCellRenderer());
        booksTable.getColumnModel().getColumn(5).setCellEditor(new ActionCellEditor());
        
        JScrollPane scrollPane = new JScrollPane(booksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        fetchBookDetails();
    }

    private void fetchBookDetails() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, title, author, description, status FROM books");
             ResultSet rs = pstmt.executeQuery()) {

            // Clear previous data from table
            tableModel.setRowCount(0);

            // Populate table with new data
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String status = rs.getString("status");

                tableModel.addRow(new Object[]{id, title, author, description, status, "Action"});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching book details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ActionCellRenderer extends JPanel implements TableCellRenderer {
        private JButton acceptButton = new JButton("Accept");
        private JButton cancelButton = new JButton("Cancel");

        public ActionCellRenderer() {
            setLayout(new FlowLayout());
            add(acceptButton);
            add(cancelButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ActionCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton acceptButton = new JButton("Accept");
        private JButton cancelButton = new JButton("Cancel");
        private int row;

        public ActionCellEditor() {
            acceptButton.addActionListener(this);
            cancelButton.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return "Action";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(acceptButton);
            panel.add(cancelButton);
            return panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int bookId = (int) tableModel.getValueAt(row, 0);
            Command command;

            if (e.getSource() == acceptButton) {
                command = new AcceptCommand(bookId);
            } else {
                command = new CancelCommand(bookId);
            }

            command.execute();
            fetchBookDetails();
            fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibrarianHomePage homePage = new LibrarianHomePage("c@gmail.com");
            homePage.setVisible(true);
        });
    }
}
