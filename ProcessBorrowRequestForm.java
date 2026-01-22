package booklinkedlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProcessBorrowRequestForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;// Reference to books
    private RequestQueue requestQueue;// Queue of borrow requests
    private RequestStatusList requestStatusList;
    private JTable requestTable;// Table to display requests
    private DefaultTableModel tableModel;

    public ProcessBorrowRequestForm(BookLinkedList list, RequestQueue queue,RequestStatusList statusList) {
        this.bookList = list;
        this.requestQueue = queue;
        this.requestStatusList = statusList;
        
        setTitle("Process Borrow Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //title label
        JLabel titleLabel = new JLabel("Pending Borrow Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(220, 10, 300, 30);
        contentPane.add(titleLabel);

        //table setup
        String[] columnNames = {"Request ID", "User ID", "Book ID", "Book Title", "Book Author"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };
        
        requestTable = new JTable(tableModel);
        requestTable.setFont(new Font("Arial", Font.PLAIN, 12));
        requestTable.setRowHeight(25);
        requestTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//able to select row
        
        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(20, 50, 650, 300);
        contentPane.add(scrollPane);

        // Count Label
        JLabel countLabel = new JLabel("Total Pending Requests: 0");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        countLabel.setBounds(20, 360, 300, 25);
        contentPane.add(countLabel);

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshBtn.setBounds(150, 400, 120, 35);
        refreshBtn.setFocusPainted(false);
        contentPane.add(refreshBtn);

        // Approve Button
        JButton approveBtn = new JButton("Approve");
        approveBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        approveBtn.setBounds(290, 400, 120, 35);
        approveBtn.setFocusPainted(false);
        contentPane.add(approveBtn);

        // Reject Button
        JButton rejectBtn = new JButton("Reject");
        rejectBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        rejectBtn.setBounds(430, 400, 120, 35);
        rejectBtn.setFocusPainted(false);
        contentPane.add(rejectBtn);

        // Close Button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(570, 400, 100, 35);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        loadRequests(countLabel);//load initial data into table

        //refresh button
        refreshBtn.addActionListener(e -> {
        	requestQueue.loadFromFile();//Reload from file
        	requestStatusList.loadFromFile();  // load all statuses
            loadRequests(countLabel); // load newest data
            JOptionPane.showMessageDialog(this, "Request list refreshed!", 
                "Refreshed", JOptionPane.INFORMATION_MESSAGE);
        });

        //approve button
        approveBtn.addActionListener(e -> {
            int selectedRow = requestTable.getSelectedRow();
            
            //if user not selecting any
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Please select a request to approve!", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Get request details from table
            String requestId = (String) tableModel.getValueAt(selectedRow, 0);
            String userId = (String) tableModel.getValueAt(selectedRow, 1);
            String bookId = (String) tableModel.getValueAt(selectedRow, 2);
            String bookTitle = (String) tableModel.getValueAt(selectedRow, 3);

            // Confirm approval
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Approve this borrow request?\n\n" +
                "Request ID: " + requestId + "\n" +
                "User ID: " + userId + "\n" +
                "Book: " + bookTitle + " (ID: " + bookId + ")",
                "Confirm Approval", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // Check if book is still available
                Book book = bookList.searchBookById(bookId);
                
                if (book == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Book no longer exists!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!book.isAvailable()) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Book is no longer available!\n" +
                        "It may have been borrowed by someone else.", 
                        "Not Available", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Process the borrow
                boolean success = bookList.borrowBook(bookId);
                
                if (success) {
                	//add transaction
                	String txId = "TX" + System.currentTimeMillis();
            	    String date = java.time.LocalDate.now().toString();

            	    Transaction tx = new Transaction(
            	        txId,
            	        userId,
            	        bookId,
            	        "BORROW",
            	        date
            	    );

            	    LibrarianMainMenu.getTransactionList().addTransaction(tx);
            	    
            	    // Update request status to APPROVED
                    requestStatusList.updateRequestStatus(requestId, "APPROVED");
            	    
            	    // Remove request from queue (auto-saves)
                    requestQueue.removeRequest(requestId);
                    
                    JOptionPane.showMessageDialog(this, 
                        "Request approved successfully!\n" +
                        "Book borrowed by User: " + userId, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadRequests(countLabel);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to process borrow!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //reject button
        rejectBtn.addActionListener(e -> {
            int selectedRow = requestTable.getSelectedRow();
            
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Please select a request to reject!", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //get details
            String requestId = (String) tableModel.getValueAt(selectedRow, 0);
            String userId = (String) tableModel.getValueAt(selectedRow, 1);
            String bookTitle = (String) tableModel.getValueAt(selectedRow, 3);

            // Confirm rejection
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Reject this borrow request?\n\n" +
                "Request ID: " + requestId + "\n" +
                "User ID: " + userId + "\n" +
                "Book: " + bookTitle,
                "Confirm Rejection", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
            	// Update request status to REJECTED
                requestStatusList.updateRequestStatus(requestId, "REJECTED");
                
            	// Remove request from queue (auto-saves)
                requestQueue.removeRequest(requestId);
                
                JOptionPane.showMessageDialog(this, 
                    "Request rejected and removed from queue.", 
                    "Rejected", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the table
                loadRequests(countLabel);
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    // Load all pending requests into table
    private void loadRequests(JLabel countLabel) {
        tableModel.setRowCount(0); // Clear table
        int count = 0;

        BorrowRequest current = requestQueue.getHead();// Traverse queue
        
        while (current != null) {
            Book book = bookList.searchBookById(current.getBookId());
            String bookTitle = (book != null) ? book.getTitle() : "Unknown";//not found show unknown
            String bookAuthor = (book != null) ? book.getAuthor() : "Unknown";
            
            Object[] row = {
                current.getRequestId(),
                current.getUserId(),
                current.getBookId(),
                bookTitle,
                bookAuthor
            };
            tableModel.addRow(row);
            count++;
            current = current.next;
        }

        countLabel.setText("Total Pending Requests: " + count);//update pending requests
    }
}