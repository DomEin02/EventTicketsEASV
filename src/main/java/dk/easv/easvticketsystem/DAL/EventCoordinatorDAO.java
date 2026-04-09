package dk.easv.easvticketsystem.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EventCoordinatorDAO {

    public void assignCoordinator(int eventId, int userId) throws Exception {

        String sql = "INSERT INTO EventCoordinators " + "(event_id, user_id) " + "VALUES (?, ?)";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);

            stmt.executeUpdate();
        }
    }

    public String getCoordinatorName(int eventId) throws Exception {

        String sql = "SELECT u.name " + "FROM Users u " + "JOIN EventCoordinators ec " + "ON u.user_id = ec.user_id " + "WHERE ec.event_id = ?";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return "Not Assigned";
    }
}
