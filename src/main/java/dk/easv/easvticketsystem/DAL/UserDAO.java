package dk.easv.easvticketsystem.DAL;

import dk.easv.easvticketsystem.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public void createUser(User user) throws Exception {
    public UserDAO() throws Exception {
        new DBConnector();
    }

    public List<User> getAllUsers() throws Exception {

        String sql = "INSERT INTO Users (name, username, email, password, role, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM Users";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);

             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("role"),
                                rs.getDate("created_date")
                                        .toString());
                users.add(user);
            }
        }
        return users;
    }

    public void updateUser(User user) throws Exception {

        String sql = "UPDATE Users " + "SET name = ?, " + "username = ?, " + "email = ?, " + "role = ?, " + "created_date = ? " + "WHERE user_id = ?";

        try (Connection conn = DBConnector.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());

            stmt.setString(2, user.getUsername());

            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getCreatedDate());

            stmt.setString(4, user.getRole());

            stmt.setDate(5, java.sql.Date.valueOf(user.getCreated()));

            stmt.setInt(6, user.getUserId());

            stmt.executeUpdate();
        }
    }
}
