package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import LibraryFramework.Command;
import ui.DatabaseConnection;

public class AcceptCommand implements Command {
    private final int bookId;

    public AcceptCommand(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute() {
        updateBookStatus("Accepted");
    }

    private void updateBookStatus(String status) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET status = ? WHERE id = ?")) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}