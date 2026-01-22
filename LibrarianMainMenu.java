package booklinkedlist;

//import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibrarianMainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private static BookLinkedList bookList;
    private static RequestQueue requestQueue; 
    private static TransactionLinkedList transactionList; 
    private static RequestStatusList requestStatusList;
    private UserLinkedList userList;
    private JButton processRequestBtn;
    private JPanel contentPane;

    public LibrarianMainMenu(UserLinkedList userList) {
    	this.userList = userList;
    	
        setTitle("Librarian Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Initialize book,request,transaction list if not created yet
        if (bookList == null) {
            bookList = new BookLinkedList();
            bookList.loadFromFile();//load books from text file
        }
        if (requestQueue == null) {               
            requestQueue = new RequestQueue();
            requestQueue.loadFromFile();
        }   
        if (transactionList == null) {
            transactionList = new TransactionLinkedList();
            transactionList.loadFromFile();
        }
        if (requestStatusList == null) {
        	requestStatusList = new RequestStatusList();
        	requestStatusList.loadFromFile();
        }
        
        // Title
        JLabel titleLabel = new JLabel("Librarian Dashboard");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        titleLabel.setBounds(92, 10, 250, 30);
        getContentPane().add(titleLabel);
        
        // Buttons
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.setBounds(110, 50, 161, 30);
        getContentPane().add(addBookBtn);
        addBookBtn.setFocusPainted(false);//To remove the focus paint

        JButton viewBookBtn = new JButton("View All Books");
        viewBookBtn.setBounds(110, 90, 161, 30);
        getContentPane().add(viewBookBtn);
        
        JButton searchBookBtn = new JButton("Search Book");
        searchBookBtn.setBounds(110, 130, 161, 30);
        getContentPane().add(searchBookBtn);
        
        JButton deleteBookBtn = new JButton("Delete Book");
        deleteBookBtn.setBounds(110, 170, 161, 30);
        getContentPane().add(deleteBookBtn);
        
        JButton modifyBookBtn = new JButton("Modify Book");
        modifyBookBtn.setBounds(110, 210, 161, 30);
        getContentPane().add(modifyBookBtn);
    
        //Button shows current number of pending borrow requests
        processRequestBtn = new JButton(
	    	    "Process Requests (" + requestQueue.countRequests() + ")"
	    	);
	    	processRequestBtn.setBounds(110, 250, 161, 30);
	    	getContentPane().add(processRequestBtn);
        
        JButton returnBookBtn = new JButton("Process Return");
        returnBookBtn.setBounds(110, 290, 161, 30);
        getContentPane().add(returnBookBtn);
        
        JButton manageUserBtn = new JButton("Manage User");
        manageUserBtn.setBounds(110, 330, 161, 30);
        getContentPane().add(manageUserBtn);
        
        JButton historyBtn = new JButton("History");
        historyBtn.setBounds(10, 405, 100, 23);
        getContentPane().add(historyBtn);
        
        //log out button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(270, 405, 90, 23);
        getContentPane().add(logoutBtn);

        //button actions
        addBookBtn.addActionListener(e -> {
            new AddBookForm(bookList).setVisible(true); // AddBookForm writes directly to file
        });

        viewBookBtn.addActionListener(e -> {
            new ViewBookForm(bookList).setVisible(true); // Load books from file inside form and show in table
        });
        
        searchBookBtn.addActionListener(e -> {
            new SearchBookForm(bookList).setVisible(true); // Searching
        });
        
        deleteBookBtn.addActionListener(e -> {
            new DeleteBookForm(bookList).setVisible(true); // delete
        });
        
        modifyBookBtn.addActionListener(e -> {
            new ModifyBookForm(bookList).setVisible(true); // modify
        });
        
        //Open process borrow request form
        processRequestBtn.addActionListener(e -> {
            ProcessBorrowRequestForm form =
                new ProcessBorrowRequestForm(bookList, requestQueue, requestStatusList);

            //// When the form is closed, update request count on button
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override//give error
                public void windowClosed(java.awt.event.WindowEvent e) {
                    updateRequestCount();//call update method to update count
                }
            });

            form.setVisible(true);
        });
        
        returnBookBtn.addActionListener(e -> {
            new ProcessReturnForm(bookList, transactionList).setVisible(true);
        });
        
        //manage user
        manageUserBtn.addActionListener(e -> {
            new ManageUserForm(userList).setVisible(true);
        });
        
        historyBtn.addActionListener(e -> {
            new TransactionHistoryForm(transactionList).setVisible(true);
        });
        
        // Logout and return to role selection
        logoutBtn.addActionListener(e -> {
            new RoleSelection().setVisible(true); // No need to pass bookList
            dispose();
        });

        setVisible(true);
        
    }
    // Get shared book list, student side get instant update if librarian modify any books
    public static BookLinkedList getBookList() {
        if (bookList == null) {
            bookList = new BookLinkedList();
            bookList.loadFromFile();
        }
        return bookList;
    }

    // Get shared request queue
    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = new RequestQueue();
            requestQueue.loadFromFile();
        }
        return requestQueue;
    }
    // Get shared transaction list
    public static TransactionLinkedList getTransactionList() {
        if (transactionList == null) {
            transactionList = new TransactionLinkedList();
            transactionList.loadFromFile(); // load existing transactions if any
        }
        return transactionList;
    }
    
    public static RequestStatusList getRequestStatusList() {
        if (requestStatusList == null) {
            requestStatusList = new RequestStatusList();
            requestStatusList.loadFromFile();
        }
        return requestStatusList;
    }
    
    //Update button text to reflect latest request count
    public void updateRequestCount() {
        processRequestBtn.setText(
            "Process Requests (" + requestQueue.countRequests() + ")"
        );
    }
    
}


