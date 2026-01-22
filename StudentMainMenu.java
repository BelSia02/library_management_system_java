package booklinkedlist;

import javax.swing.*;
import java.awt.Font;

public class StudentMainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private BookLinkedList bookList;
    private RequestQueue requestQueue;
    private RequestStatusList requestStatusList;
    private UserLinkedList userList;
    private String loggedInUserId;


    public StudentMainMenu(UserLinkedList userList, String loggedInUserId) {
    	this.userList = userList;
    	this.loggedInUserId = loggedInUserId.toUpperCase();
    	
        setTitle("Student Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        bookList = LibrarianMainMenu.getBookList(); // get from librarian
        if (bookList.getHead() == null) {  // if empty, load from file
            bookList.loadFromFile();
        }
        requestQueue = LibrarianMainMenu.getRequestQueue(); 
        requestStatusList = LibrarianMainMenu.getRequestStatusList();
        requestStatusList.loadFromFile();

        //buttons
        JButton viewBooksBtn = new JButton("View All Books");
        viewBooksBtn.setBounds(109, 50, 154, 28);
        viewBooksBtn.setFocusPainted(false);
        getContentPane().add(viewBooksBtn);

        JButton searchBookBtn = new JButton("Search Book");
        searchBookBtn.setBounds(109, 88, 154, 28);
        searchBookBtn.setFocusPainted(false);
        getContentPane().add(searchBookBtn);

        JButton requestBorrowBtn = new JButton("Request to Borrow");
        requestBorrowBtn.setBounds(109, 126, 154, 28);
        requestBorrowBtn.setFocusPainted(false);
        getContentPane().add(requestBorrowBtn);

        JButton viewMyRequestsBtn = new JButton("View My Requests");
        viewMyRequestsBtn.setBounds(109, 164, 154, 28);
        viewMyRequestsBtn.setFocusPainted(false);
        getContentPane().add(viewMyRequestsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(292, 229, 84, 23);
        logoutBtn.setFocusPainted(false);
        getContentPane().add(logoutBtn);
        
        JLabel lblNewLabel = new JLabel("Student Dashboard");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel.setBounds(95, 10, 192, 30);
        getContentPane().add(lblNewLabel);

        //button action
        viewBooksBtn.addActionListener(e -> {
            new ViewBookForm(bookList).setVisible(true); // Reuse librarian's form
        });

        searchBookBtn.addActionListener(e -> {
            new SearchBookForm(bookList).setVisible(true); // Reuse librarian's form
        });

        requestBorrowBtn.addActionListener(e -> {
            new RequestBorrowForm(bookList, requestQueue, requestStatusList, userList, loggedInUserId).setVisible(true);
        });

        viewMyRequestsBtn.addActionListener(e -> {
            new ViewMyRequestsForm(requestQueue, requestStatusList, bookList, loggedInUserId).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            new RoleSelection().setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}