package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class DeleteUserForm extends JFrame {
    private JTextField txtId;
    private UserLinkedList userList;

    public DeleteUserForm(UserLinkedList userList) {
        this.userList = userList;
        setTitle("Delete User");
        setSize(400, 200);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JLabel lblId = new JLabel("User ID to delete:");
        lblId.setBounds(30, 30, 150, 25);
        getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 30, 140, 25);
        getContentPane().add(txtId);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(110, 80, 120, 30);
        getContentPane().add(btnDelete);

        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim().toUpperCase();//convert to uppercase
            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = userList.deleteUserById(id);
            if(success) {
                FileHandler.saveUsersToFile(userList);
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
