package booklinkedlist;

public class BookLinkedList {

    private Book head;

    public BookLinkedList() {
        head = null;
    }

    public Book getHead() {
        return head;
    }
    public void setHead(Book head) {
        this.head = head;
    }

    //load books from text file
    public void loadFromFile() {
        BookLinkedList loadedList = FileHandler.loadBooksFromFile();
        this.head = loadedList.getHead();  // assign rebuilt list
    }

    //save to text files
    public void saveToFile() {
        FileHandler.saveBooksToFile(this);
    }

    //add book
    public void addBook(Book newBook) {
        if (head == null) {
            head = newBook;
        } else {
            Book current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newBook;
        }

        saveToFile();  // auto save
    }

    //display book
    public void displayBooks() {
        Book current = head;
        if (current == null) {
            System.out.println("No books found.");
            return;
        }

        while (current != null) {
            current.display();
            current = current.next;
        }
    }

    //search book
    public Book searchBookById(String id) {
        Book current = head;
        while (current != null) {
            if (current.getId().equals(id)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    //delete by id
    public boolean deleteBookById(String id) {

        if (head == null) return false;

        if (head.getId().equals(id)) {
            head = head.next;
            saveToFile();//save
            return true;
        }

        Book current = head;
        while (current.next != null && !current.next.getId().equals(id)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            saveToFile();//save
            return true;
        }

        return false;
    }

    //modify
    public boolean modifyBookById(String id, String newTitle, String newAuthor, Boolean newAvailability) {
        Book book = searchBookById(id);
        if (book != null) {
            if (newTitle != null) book.setTitle(newTitle);
            if (newAuthor != null) book.setAuthor(newAuthor);
            if (newAvailability != null) book.setAvailable(newAvailability);
            
            saveToFile();//save
            return true;
        }
        return false; // book not found
    }

    //count book
    public int countBooks() {
        int count = 0;
        Book current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }
    
    // Borrow a book
    public boolean borrowBook(String id) {
        Book book = searchBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        
        boolean success = book.borrow(); // borrow() is called here explicitly
        
        if (success) {
        	saveToFile();//save
            System.out.println("Book borrowed successfully.");
            return true;
        } else {
            System.out.println("Book is already borrowed.");
            return false;
        }
 
    }

    // Return a book
    public boolean returnBook(String id) {
        Book book = searchBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }
        
        boolean success = book.returnBook(); // returnBook() is called here explicitly
        
        if (success) {
        	saveToFile();//save
            System.out.println("Book returned successfully.");
            return true;
        } else {
            System.out.println("Book is already available.");
            return false;
        }
        
    }

}
