package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class RequestBorrowForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;
    private UserLinkedList userList;
    private String loggedInUserId;
    private RequestQueue requestQueue;
    private RequestStatusList requestStatusList;

    public RequestBorrowForm(BookLinkedList bookList, RequestQueue requestQueue,RequestStatusList statusList, 
    		UserLinkedList userList, String loggedInUserId) {
        this.bookList = bookList;
        this.requestQueue = requestQueue;
        this.requestStatusList = statusList;
        this.userList = userList;
        this.loggedInUserId = loggedInUserId.toUpperCase();
        
        setTitle("Request to Borrow Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title Label
        JLabel titleLabel = new JLabel("Request to Borrow Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(80, 20, 250, 30);
        contentPane.add(titleLabel);

        // Show logged-in User ID
        JLabel userIdLabel = new JLabel("User ID: " + loggedInUserId);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setBounds(50, 70, 250, 25);
        contentPane.add(userIdLabel);

        // Book ID Label
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bookIdLabel.setBounds(50, 110, 120, 25);
        contentPane.add(bookIdLabel);

        // Book ID TextField
        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        bookIdField.setBounds(170, 110, 150, 30);
        contentPane.add(bookIdField);

        // Request Button
        JButton requestBtn = new JButton("Request");
        requestBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        requestBtn.setBounds(90, 160, 100, 35);
        requestBtn.setFocusPainted(false);
        contentPane.add(requestBtn);

        // Close Button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(210, 160, 100, 35);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        // Button Actions
        requestBtn.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter Book ID!",
                        "Empty Field",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = bookList.searchBookById(bookId);
            if (book == null) {
                JOptionPane.showMessageDialog(this,
                        "Book with ID '" + bookId + "' not found!",
                        "Book Not Found",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User student = userList.searchUserById(loggedInUserId);
            if (student == null || !student.getRole().equalsIgnoreCase("student")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid student!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //check duplication request
            if (requestStatusList.hasPendingRequest(loggedInUserId, bookId)) {
                JOptionPane.showMessageDialog(this,
                        "You already have a pending request for this book!\n\n" +
                        "Book: " + book.getTitle() + "\n" +
                        "Author: " + book.getAuthor() + "\n\n" +
                        "Please wait for librarian's response.",
                        "Duplicate Request",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(this,
                        "Sorry, this book is currently borrowed.\n\n" +
                                "Book: " + book.getTitle() + "\n" +
                                "Author: " + book.getAuthor(),
                        "Book Not Available",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Generate unique request ID
            String requestId = "REQ" + System.currentTimeMillis();
            String date = java.time.LocalDate.now().toString();

            // Add to request queue
            BorrowRequest request = new BorrowRequest(requestId, loggedInUserId, bookId);
            requestQueue.enqueue(request);
            requestQueue.saveToFile();

            // Add to request status with "PENDING" status
            RequestStatus status = new RequestStatus(requestId, loggedInUserId, bookId, "PENDING", date);
            requestStatusList.addRequestStatus(status);

            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Borrow request submitted successfully!\n\n" +
                "Request ID: " + requestId + "\n" +
                "Book: " + book.getTitle() + "\n" +
                "Author: " + book.getAuthor() + "\n\n" +
                "Please wait for librarian approval.",
                "Request Submitted", JOptionPane.INFORMATION_MESSAGE);

            // Clear book field only
            bookIdField.setText("");
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
