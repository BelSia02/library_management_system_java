package booklinkedlist;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ModifyBookForm extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BookLinkedList bookList;
    private JTextField idField, titleField, authorField;
    private JButton searchBtn, modifyBtn, closeBtn;

    public ModifyBookForm(BookLinkedList list) {
        this.bookList = list;
        
        setTitle("Modify Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //title label
        JLabel titleLabel = new JLabel("Modify Book Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(130, 10, 200, 30);
        contentPane.add(titleLabel);

        //book id (search)
        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setBounds(50, 60, 80, 25);
        contentPane.add(idLabel);

        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        idField.setBounds(140, 60, 150, 30);
        contentPane.add(idField);

        searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        searchBtn.setBounds(300, 60, 90, 30);
        searchBtn.setFocusPainted(false);
        contentPane.add(searchBtn);

        //new title
        JLabel newTitleLabel = new JLabel("New Title:");
        newTitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newTitleLabel.setBounds(50, 110, 80, 25);
        contentPane.add(newTitleLabel);

        titleField = new JTextField();
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setBounds(140, 110, 250, 30);
        titleField.setEnabled(false); // Disabled until book is found
        contentPane.add(titleField);

        //author
        JLabel newAuthorLabel = new JLabel("New Author:");
        newAuthorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newAuthorLabel.setBounds(50, 160, 90, 25);
        contentPane.add(newAuthorLabel);

        authorField = new JTextField();
        authorField.setFont(new Font("Arial", Font.PLAIN, 14));
        authorField.setBounds(140, 160, 250, 30);
        authorField.setEnabled(false); // Disabled until book is found
        contentPane.add(authorField);
        
        ///modify button
        modifyBtn = new JButton("Modify");
        modifyBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        modifyBtn.setBounds(120, 270, 100, 35);
        modifyBtn.setFocusPainted(false);
        modifyBtn.setEnabled(false); // Disabled until book is found
        contentPane.add(modifyBtn);

        //close
        closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setBounds(240, 270, 100, 35);
        closeBtn.setFocusPainted(false);
        contentPane.add(closeBtn);

        searchBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            
            //validate input
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Book ID!", 
                    "Empty Field", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = bookList.searchBookById(id);//search book
            
            //if book not null then can edit, else cant edit
            if (book != null) {
                //populate fields with current book details
                titleField.setText(book.getTitle());
                authorField.setText(book.getAuthor());
                
                //enable fields for editing
                titleField.setEnabled(true);
                authorField.setEnabled(true);
                modifyBtn.setEnabled(true);
                
                JOptionPane.showMessageDialog(this, "Book found! You can now modify the details.", 
                    "Book Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //clear and disable fields
                titleField.setText("");
                authorField.setText("");
                titleField.setEnabled(false);
                authorField.setEnabled(false);
                //statusComboBox.setEnabled(false);
                modifyBtn.setEnabled(false);
                
                JOptionPane.showMessageDialog(this, "Book with ID '" + id + "' not found!", 
                    "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        });

        modifyBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String newTitle = titleField.getText().trim();
            String newAuthor = authorField.getText().trim();

            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author cannot be empty!", 
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //confirm modification
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to modify this book?",
                "Confirm Modification", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean modified = bookList.modifyBookById(id, newTitle, newAuthor, null);//attempt modification in linked list
                
                if (modified) {
                    JOptionPane.showMessageDialog(this, "Book modified successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    //clear and reset form
                    idField.setText("");
                    titleField.setText("");
                    authorField.setText("");
                    titleField.setEnabled(false);
                    authorField.setEnabled(false);
                    modifyBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to modify book!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

}
