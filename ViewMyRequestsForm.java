package booklinkedlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewMyRequestsForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private RequestQueue requestQueue;
    private RequestStatusList requestStatusList;
    private BookLinkedList bookList;
    private String loggedInUserId;
    private JTable requestTable;
    private DefaultTableModel tableModel;

    public ViewMyRequestsForm(RequestQueue queue, RequestStatusList statusList, 
                             BookLinkedList bookList, String loggedInUserId) {
        this.requestQueue = queue;
        this.requestStatusList = statusList;
        this.bookList = bookList;
        this.loggedInUserId = loggedInUserId.toUpperCase();

        setTitle("My Borrow Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title Label
        JLabel titleLabel = new JLabel("My Borrow Requests (" + loggedInUserId + ")");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(220, 10, 400, 30);
        contentPane.add(titleLabel);

        // Info Label - Explain statuses
        JLabel infoLabel = new JLabel("Status: PENDING (Waiting) | APPROVED (Borrowed) | REJECTED (Denied)");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setBounds(150, 45, 500, 20);
        contentPane.add(infoLabel);

        // Table setup - Added Book Title column
        String[] columnNames = {"Request ID", "Book ID", "Book Title", "Status", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };

        requestTable = new JTable(tableModel);
        requestTable.setFont(new Font("Arial", Font.PLAIN, 13));
        requestTable.setRowHeight(25);
        requestTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Set column widths
        requestTable.getColumnModel().getColumn(0).setPreferredWidth(130); // Request ID
        requestTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Book ID
        requestTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Book Title
        requestTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Status
        requestTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Date

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBounds(30, 75, 680, 310);
        contentPane.add(scrollPane);

        // Count Label
        JLabel countLabel = new JLabel("Total Requests: 0");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        countLabel.setBounds(30, 395, 300, 25);
        contentPane.add(countLabel);

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshBtn.setBounds(500, 395, 100, 30);
        refreshBtn.setFocusPainted(false);
        contentPane.add(refreshBtn);

        // Close Button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(610, 395, 100, 30);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);


        // Button Actions
        refreshBtn.addActionListener(e -> {
            // Reload from file
            requestStatusList.loadFromFile();
            loadMyRequests(countLabel);
            JOptionPane.showMessageDialog(this,
                    "Request list refreshed!",
                    "Refreshed",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        closeBtn.addActionListener(e -> dispose());

        // Load initial data
        loadMyRequests(countLabel);

        setVisible(true);
    }

    // Load logged-in user's requests with their status
    private void loadMyRequests(JLabel countLabel) {
        tableModel.setRowCount(0);
        int count = 0;

        // Get all requests for this user from RequestStatusList
        RequestStatusList myRequests = requestStatusList.getRequestsByUser(loggedInUserId);
        RequestStatus current = myRequests.getHead();

        while (current != null) {
            // Get book details
            Book book = bookList.searchBookById(current.getBookId());
            String bookTitle = (book != null) ? book.getTitle() : "Unknown";

            Object[] row = {
                current.getRequestId(),
                current.getBookId(),
                bookTitle,
                current.getStatus(),  //Shows PENDING, APPROVED, or REJECTED
                current.getDate()
            };
            tableModel.addRow(row);
            count++;
            current = current.next;
        }

        // Update count label
        countLabel.setText("Total Requests: " + count);

        // Show message if no requests
        if (count == 0) {
            JOptionPane.showMessageDialog(this,
                    "You have no request history yet.",
                    "No Requests",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}