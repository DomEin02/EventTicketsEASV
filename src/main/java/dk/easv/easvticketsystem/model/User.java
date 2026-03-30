package dk.easv.easvticketsystem.model;

public class User {

    private String name;
    private String username;
    private String email;
    private String role;
    private String created;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
}