package booklinkedlist;

public class User {
	private String id;
    private String name;
    private String email;
    private String role;    // e.g., "student", "librarian"
    User next; // pointer to next user in the list

    // Constructor
    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.next = null;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    // Display user details
    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Role: " + role);
    }
}
