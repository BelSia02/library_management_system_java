package booklinkedlist;

public class RequestQueue {
	private BorrowRequest front;// first request in queue
    private BorrowRequest rear;// last request in queue

    public RequestQueue() {
        front = null;
        rear = null;
    }

    //load request from text file
    public void loadFromFile() {
        RequestQueue loaded = FileHandler.loadRequestsFromFile();
        this.front = loaded.getHead();
        this.rear = loaded.rear;
    }
    
    //save to text file
    public void saveToFile() {
        FileHandler.saveRequestsToFile(this);
    }
    
    // Enqueue: add new borrow request at the end
    public void enqueue(BorrowRequest request) {
        if (rear == null) {   // empty queue
            front = rear = request;
            return;
        }

        rear.next = request;
        rear = request;
    }
    
    
    public BorrowRequest getHead() {
        return front;
    }

    // Dequeue: remove the front request and return it
    public BorrowRequest dequeue() {	
        if (front == null) return null; // empty queue

        BorrowRequest temp = front;
        front = front.next;

        // If queue becomes empty
        if (front == null) {
            rear = null;
        }

        temp.next = null; // clean up pointer
        saveToFile();
        return temp;
    }

    // Display all pending requests
    public void displayQueue() {
        if (front == null) {
            System.out.println("No pending borrow requests.");
            return;
        }

        BorrowRequest current = front;
        while (current != null) {
            current.display();
            current = current.next;
        }
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Count pending requests
    public int countRequests() {
        int count = 0;
        BorrowRequest current = front;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
    
    //remove specific request
    public void removeRequest(String requestId) {
        if (front == null) return;

        // Special case: if it's the first request
        if (front.getRequestId().equals(requestId)) {
            dequeue();  // This will auto-save
            return;
        }

        // Search for the request in the queue
        BorrowRequest current = front;
        while (current != null && current.next != null) {
            if (current.next.getRequestId().equals(requestId)) {
                current.next = current.next.next;
                
                // Update rear if we removed the last element
                if (current.next == null) {
                    rear = current;
                }
                
                saveToFile();  //Auto-save after removing
                return;
            }
            current = current.next;
        }
    }
    
}
