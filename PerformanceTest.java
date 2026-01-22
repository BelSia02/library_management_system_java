package booklinkedlist;

public class PerformanceTest {
	public static void main(String[] args) {

        // Test Add and Remove for BookLinkedList
        BookLinkedList bookList = new BookLinkedList();

        // Test 100 books
        long start100 = System.nanoTime();
        for (int i = 1; i <= 100; i++) {
            bookList.addBook(new Book("B" + i, "Title" + i, "Author" + i, true));
        }
        long end100 = System.nanoTime();
        System.out.println("Time to add 100 books: " + (end100 - start100)/1e6 + " ms");

        // Remove 100 books
        long removeStart100 = System.nanoTime();
        for (int i = 1; i <= 100; i++) {
            bookList.deleteBookById("B" + i);
        }
        long removeEnd100 = System.nanoTime();
        System.out.println("Time to remove 100 books: " + (removeEnd100 - removeStart100)/1e6 + " ms");

        // Test 150 books
        long start150 = System.nanoTime();
        for (int i = 1; i <= 150; i++) {
            bookList.addBook(new Book("B" + i, "Title" + i, "Author" + i, true));
        }
        long end150 = System.nanoTime();
        System.out.println("Time to add 150 books: " + (end150 - start150)/1e6 + " ms");

        // Remove 150 books
        long removeStart150 = System.nanoTime();
        for (int i = 1; i <= 150; i++) {
            bookList.deleteBookById("B" + i);
        }
        long removeEnd150 = System.nanoTime();
        System.out.println("Time to remove 150 books: " + (removeEnd150 - removeStart150)/1e6 + " ms");
    }
}
