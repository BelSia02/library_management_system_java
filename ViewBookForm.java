package booklinkedlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewBookForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;//main panel
	private JTable bookTable;//table to display books
    private DefaultTableModel tableModel;//model for table data

	public ViewBookForm(BookLinkedList list) {
		setTitle("View All Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
		
        JLabel titleLabel = new JLabel("All Books in Library");//title Label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel, BorderLayout.NORTH);//place at top

        //table setup
        String[] columnNames = {"Book ID", "Title", "Author", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override//give errors when something wrong
            public boolean isCellEditable(int row, int column) {
                return false; //make table read-only
            }
        };
        
        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookTable.setRowHeight(25);
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        loadBooksIntoTable(list);//load books into table

        JScrollPane scrollPane = new JScrollPane(bookTable);//scroll pane for table
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //bottom panel with close button
        JPanel bottomPanel = new JPanel();
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);//add bottom panel

        //book count label
        JLabel countLabel = new JLabel("Total Books: " + list.countBooks());
        countLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomPanel.add(countLabel);

        setVisible(true);
    }
	
	//method to load books from linked list into table
	private void loadBooksIntoTable(BookLinkedList list) {
        Book current = list.getHead();
        
        //if list is empty, show message
        if (current == null) {
            JOptionPane.showMessageDialog(this, "No books in the library.", 
                "Empty Library", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //traverse the linked list and add each book to the table
        while (current != null) {
            String status = current.isAvailable() ? "Available" : "Borrowed";
            Object[] row = {
                current.getId(),
                current.getTitle(),
                current.getAuthor(),
                status
            };
            tableModel.addRow(row);
            current = current.next;//move to next book
        }

	}

}
