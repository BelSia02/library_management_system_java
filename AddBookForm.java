package booklinkedlist;

import javax.swing.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddBookForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;//main panel

	public AddBookForm(BookLinkedList list) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //close this window only, return to previous page
		setBounds(100, 100, 450, 300);//initial window size and position
		
		contentPane = new JPanel();//main content panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//padding around edges
		setContentPane(contentPane);
		
		setTitle("Add New Book");//window title
        setSize(300, 200);//window size
        setLayout(null);//absolute positioning
        setLocationRelativeTo(null);//center window on screen

        JLabel idLabel = new JLabel("Book ID:"); //book id label
        idLabel.setBounds(20, 20, 80, 25);
        add(idLabel);

        JTextField idField = new JTextField();//book id text field
        idField.setBounds(100, 20, 150, 25);
        add(idField);

        JLabel titleLabel = new JLabel("Title:"); //title label
        titleLabel.setBounds(20, 60, 80, 25);
        add(titleLabel);

        JTextField titleField = new JTextField();//title text field
        titleField.setBounds(100, 60, 150, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");//author label
        authorLabel.setBounds(20, 100, 80, 25);
        add(authorLabel);

        JTextField authorField = new JTextField();//author text field
        authorField.setBounds(100, 100, 150, 25);
        add(authorField);

        JButton addBtn = new JButton("Add");//add book button
        addBtn.setBounds(100, 140, 100, 30);
        add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //get values from text fields
            	String id = idField.getText();
                String title = titleField.getText();
                String author = authorField.getText();

                //check if any field is empty
                if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                    return;
                }
                
                //check duplication by comparing current id
                if (list.searchBookById(id) != null) {
                    JOptionPane.showMessageDialog(null, "Book ID already exists! Please use a different ID.");
                    return;
                }

                list.addBook(new Book(id, title, author, true)); //add new book to the linked list
                JOptionPane.showMessageDialog(null, "Book added successfully!");
                dispose();//close this form after adding
            }
        });

        setVisible(true);
    }


}
