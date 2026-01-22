package booklinkedlist;

public class UserLinkedList {

    private User head;  // points to first user

    public UserLinkedList() {
        head = null;
    }

    public User getHead() {
        return head;
    }

    //load user from text file
    public void loadFromFile() {
        UserLinkedList loadedList = FileHandler.loadUsersFromFile();
        this.head = loadedList.getHead();
    }

    //save to text file
    public void saveToFile() {
        FileHandler.saveUsersToFile(this);
    }

    //add user
    public void addUser(User newUser) {
        if (head == null) {
            head = newUser;
        } else {
            User current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newUser;
        }

        saveToFile();   // auto save
    }

    public void displayUsers() {
        User current = head;
        if (current == null) {
            System.out.println("No users found.");
            return;
        }

        while (current != null) {
            current.display();
            current = current.next;
        }
    }

    public User searchUserById(String id) {
        User current = head;
        while (current != null) {
            if (current.getId().equalsIgnoreCase(id))
                return current;
            current = current.next;
        }
        return null;
    }
    
    public User searchUserByIdIgnoreCase(String userId) {
        User current = head;
        while (current != null) {
            if (current.getId().equalsIgnoreCase(userId)) { // ignore case
                return current;
            }
            current = current.next;
        }
        return null; // not found
    }

    public boolean deleteUserById(String id) {

        if (head == null) return false;

        if (head.getId().equals(id)) {
            head = head.next;
            saveToFile(); // auto save
            return true;
        }

        User current = head;
        while (current.next != null && !current.next.getId().equals(id)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            saveToFile(); // auto save
            return true;
        }

        return false;
    }

    //modify user
    public boolean modifyUserById(String id, String newName, String newEmail, String newRole) {
        User user = searchUserById(id);

        if (user != null) {
            if (newName != null) user.setName(newName);
            if (newEmail != null) user.setEmail(newEmail);
            if (newRole != null) user.setRole(newRole);

            saveToFile();  // auto save
            return true;
        }

        return false;
    }

    //count
    public int countUsers() {
        int count = 0;
        User current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }
}
