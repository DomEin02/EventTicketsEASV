package dk.easv.easvticketsystem.DAL;

import dk.easv.easvticketsystem.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public UserDAO() {
    }

    //Get all users
    public List<User> getAllUsers() throws Exception {

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
                        rs.getDate("created_date").toLocalDate()
                );

                users.add(user);
            }
        }

        return users;
    }

    // CREATE USER
    public void createUser(User user) throws Exception {

        String sql =
                "INSERT INTO Users " +
                        "(name, username, email, password_hash, role, created_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setDate(6,
                    user.getCreated() != null
                            ? Date.valueOf(user.getCreated())
                            : Date.valueOf(LocalDate.now())
            );

            stmt.executeUpdate();
        }
    }

    // UPDATE USER
    public void updateUser(User user) throws Exception {

        String sql =
                "UPDATE Users SET " +
                        "name = ?, " +
                        "username = ?, " +
                        "email = ?, " +
                        "role = ?, " +
                        "created_date = ? " +
                        "WHERE user_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setDate(5,
                    Date.valueOf(user.getCreated()));
            stmt.setInt(6,
                    user.getUserId());

            stmt.executeUpdate();
        }
    }
}
