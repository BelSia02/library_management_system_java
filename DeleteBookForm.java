package booklinkedlist;

//import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;

public class DeleteBookForm extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;

    public DeleteBookForm(BookLinkedList list) {
        this.bookList = list;
        
        setTitle("Delete Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //title label
        JLabel titleLabel = new JLabel("Delete Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(130, 20, 150, 30);
        contentPane.add(titleLabel);

        //book id label
        JLabel idLabel = new JLabel("Enter Book ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setBounds(50, 70, 120, 25);
        contentPane.add(idLabel);

        JTextField idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        idField.setBounds(170, 70, 150, 30);
        contentPane.add(idField);

        //delete button
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteBtn.setBounds(90, 130, 100, 35);
        deleteBtn.setFocusPainted(false);
        contentPane.add(deleteBtn);

        //close button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(210, 130, 100, 35);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        deleteBtn.addActionListener(e -> {
            String id = idField.getText().trim(); //get book id
            
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Book ID!", 
                    "Empty Field", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //check if book exists
            Book book = bookList.searchBookById(id);
            
            if (book == null) {
                JOptionPane.showMessageDialog(this, "Book with ID '" + id + "' not found!", 
                    "Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //show book details and confirm deletion
            String bookInfo = "Book ID: " + book.getId() + "\n" +
                             "Title: " + book.getTitle() + "\n" +
                             "Author: " + book.getAuthor();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this book?\n\n" + bookInfo,
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            //press yes
            if (confirm == JOptionPane.YES_OPTION) {
                //delete the book
                boolean deleted = bookList.deleteBookById(id);
                
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Book deleted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    idField.setText(""); // Clear the field
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete book!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

}
