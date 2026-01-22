package booklinkedlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ViewUserForm extends JFrame {
    private UserLinkedList userList;
    private JTable table;
    private DefaultTableModel tableModel;
    private static final long serialVersionUID = 1L;

    public ViewUserForm(UserLinkedList userList) {
        this.userList = userList;
        setTitle("View Users");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID","Name","Email","Role"}, 0);
        table = new JTable(tableModel);// JTable uses the table model to display data
        add(new JScrollPane(table), BorderLayout.CENTER); // Add table inside a scroll pane

        refreshTable();//load user into table
    }

    private void refreshTable() {
        tableModel.setRowCount(0);// Remove all existing rows before reloading
        User current = userList.getHead();  // Start from the head of the linked list
        while(current != null) {
            Vector<String> row = new Vector<>();// Create a row for the current user
            row.add(current.getId());
            row.add(current.getName());
            row.add(current.getEmail());
            row.add(current.getRole());
            tableModel.addRow(row);// Add the row to the table model
            current = current.next; // Move to next user in linked list
        }
    }
}
