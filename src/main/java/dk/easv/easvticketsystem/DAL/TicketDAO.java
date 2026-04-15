package dk.easv.easvticketsystem.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import dk.easv.easvticketsystem.model.TicketType;

public class TicketDAO {

    public void createTicket(String ticketId, int eventId, int ticketTypeId, String name, String email) throws Exception {

        String sql = "INSERT INTO Tickets (ticket_id, event_id, ticket_type_id, customer_name, customer_email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, java.util.UUID.fromString(ticketId));
            stmt.setInt(2, eventId);
            stmt.setInt(3, ticketTypeId);
            stmt.setString(4, name);
            stmt.setString(5, email);

            stmt.executeUpdate();
        }
    }

    public List<String> getAllTickets() throws Exception {

        List<String> tickets = new ArrayList<>();

        String sql = "SELECT ticket_id, event_id, ticket_type_id, customer_name FROM Tickets";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ticket = "ID: " + rs.getString("ticket_id") +
                        " | Event: " + rs.getInt("event_id") +
                        " | Type: " + rs.getInt("ticket_type_id") +
                        " | Name: " + rs.getString("customer_name");

                tickets.add(ticket);
            }
        }

        return tickets;
    }

    public List<TicketType> getAllTicketTypes() throws Exception {

        List<TicketType> types = new ArrayList<>();

        String sql = "SELECT ticket_type_id, name FROM TicketTypes";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                types.add(new TicketType(
                        rs.getInt("ticket_type_id"),
                        rs.getString("name")
                ));
            }
        }

        return types;
    }
}