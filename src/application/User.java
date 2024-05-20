package application;


public class User {

    private int userId; // Renommer l'attribut id en userId

    private String username;
    private String role;
    private String email;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Ajouter une méthode pour définir l'identifiant de l'utilisateur
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Ajouter une méthode pour obtenir l'identifiant de l'utilisateur
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}