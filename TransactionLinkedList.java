package booklinkedlist;

public class TransactionLinkedList {
	private Transaction head;

    public TransactionLinkedList() {
        head = null;
    }

    public Transaction getHead() {
        return head;
    }

    public void addTransaction(Transaction t) {
        if (head == null) {
            head = t;
        } else {
            Transaction current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = t;
        }
        saveToFile();//save
    }

    public void loadFromFile() {
        TransactionLinkedList loaded = FileHandler.loadTransactionsFromFile();
        this.head = loaded.getHead();
    }

    public void saveToFile() {
        FileHandler.saveTransactionsToFile(this);
    }
}
