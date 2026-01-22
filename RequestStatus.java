package booklinkedlist;

public class RequestStatus {
	private String requestId;
    private String userId;
    private String bookId;
    private String status;// "PENDING", "APPROVED", "REJECTED"
    private String date;
    RequestStatus next;

    public RequestStatus(String requestId, String userId, String bookId, String status, String date) {
        this.requestId = requestId;
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
        this.date = date;
        this.next = null;
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public String getStatus() { return status; }
    public String getDate() { return date; }

    // Setter for status
    public void setStatus(String status) { this.status = status; }
}
