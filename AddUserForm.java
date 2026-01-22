package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class AddUserForm extends JFrame {
    private JTextField txtId, txtName, txtEmail, txtRole;
    private UserLinkedList userList;
    private static final long serialVersionUID = 1L;

    public AddUserForm(UserLinkedList userList) {
        this.userList = userList;
        
        setTitle("Add User");
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("User ID (L001 / S001):");
        lblId.setBounds(40, 30, 160, 25);
        getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(40, 70, 160, 25);
        getContentPane().add(lblName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 110, 160, 25);
        getContentPane().add(lblEmail);

        JLabel lblRole = new JLabel("Role (librarian / student):");
        lblRole.setBounds(40, 150, 200, 25);
        getContentPane().add(lblRole);
        
        txtId = new JTextField();
        txtId.setBounds(220, 30, 140, 25);
        getContentPane().add(txtId);

        txtName = new JTextField();
        txtName.setBounds(220, 70, 140, 25);
        getContentPane().add(txtName);

        txtEmail = new JTextField();
        txtEmail.setBounds(220, 110, 140, 25);
        getContentPane().add(txtEmail);

        txtRole = new JTextField();
        txtRole.setBounds(220, 150, 140, 25);
        getContentPane().add(txtRole);

        JButton btnAdd = new JButton("Add User");
        btnAdd.setBounds(150, 200, 120, 30);
        btnAdd.setFocusPainted(false);
        add(btnAdd);
        
        btnAdd.addActionListener(e -> {
            String id = txtId.getText().trim().toUpperCase();
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String role = txtRole.getText().trim().toLowerCase();

            //check all field
            if(id.isEmpty() || name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //check id format
            if(!id.matches("[LS]\\d{3}")) {
                JOptionPane.showMessageDialog(this, "Invalid User ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //check role text
            if(!role.equals("librarian") && !role.equals("student")) {
                JOptionPane.showMessageDialog(this, "Role must be 'librarian' or 'student'", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //role id check
            if (role.equals("student") && !id.startsWith("S")) {
                JOptionPane.showMessageDialog(this, "Student ID must start with 'S'");
                return;
            }

            if (role.equals("librarian") && !id.startsWith("L")) {
                JOptionPane.showMessageDialog(this, "Librarian ID must start with 'L'");
                return;
            }

            //duplication check
            if(userList.searchUserById(id) != null) {
                JOptionPane.showMessageDialog(this, "User ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            userList.addUser(new User(id, name, email, role));
            FileHandler.saveUsersToFile(userList);
            JOptionPane.showMessageDialog(this, "User added successfully!");
            dispose();
        });
    }
}
