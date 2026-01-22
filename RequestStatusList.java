package booklinkedlist;

public class RequestStatusList {
	private RequestStatus head;

    public RequestStatusList() {
        head = null;
    }

    public RequestStatus getHead() {
        return head;
    }
    
    // For loading from file without auto-save
    public void setHead(RequestStatus head) {
        this.head = head;
    }

    //load from file
    public void loadFromFile() {
        RequestStatusList loaded = FileHandler.loadRequestStatusFromFile();
        this.head = loaded.getHead();
    }

    //save to file
    public void saveToFile() {
        FileHandler.saveRequestStatusToFile(this);
    }

    //add request
    public void addRequestStatus(RequestStatus status) {
        if (head == null) {
            head = status;
        } else {
            RequestStatus current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = status;
        }
        saveToFile();
    }

    // check pending request
    public boolean hasPendingRequest(String userId, String bookId) {
        RequestStatus current = head;
        while (current != null) {
            if (current.getUserId().equals(userId) && 
                current.getBookId().equals(bookId) && 
                current.getStatus().equals("PENDING")) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //update status
    public void updateRequestStatus(String requestId, String newStatus) {
        RequestStatus current = head;
        while (current != null) {
            if (current.getRequestId().equals(requestId)) {
                current.setStatus(newStatus);
                saveToFile();
                return;
            }
            current = current.next;
        }
    }

    //get request by user
    public RequestStatusList getRequestsByUser(String userId) {
        RequestStatusList userRequests = new RequestStatusList();
        RequestStatus current = head;

        while (current != null) {
            if (current.getUserId().equals(userId)) {
                RequestStatus copy = new RequestStatus(
                    current.getRequestId(),
                    current.getUserId(),
                    current.getBookId(),
                    current.getStatus(),
                    current.getDate()
                );
                
                // Add without saving
                if (userRequests.head == null) {
                    userRequests.head = copy;
                } else {
                    RequestStatus last = userRequests.head;
                    while (last.next != null) {
                        last = last.next;
                    }
                    last.next = copy;
                }
            }
            current = current.next;
        }

        return userRequests;
    }
}
