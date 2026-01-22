package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class ModifyUserForm extends JFrame {
    private JTextField txtId, txtName, txtEmail, txtRole;
    private UserLinkedList userList;
    private static final long serialVersionUID = 1L;

    public ModifyUserForm(UserLinkedList userList) {
        this.userList = userList;
        
        setTitle("Modify User");
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("User ID to modify:");
        lblId.setBounds(20, 20, 180, 25);
        getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(200, 20, 180, 25);
        getContentPane().add(txtId);

        JLabel lblName = new JLabel("New Name:");
        lblName.setBounds(20, 60, 180, 25);
        getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(200, 60, 180, 25);
        getContentPane().add(txtName);

        JLabel lblEmail = new JLabel("New Email:");
        lblEmail.setBounds(20, 100, 180, 25);
        getContentPane().add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(200, 100, 180, 25);
        getContentPane().add(txtEmail);

        JLabel lblRole = new JLabel("New Role (librarian/student):");
        lblRole.setBounds(20, 140, 200, 25);
        getContentPane().add(lblRole);

        txtRole = new JTextField();
        txtRole.setBounds(200, 140, 180, 25);
        getContentPane().add(txtRole);

        JButton btnModify = new JButton("Modify");
        btnModify.setBounds(140, 200, 120, 30);
        getContentPane().add(btnModify);

        btnModify.addActionListener(e -> {
            String id = txtId.getText().trim().toUpperCase();
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String role = txtRole.getText().trim().toLowerCase();

            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!role.isEmpty() && !role.equals("librarian") && !role.equals("student")) {
                JOptionPane.showMessageDialog(this, "Role must be 'librarian' or 'student'", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = userList.modifyUserById(id,
                    name.isEmpty() ? null : name,
                    email.isEmpty() ? null : email,
                    role.isEmpty() ? null : role);

            if(success) {
                FileHandler.saveUsersToFile(userList);
                JOptionPane.showMessageDialog(this, "User modified successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
