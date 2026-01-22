package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class ProcessReturnForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;
    private TransactionLinkedList transactionList;

    public ProcessReturnForm(BookLinkedList list, TransactionLinkedList transactionList) {
        this.bookList = list;
        this.transactionList = transactionList;
        
        setTitle("Process Book Return");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title Label
        JLabel titleLabel = new JLabel("Process Book Return");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(100, 20, 250, 30);
        contentPane.add(titleLabel);

        // User ID Label
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setBounds(50, 70, 120, 25);
        contentPane.add(userIdLabel);

        // User ID TextField
        JTextField userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdField.setBounds(170, 70, 150, 30);
        contentPane.add(userIdField);

        // Book ID Label
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bookIdLabel.setBounds(50, 115, 120, 25);
        contentPane.add(bookIdLabel);

        // Book ID TextField
        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        bookIdField.setBounds(170, 115, 150, 30);
        contentPane.add(bookIdField);

        // Return Button
        JButton returnBtn = new JButton("Return");
        returnBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        returnBtn.setBounds(90, 175, 100, 35);
        returnBtn.setFocusPainted(false);
        contentPane.add(returnBtn);

        // Close Button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(210, 175, 100, 35);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        //return button
        returnBtn.addActionListener(e -> {
            String userId = userIdField.getText().trim().toUpperCase();
            String bookId = bookIdField.getText().trim();
            
            // Validate inputs
            if (userId.isEmpty() || bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter both User ID and Book ID!", 
                    "Empty Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check if book exists
            Book book = bookList.searchBookById(bookId);
            
            if (book == null) {
                JOptionPane.showMessageDialog(this, 
                    "Book with ID '" + bookId + "' not found!", 
                    "Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if book is already available
            if (book.isAvailable()) {
                JOptionPane.showMessageDialog(this, 
                    "Book is already available (not borrowed)!\n\n" +
                    "Book ID: " + book.getId() + "\n" +
                    "Title: " + book.getTitle() + "\n" +
                    "Author: " + book.getAuthor(),
                    "Already Available", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Show book details and confirm return
            String bookInfo = "User ID: " + userId + "\n" +
                             "Book ID: " + book.getId() + "\n" +
                             "Title: " + book.getTitle() + "\n" +
                             "Author: " + book.getAuthor() + "\n" +
                             "Current Status: Borrowed";
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Process return for this book?\n\n" + bookInfo,
                "Confirm Return", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // Process the return
                boolean returned = bookList.returnBook(bookId);
                
                if (returned) {
                    // Add transaction record
                    String txId = "TX" + System.currentTimeMillis();
                    String date = java.time.LocalDate.now().toString();

                    Transaction tx = new Transaction(
                        txId,
                        userId,    //using the userId from input field
                        bookId,
                        "RETURN",
                        date
                    );

                    transactionList.addTransaction(tx);//add transaction
                    
                    JOptionPane.showMessageDialog(this, 
                        "Book returned successfully!\n" +
                        "User: " + userId + "\n" +
                        "Status changed to: Available", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Clear the fields
                    userIdField.setText("");
                    bookIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to return book!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}