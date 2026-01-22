package booklinkedlist;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionHistoryForm extends JFrame {
	
	private TransactionLinkedList transactionList;
    private DefaultTableModel model;
    private JTable historyTable;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public TransactionHistoryForm(TransactionLinkedList transactionList) {
		this.transactionList = transactionList;
		this.transactionList.loadFromFile();
		
		setTitle("Transaction History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //title label
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(280, 15, 250, 30);
        contentPane.add(titleLabel);
        
        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(30, 60, 730, 50);
        filterPanel.setLayout(null);
        filterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Filter Transactions",
            0,
            0,
            new Font("Arial", Font.BOLD, 12)
        ));
        contentPane.add(filterPanel);

     	// User ID Filter
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        userIdLabel.setBounds(20, 20, 70, 25);
        filterPanel.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 13));
        userIdField.setBounds(90, 20, 120, 25);
        filterPanel.add(userIdField);

        // Book ID Filter
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        bookIdLabel.setBounds(230, 20, 70, 25);
        filterPanel.add(bookIdLabel);

        JTextField bookIdField = new JTextField();
        bookIdField.setFont(new Font("Arial", Font.PLAIN, 13));
        bookIdField.setBounds(300, 20, 120, 25);
        filterPanel.add(bookIdField);

        // Search Button
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        searchBtn.setBounds(440, 20, 100, 25);
        searchBtn.setFocusPainted(false);
        filterPanel.add(searchBtn);

        // Show All Button
        JButton showAllBtn = new JButton("Show All");
        showAllBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        showAllBtn.setBounds(550, 20, 100, 25);
        showAllBtn.setFocusPainted(false);
        filterPanel.add(showAllBtn);
        
        String[] cols = {"Transaction ID", "User ID", "Book ID", "Type", "Date"};
        model = new DefaultTableModel(cols, 0) {
            @Override//get error from compile time
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        historyTable = new JTable(model);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 12));
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Transaction ID
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // User ID
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Book ID
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Type
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Date
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBounds(30, 125, 730, 310);
        contentPane.add(scrollPane);
        
        JLabel countLabel = new JLabel("Total Transactions: 0");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        countLabel.setBounds(30, 445, 300, 25);
        contentPane.add(countLabel);
        
        //button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        closeBtn.setBounds(660, 445, 100, 30);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);
        
        loadAllTransactions(countLabel); //load all transactions
        
        // Search Button - Filter by User ID and/or Book ID
        searchBtn.addActionListener(e -> {
            String userId = userIdField.getText().trim().toUpperCase();
            String bookId = bookIdField.getText().trim();

            if (userId.isEmpty() && bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter at least one filter (User ID or Book ID)!",
                    "No Filter", JOptionPane.WARNING_MESSAGE);
                return;
            }

            filterTransactions(userId, bookId, countLabel);
        });

        // Show All Button - Display all transactions
        showAllBtn.addActionListener(e -> {
            userIdField.setText("");
            bookIdField.setText("");
            loadAllTransactions(countLabel);
        });
        
     	// Close Button
        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
       
	}
	
	private void loadAllTransactions(JLabel countLabel) {
        model.setRowCount(0); // Clear table
        int count = 0;

        Transaction current = transactionList.getHead();
        while (current != null) {
            Object[] row = {
                current.getTransactionId(),
                current.getUserId(),
                current.getBookId(),
                current.getType(),
                current.getDate()
            };
            model.addRow(row);
            count++;
            current = current.next;
        }

        countLabel.setText("Total Transactions: " + count);
        
        if (count == 0) {
            JOptionPane.showMessageDialog(this,
                "No transaction history found.",
                "Empty History", JOptionPane.INFORMATION_MESSAGE);
            }
	}
        
    private void filterTransactions(String userId, String bookId, JLabel countLabel) {
        model.setRowCount(0); // Clear table
        int count = 0;

        Transaction current = transactionList.getHead();
        while (current != null) {
            boolean matchUser = userId.isEmpty() || current.getUserId().equalsIgnoreCase(userId);
            boolean matchBook = bookId.isEmpty() || current.getBookId().equalsIgnoreCase(bookId);

            if (matchUser && matchBook) {
                Object[] row = {
                    current.getTransactionId(),
                    current.getUserId(),
                    current.getBookId(),
                    current.getType(),
                    current.getDate()
                };
                model.addRow(row);
                count++;
            }
            current = current.next;
        }

        // Update count label with filter info
        String filterInfo = "";
        if (!userId.isEmpty() && !bookId.isEmpty()) {
            filterInfo = " (Filtered by User: " + userId + ", Book: " + bookId + ")";
        } else if (!userId.isEmpty()) {
            filterInfo = " (Filtered by User: " + userId + ")";
        } else if (!bookId.isEmpty()) {
            filterInfo = " (Filtered by Book: " + bookId + ")";
        }
        
        countLabel.setText("Total Transactions: " + count + filterInfo);

        if (count == 0) {
            JOptionPane.showMessageDialog(this,
                "No transactions found matching the filter criteria.",
                "No Results", JOptionPane.INFORMATION_MESSAGE);
        }
	}
}
