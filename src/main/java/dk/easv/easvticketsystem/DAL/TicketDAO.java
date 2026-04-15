package dk.easv.easvticketsystem.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class TicketDAO {

    private DBConnector dbConnector;

    public TicketDAO() {
        dbConnector = new DBConnector();
    }

    public void createTicket(int eventId, int userId) throws Exception {

        String sql = "INSERT INTO Tickets (event_id, user_id) VALUES (?, ?)";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);

            stmt.executeUpdate();
        }
    }

    public List<String> getAllTickets() throws Exception {

        List<String> tickets = new ArrayList<>();

        String sql = "SELECT id, event_id, user_id FROM Tickets";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ticket = "ID: " + rs.getInt("id") +
                        " | Event: " + rs.getInt("event_id") +
                        " | User: " + rs.getInt("user_id");

                tickets.add(ticket);
            }
        }

        return tickets;
    }
}