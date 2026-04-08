package dk.easv.easvticketsystem.model;

public class User {

    private int userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private String created;

    //Constructor without parameter
    public User() {}

    //Constructor
    public User(String name, String username, String email, String password, String role, String created) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.created = created;
    }

    //Getters + Setters
    public User(int userId, String name, String username, String email, String role, String created) {

        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.created = created;
    }

    public User() {}

    public int getUserId() { return userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }

}