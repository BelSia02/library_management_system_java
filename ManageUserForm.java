package booklinkedlist;

import javax.swing.*;
import java.awt.*;

public class ManageUserForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private static UserLinkedList userList;

    public ManageUserForm(UserLinkedList userList) {
        ManageUserForm.userList = userList; // static reference to userList

        setTitle("Manage Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 10, 250, 30);
        getContentPane().add(titleLabel);

        // Buttons
        JButton addUserBtn = new JButton("Add User");
        addUserBtn.setBounds(120, 60, 150, 30);
        addUserBtn.setFocusPainted(false);
        getContentPane().add(addUserBtn);

        JButton modifyUserBtn = new JButton("Modify User");
        modifyUserBtn.setBounds(120, 100, 150, 30);
        modifyUserBtn.setFocusPainted(false);
        getContentPane().add(modifyUserBtn);

        JButton deleteUserBtn = new JButton("Delete User");
        deleteUserBtn.setBounds(120, 140, 150, 30);
        deleteUserBtn.setFocusPainted(false);
        getContentPane().add(deleteUserBtn);

        JButton viewUserBtn = new JButton("View Users");
        viewUserBtn.setBounds(120, 180, 150, 30);
        viewUserBtn.setFocusPainted(false);
        getContentPane().add(viewUserBtn);

        // Button actions
        addUserBtn.addActionListener(e -> {
            new AddUserForm(userList).setVisible(true);
        });

        modifyUserBtn.addActionListener(e -> {
            new ModifyUserForm(userList).setVisible(true);
        });

        deleteUserBtn.addActionListener(e -> {
            new DeleteUserForm(userList).setVisible(true);
        });

        viewUserBtn.addActionListener(e -> {
            new ViewUserForm(userList).setVisible(true);
        });

        setVisible(true);
    }

    public static UserLinkedList getUserList() {
        return userList;
    }
}
