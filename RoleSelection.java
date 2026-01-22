package booklinkedlist;

import java.awt.*;
import javax.swing.*;
import java.util.Locale;

public class RoleSelection extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static UserLinkedList userList;

    public RoleSelection() {
    	//load users from file on startup
    	userList = new UserLinkedList();
        userList.loadFromFile();//load persisted users into memory
    	
        setTitle("Role Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //welcome label
        JLabel lblWelcome = new JLabel("Welcome to the Library Management System");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setBounds(22, 10, 400, 30);
        contentPane.add(lblWelcome);
        
        //enter user id label
        JLabel lblUserId = new JLabel("Enter User ID:");
        lblUserId.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUserId.setBounds(70, 70, 120, 25);
        contentPane.add(lblUserId);

        //user id text field
        JTextField userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdField.setBounds(190, 70, 150, 30);
        contentPane.add(userIdField);

        //login button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLogin.setBounds(170, 140, 100, 35);
        btnLogin.setFocusPainted(false);
        contentPane.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String userId = userIdField.getText().trim().toUpperCase();//get user id, trim whitespace, convert to uppercase	
            
            //check if input is empty
            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter your User ID!", 
                    "Empty Field", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            //validate user ID format
            if (!isValidUserId(userId)) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid User ID format!\n\n" +
                    "Librarian: L + 3 digits (e.g., L001)\n" +
                    "Student: S + 3 digits (e.g., S001)", 
                    "Invalid Format", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            //check if user exists, show error if not found
            User user = userList.searchUserById(userId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, 
                    "User ID not found!\n\n" +
                    "Please contact administrator or use a valid ID.", 
                    "User Not Found", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //store logged in user for further use
            String loggedInUserId = user.getId();
            
            //open main menu based on role of id
            if (user.getRole().equalsIgnoreCase("librarian")) {
                new LibrarianMainMenu(userList).setVisible(true); //open librarian main menu
                dispose();
            } else if (user.getRole().equalsIgnoreCase("student")) {
                new StudentMainMenu(userList, loggedInUserId).setVisible(true);//open student main menu
                dispose();
            }
        });
        
        userIdField.addActionListener(e -> btnLogin.doClick());//allow to press enter key to login

        setVisible(true); //show the frame
    }
    
    //validate user id format L001-L999 or S001-S999
    private boolean isValidUserId(String userId) {
        if (userId.length() != 4) return false;//id must be 4 characters
        
        char firstChar = userId.charAt(0);
        if (firstChar != 'L' && firstChar != 'S') return false; //first character must be L or S
        
        String digits = userId.substring(1);//remaining characters must be digits(substring(1)=(all characters after the first one)
        try {
            int num = Integer.parseInt(digits);//convert into integer like 001 to 1 / 010 to 10
            return num >= 1 && num <= 999;//only 1–999 allowed
        } catch (NumberFormatException e) {
            return false;//non numeric input fails validation
        }
    }
    
 	//getter for user list, will call by librarian and student
    public static UserLinkedList getUserList() {
        if (userList == null) {
            userList = FileHandler.loadUsersFromFile();
        }
        return userList;
    }

    //Main method to start the application
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        EventQueue.invokeLater(() -> {
            new RoleSelection().setVisible(true);
        });
    }
}
