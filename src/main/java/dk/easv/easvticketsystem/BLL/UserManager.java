package dk.easv.easvticketsystem.BLL;

import dk.easv.easvticketsystem.DAL.UserDAO;
import dk.easv.easvticketsystem.model.User;

import java.util.List;

public class UserManager {

    private UserDAO userDAO;

    public UserManager() throws Exception {
        userDAO = new UserDAO();
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }

    public void createUser(User user) throws Exception {

        if(user.getName().isEmpty())
            throw new Exception("Name required");

        if(user.getUsername().isEmpty())
            throw new Exception("Username required");

        if(user.getPassword().isEmpty())
            throw new Exception("Password required");

        userDAO.createUser(user);
    }

    public void updateUser(User user) throws Exception {
        userDAO.updateUser(user);
    }
}
