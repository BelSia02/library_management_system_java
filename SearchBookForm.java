package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class SearchBookForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;

    public SearchBookForm(BookLinkedList list) {
        this.bookList = list;
        
        setTitle("Search Book by ID");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //title label
        JLabel titleLabel = new JLabel("Search Book by ID");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(100, 20, 200, 30);
        contentPane.add(titleLabel);

        //book ID label
        JLabel idLabel = new JLabel("Enter Book ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setBounds(50, 70, 120, 25);
        contentPane.add(idLabel);

        //book ID textfield
        JTextField idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        idField.setBounds(170, 70, 150, 30);
        contentPane.add(idField);
        
        //search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        searchBtn.setBounds(145, 120, 100, 35);
        searchBtn.setFocusPainted(false);
        contentPane.add(searchBtn);

        //result display area
        JTextArea resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);//scrollable area
        scrollPane.setBounds(50, 170, 290, 60);
        contentPane.add(scrollPane);

        //close button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(145, 240, 100, 30);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        //button actions
        searchBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            
            //check if empty
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Book ID!", 
                    "Empty Field", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //search for book
            Book book = bookList.searchBookById(id);
            
            if (book != null) {
                //display book details
                String status = book.isAvailable() ? "Available" : "Borrowed";
                String result = "Book ID: " + book.getId() + "\n" +
                               "Title: " + book.getTitle() + "\n" +
                               "Author: " + book.getAuthor() + "\n" +
                               "Status: " + status;
                resultArea.setText(result);
            } else {
                resultArea.setText("Book not found!");
                JOptionPane.showMessageDialog(this, "No book found with ID: " + id, 
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}