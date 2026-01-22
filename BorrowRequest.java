package booklinkedlist;

public class BorrowRequest {
	private String requestId; // unique request ID
    private String userId; // ID of the user making request
    private String bookId; // ID of the book requested
    BorrowRequest next; // pointer to next request in queue

    // Constructor
    public BorrowRequest(String requestId, String userId, String bookId) {
        this.requestId = requestId;
        this.userId = userId;
        this.bookId = bookId;
        this.next = null;
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }

    // Display borrow request
    public void display() {
        System.out.println("Request ID: " + requestId + ", User ID: " + userId + ", Book ID: " + bookId);
    }
}
