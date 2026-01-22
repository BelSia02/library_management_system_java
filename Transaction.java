package booklinkedlist;

public class Transaction {
	private String transactionId;
    private String userId;
    private String bookId;
    private String type; // BORROW or RETURN
    private String date;
    Transaction next;

    public Transaction(String transactionId, String userId,
                       String bookId, String type, String date) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.type = type;
        this.date = date;
        this.next = null;
    }

    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public String getType() { return type; }
    public String getDate() { return date; }
}
