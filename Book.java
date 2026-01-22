package booklinkedlist;

public class Book {
	private String id;       // unique book ID
    private String title;    // book title
    private String author;   // author name
    private boolean available; // true = available, false = borrowed
    Book next;               // pointer to the next book (package-private for linked list)

    // Constructor
    public Book(String id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
        this.next = null;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailable(boolean available) { this.available = available; }

    // Borrow this book
    public boolean borrow() {
        if (!available) {
            return false; // already borrowed
        }
        available = false;
        return true;
    }

    // Return this book
    public boolean returnBook() {
        if (available) {
            return false; // already available
        }
        available = true;
        return true;
    }
    
    // Display book details (for console)
    public void display() {
        System.out.println("ID: " + id + ", Title: " + title +
                           ", Author: " + author + ", Available: " + available);
    }
}
