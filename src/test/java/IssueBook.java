package rmit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Date;

/**
 *  Implement and test {Programme.addStudent } that respects the consideration given the assignment specification
 * NOTE: You are expected to verify that the constraints to borrow a new book from a library
 *
 * Each test criteria must be in an independent test method .
 *
 * Initialize the test object with "setting" method.
 */
public class IssueBook {

    // Declare private student variables
    private Student student1;
    private Student student2;
    private Student student3;

    // Declare private library card variables
    private LibraryCard libraryCard1;
    private LibraryCard libraryCard2;
    private LibraryCard libraryCard3;

    // Declare private book variables
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;
    private Book book6;

    @BeforeEach
    public void setting() {
        // Create sample students
        student1 = new Student("Parsley S", 9999);
        student2 = new Student("Sage C", 9998);
        student3 = new Student("Rosemary A", 9997);

        // Create sample books
        book1 = new Book(1, "Little Red Riding Hood", 1); // High demand book
        book2 = new Book(2, "Where The Wild Things Are", 0); // Low demand book
        book3 = new Book(3, "Three Little Pigs", 0);
        book4 = new Book(4, "Goldilocks and The Three Bears", 0);
        book5 = new Book(5, "The Rainbow Fish", 0);
        book6 = new Book(6, "The Giving Tree", 0);

        // Get the current date
        Date currentDate = new Date();

        // Calculate the expiry date to be one year from now
        long oneYearInMillis = 365L * 24 * 60 * 60 * 1000; // milliseconds in a year
        Date expiryDate = new Date(currentDate.getTime() + oneYearInMillis);

        // Calculate the expiry date to be yesterday
        Date expiredDate = new Date(currentDate.getTime() - 24 * 60 * 60 * 1000); // one day ago

        // Create sample library cards
        libraryCard1 = new LibraryCard(student1, currentDate, expiryDate, 1001);
        libraryCard2 = new LibraryCard(student2, currentDate, expiryDate, 1002);
        libraryCard3 = new LibraryCard(student3, currentDate, expiredDate, 1003);
    }


    /**
     * This method tests getting the number of books borrowed by the student on the library card
     */
    @Test
    public void getNumberOfBooksOnCard_True_IfThereAreBooksBorrowed() throws IllegalBookIssueException {
        // Add books to the library card
        libraryCard1.issueBook(book1);
        libraryCard1.issueBook(book2);
        libraryCard1.issueBook(book3);
        libraryCard1.issueBook(book4);

        // Checks the number of books on the library card is as expected, that is 4
        assertEquals(4, libraryCard1.getBooks().size());
        // Retrieves the first element (book) from the list by indexes
        assertEquals(book1, libraryCard1.getBooks().get(0));
        // Retrieves the second element (book2) from the list by indexes
        assertEquals(book2, libraryCard1.getBooks().get(1));
        // Prints the current number of books on the library card
        System.out.println("Current Number of Books on Card: " + libraryCard1.getBooks().size());
    }

    /**
     * This method tests getting the number of books borrowed by the student on the library card
     */
    @Test
    public void getNumberOfBooksBorrowedByStudent_True_IfNoBooksBorrowed() {
        // Check the number of books on the library card is as expected, that is 0.
        assertEquals(0, libraryCard1.getBooks().size());
        // Check that library card ID is correct or as expected
        assertEquals(1001, libraryCard1.getID());
        // Check that student number is correct or as expected
        assertEquals(9999, student1.getId());
        // Print the current number of books borrowed, name and number of the card holder.
        System.out.println("Current Number of Books on Card: " + libraryCard1.getBooks().size() +
                "\nCard Holder: " + student1.getName() +
                "\n" + "Student Number: " + student1.getId());
    }

    @Test
    public void getNumberOfBooksBorrowedByStudent_True_IfThereAreBooksBorrowed() throws IllegalBookIssueException {
        // Add books to the library card
        libraryCard2.issueBook(book1);
        libraryCard2.issueBook(book2);

        // Check the number of books on the library card is as expected, that is 2
        assertEquals(2, libraryCard2.getBooks().size());
        // Check that library card ID is correct or as expected
        assertEquals(1002, libraryCard2.getID());
        // Check that student number is correct or as expected
        assertEquals(9998, student2.getId());
        // Print the current number of books borrowed, name and number of the card holder.
        System.out.println("Current Number of Books on Card: " + libraryCard2.getBooks().size() +
                "\nCard Holder: " + student2.getName() +
                "\n" + "Student Number: " + student2.getId());
    }

    /**
     * This method tests the number of books borrowed should not be greater than 4
     */
    @Test
    public void maxFourBooks_True_IfNoMoreThanFourBooksBorrowed() throws IllegalBookIssueException {
        // Try to add 5 books to the library card
        libraryCard1.issueBook(book1);
        libraryCard1.issueBook(book2);
        libraryCard1.issueBook(book3);
        libraryCard1.issueBook(book4);
        libraryCard1.issueBook(book5);

        // Checks the number of books on the library card is as expected, that is 4 and not 5
        assertEquals(4, libraryCard1.getBooks().size());
        // Check that library card ID is correct or as expected
        assertEquals(1001, libraryCard1.getID());
        // Check that student number is correct or as expected
        assertEquals(9999, student1.getId());
        // Print the current number of books borrowed, name and number of the card holder.
        System.out.println("Max number of books borrowed reached, cannot borrow anymore. \nCurrent Number of Books on Card: " + libraryCard1.getBooks().size() +
                "\nCard Holder: " + student1.getName() +
                "\n" + "Student Number: " + student1.getId());
    }

    /**
     * This method tests that the library card is still valid
     */
    @Test
    public void isValid_True_IfBeforeExpiryDate() {
        // Check that the library card is valid and not expired
        assertTrue(libraryCard2.isCardValid());
        System.out.println("Library Card " + libraryCard2.getID() + " is valid.");
    }

    @Test
    public void isExpired_True_IfPassedExpiryDate() {
        // Check that the library card is expired and no longer valid
        assertFalse(libraryCard3.isCardValid());
        System.out.println("Library Card " + libraryCard3.getID() + " has expired.");
    }

    /**
     * This method tests that the book is available for borrowing
     */
    @Test
    public void bookAvailable_True_IfStatusIsTrue() {
        // Set the status of book5 as available (true)
        book5.setStatus(true);
        // Check that the book is available for borrowing
        assertEquals(true, book5.getStatus());
        System.out.println("The book, " + book5.getTitle() + " is available.");
    }

    @Test
    public void bookUnavailable_True_IfStatusIsFalse() {
        // Set the status of book6 as unavailable (false)
        book6.setStatus(false);
        // Check that the book is not available for borrowing
        assertEquals(false, book6.getStatus());
        System.out.println("The book," + book6.getTitle() + " is unavailable.");
    }

    /**
     * This method tests that the book should not be issued if there is pending fine associated with the library card
     */
    @Test
    public void failureDueToPendingFine_True_WhenCardHasAFine() throws IllegalBookIssueException {
        // Create a pending fine of $15.00 for libraryCard2
        libraryCard2.setFine(15.00);
        // Issue the book
        assertFalse(libraryCard2.issueBook(book5));
        // Check that the book was not issued due to the pending fine
        assertTrue(libraryCard2.getBooks().isEmpty());
        // Check the current number of books in the card, which should be 0
        assertEquals(0, libraryCard2.getBooks().size());
        System.out.println("The book, " + book5.getTitle() + " cannot be borrowed as there is a fine of $" + libraryCard2.getFine() + "in the card.");
    }

    /**
     * This method tests that if the same book is already issued on the library card then an exception is thrown.
     */

    @Test
    public void issueSameBook_True_IfBookAlreadyBorrowed() throws IllegalBookIssueException {
        // Issue a book
        libraryCard1.issueBook(book1);

        // Check the current number of books in the card, which should be 1
        assertEquals(1, libraryCard1.getBooks().size());

        // Throw exception if the same book is issued to the same library card again.
        assertThrows(IllegalBookIssueException.class,
                () -> libraryCard1.issueBook(book1),
                "This book is already borrowed on the card.");
    }

    @Test
    public void issueDuplicateBooks_True_IfBooksAlreadyBorrowed() throws IllegalBookIssueException {
        // Issue two different books
        libraryCard2.issueBook(book1);
        libraryCard2.issueBook(book2);

        // Check the current number of books in the card, which should be 2
        assertEquals(2, libraryCard2.getBooks().size());

        // Throw exception if the same books is issued to the same library card again.
        assertThrows(IllegalBookIssueException.class,
                () -> libraryCard2.issueBook(book1),
                "This book is already borrowed on the card.");

        assertThrows(IllegalBookIssueException.class,
                () -> libraryCard2.issueBook(book2),
                "Book already borrowed on card.");
    }

    /**
     * This method tests that if book to be borrowed is a high demand book then issue the book for 3 days.
     * For a low demand book, it can be issued for 15 days.
     */
    @Test
    public void issueHighDemandBook_True_IfBookIsDenotedByOne() throws IllegalBookIssueException {
        // Issue the high demand book
        libraryCard1.issueBook(book1);

        // Check that it is a high demand book (indicated by 1)
        assertEquals(1, book1.getDemand());

        // Check how many days the high demand book can be issued for (that is 3 days)
        assertEquals(3, book1.getDays());
        System.out.println("Book is a " +  book1.getDemand() + " (high demand) book and is issued for " + book1.getDays() + " days.");
    }

    @Test
    public void issueLowDemandBook_True_IfBookIsDenotedByZero() throws IllegalBookIssueException {
        // Issue the low demand book
        libraryCard2.issueBook(book2);

        // Check that it is a low demand book (indicated by 0)
        assertEquals(0, book2.getDemand());

        // Check how many days the low demand book can be issued for (that is 15 days)
        assertEquals(15, book2.getDays());
        System.out.println("Book is a " +  book2.getDemand() + " (low demand) book and is issued for " + book2.getDays() + " days.");
    }

}
