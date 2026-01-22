package booklinkedlist;

import java.io.*;

public class FileHandler {
    private static final String BOOKS_FILE = "books.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String REQUESTS_FILE = "requests.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String REQUEST_STATUS_FILE = "request_status.txt";

 // book file operations
 public static void saveBooksToFile(BookLinkedList bookList) {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
         Book current = bookList.getHead();
         while (current != null) {
             // Format: ID|Title|Author|Available
             writer.write(current.getId() + "|" +
                          current.getTitle() + "|" +
                          current.getAuthor() + "|" +
                          current.isAvailable());
             writer.newLine();
             current = current.next;
         }
     } catch (IOException e) {
         System.err.println("Error saving books: " + e.getMessage());
     }
 }

 public static BookLinkedList loadBooksFromFile() {
     BookLinkedList bookList = new BookLinkedList();
     File file = new File(BOOKS_FILE);

     if (!file.exists()) {
         return bookList;  // empty list
     }

     try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
         String line;
         Book last = null;

         while ((line = reader.readLine()) != null) {
             String[] parts = line.split("\\|");

             if (parts.length == 4) {
                 String id = parts[0];
                 String title = parts[1];
                 String author = parts[2];
                 boolean available = Boolean.parseBoolean(parts[3]);

                 Book newBook = new Book(id, title, author, available);

                 // manually append WITHOUT saving
                 if (bookList.getHead() == null) {
                     bookList.setHead(newBook);
                     last = newBook;
                 } else {
                     last.next = newBook;
                     last = newBook;
                 }
             }
         }
     } catch (IOException e) {
         System.err.println("Error loading books: " + e.getMessage());
     }

     return bookList;
 }


    //user file  operations
    public static void saveUsersToFile(UserLinkedList userList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            User current = userList.getHead();
            while (current != null) {
                // Format: ID|Name|Email|Role
                writer.write(current.getId() + "|" + 
                           current.getName() + "|" + 
                           current.getEmail() + "|" + 
                           current.getRole());
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static UserLinkedList loadUsersFromFile() {
        UserLinkedList userList = new UserLinkedList();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            // Create default users if file doesn't exist
            createDefaultUsers(userList);
            saveUsersToFile(userList);
            return userList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String role = parts[3];
                    userList.addUser(new User(id, name, email, role));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        
        return userList;
    }

    private static void createDefaultUsers(UserLinkedList userList) {
        // Default librarians
        userList.addUser(new User("L001", "Alice Johnson", "alice@library.com", "librarian"));
        userList.addUser(new User("L002", "Bob Smith", "bob@library.com", "librarian"));
        
        // Default students
        userList.addUser(new User("S001", "Charlie Brown", "charlie@student.com", "student"));
        userList.addUser(new User("S002", "Diana Prince", "diana@student.com", "student"));
        userList.addUser(new User("S003", "Eve Adams", "eve@student.com", "student"));
    }

    //request queue operation
    public static void saveRequestsToFile(RequestQueue requestQueue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REQUESTS_FILE))) {
            BorrowRequest current = requestQueue.getHead();
            while (current != null) {
                // Format: RequestID|UserID|BookID
                writer.write(current.getRequestId() + "|" + 
                           current.getUserId() + "|" + 
                           current.getBookId());
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.err.println("Error saving requests: " + e.getMessage());
        }
    }

    public static RequestQueue loadRequestsFromFile() {
        RequestQueue requestQueue = new RequestQueue();
        File file = new File(REQUESTS_FILE);
        
        if (!file.exists()) {
            return requestQueue; // Return empty queue if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String requestId = parts[0];
                    String userId = parts[1];
                    String bookId = parts[2];
                    requestQueue.enqueue(new BorrowRequest(requestId, userId, bookId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading requests: " + e.getMessage());
        }
        
        return requestQueue;
    }

    // transaction history operations
    public static void saveTransactionsToFile(TransactionLinkedList transactionList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            Transaction current = transactionList.getHead();
            while (current != null) {
                // Format: TransactionID|UserID|BookID|Action|DateTime
                writer.write(current.getTransactionId() + "|" + 
                           current.getUserId() + "|" + 
                           current.getBookId() + "|" + 
                           current.getType() + "|" + 
                           current.getDate());
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    public static TransactionLinkedList loadTransactionsFromFile() {
    	TransactionLinkedList history = new TransactionLinkedList();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return history; // Return empty history if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String transactionId = parts[0];
                    String userId = parts[1];
                    String bookId = parts[2];
                    String action = parts[3];
                    String dateTime = parts[4];
                    history.addTransaction(new Transaction(transactionId, userId, bookId, action, dateTime));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        
        return history;
    }
    
    //request status operations
    public static void saveRequestStatusToFile(RequestStatusList statusList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REQUEST_STATUS_FILE))) {
            RequestStatus current = statusList.getHead();
            while (current != null) {
                // Format: RequestID|UserID|BookID|Status|Date
                writer.write(current.getRequestId() + "|" + 
                           current.getUserId() + "|" + 
                           current.getBookId() + "|" + 
                           current.getStatus() + "|" + 
                           current.getDate());
                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.err.println("Error saving request status: " + e.getMessage());
        }
    }

    public static RequestStatusList loadRequestStatusFromFile() {
        RequestStatusList statusList = new RequestStatusList();
        File file = new File(REQUEST_STATUS_FILE);
        
        if (!file.exists()) {
            return statusList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(REQUEST_STATUS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String requestId = parts[0];
                    String userId = parts[1];
                    String bookId = parts[2];
                    String status = parts[3];
                    String date = parts[4];
                    
                    RequestStatus rs = new RequestStatus(requestId, userId, bookId, status, date);
                    
                    // Add without auto-save during load
                    if (statusList.getHead() == null) {
                        statusList.setHead(rs);
                    } else {
                        RequestStatus last = statusList.getHead();
                        while (last.next != null) {
                            last = last.next;
                        }
                        last.next = rs;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading request status: " + e.getMessage());
        }
        
        return statusList;
    }
}