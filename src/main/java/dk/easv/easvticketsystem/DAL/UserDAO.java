package dk.easv.easvticketsystem.DAL;

import dk.easv.easvticketsystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public void createUser(User user) throws Exception {

        String sql = "INSERT INTO Users (name, username, email, password, role, created_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getCreatedDate());

            stmt.executeUpdate();
        }
    }
}