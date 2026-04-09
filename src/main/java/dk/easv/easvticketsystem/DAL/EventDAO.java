package dk.easv.easvticketsystem.DAL;

import dk.easv.easvticketsystem.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public EventDAO() throws Exception {
        new DBConnector();
    }

    public int createEvent(Event event) throws Exception {
        String sql = "INSERT INTO Events (title, start_datetime, location, notes, max_capacity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, event.getTitle());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(event.getStartDateTime()));
            stmt.setString(3, event.getLocation());
            String notes = event.getNotes();
            if (notes == null) notes ="";
            stmt.setString(4, notes);
            stmt.setInt(5, event.getMaxCapacity());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                event.setEventId(id);
                return id;
            } else {
                throw new Exception("Event id not found");
            }
        }
    }

    public List<Event> getAllEvents() throws Exception {

        List<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM Events";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getString("title"),
                        rs.getTimestamp("start_datetime").toLocalDateTime(),
                        rs.getTimestamp("end_datetime") != null ?
                                rs.getTimestamp("end_datetime").toLocalDateTime() : null,
                        rs.getString("location"),
                        rs.getString("notes"),
                        rs.getString("location_guidance"),
                        rs.getInt("max_capacity")
                );
                events.add(event);
            }
        }
        return events;
    }

    public void updateEvent(Event event)
            throws Exception {

        String sql = "UPDATE Events " + "SET title = ?, " + "start_datetime = ?, " + "location = ?, "+ "notes = ?, " + "max_capacity = ? " + "WHERE event_id = ?";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(event.getStartDateTime()));
            stmt.setString(3, event.getLocation());
            stmt.setString(4, event.getNotes());
            stmt.setInt(5, event.getMaxCapacity());
            stmt.setInt(6, event.getEventId());
            stmt.executeUpdate();
        }
    }

    public int getTicketCountForEvent(int eventId) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM Tickets " + "WHERE event_id = ?";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public void deleteEvent(int eventId) throws Exception {
        String sql = "DELETE FROM Events " + "WHERE event_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                 stmt.setInt(1, eventId);
                 stmt.executeUpdate();
        }
    }
}
