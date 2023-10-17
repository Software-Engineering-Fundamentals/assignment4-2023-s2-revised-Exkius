package rmit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Library Card associated with the Student 
 */
public class LibraryCard {
    /**
     * Card id 
     */
    private int ID;

    /**
     * Issue Date of the Card
     */
    private Date IssueDate;

    /**
     * Expiry Date of the Card
     */
    private Date ExpiryDate;

    /**
     * Number of books borrowed
     */
    private List<Book> borrowed = new ArrayList<Book>();

    /**
     * Fine associated with the card
     */
    private double fine;

    /**
     *  Details about the cardholder
     */
    private Student student;




    public LibraryCard(Student student, Date IssueDate, Date ExpiryDate, int ID) {
        this.student = student;
        this.IssueDate = IssueDate;
	    this.ExpiryDate = ExpiryDate;
	    this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }


    public Date getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(Date IssueDate) {
        this.IssueDate = IssueDate;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(Date ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    
    public List<Book> getBooks() {
        return borrowed;
    }


    /*
     * Issue a new book
     * @param Book: book to borrow 
     * @return true if the book is successfully borrowed, false otherwise
     */

    public boolean issueBook(Book book) throws IllegalBookIssueException {
            if (getNumberOfBooksBorrowed() >= 4 ||  // If number of books exceeds 4
                    !isBookAvailable(book) ||  // Or book is not available
                    !isCardValid() ||  // Or if card is not valid (has expired)
                    !book.getStatus() ||  // Or if book's status is not available
                    !isStudentMatch(student.getId()) ||  // Or if student ID does not match the library card's student ID
                    getFine() > 0) {  // Or if there is a pending fine
                return false;  // Then return false
            }

            setDueDate(book);  // Otherwise if all the above conditions are met, set a due date for the book

            // If the card already contains the same book borrowed on it, then throw an exception
            if (borrowed.contains(book)) {
                throw new IllegalBookIssueException("This book is already borrowed on the card.");
            }

            System.out.println("Book issued successfully.");
            borrowed.add(book);  // Issue the book to the student
            return true;  // And return true
    }

    // This method returns the current number of books borrowed on the card
    public int getNumberOfBooksBorrowed() {
    return borrowed.size();
    }

    // This method comparing the current date with the card's expiry date to check if the card is still valid or not
    public boolean isCardValid() {
        Date currentDate = new Date();
        return currentDate.before(ExpiryDate);
    }

    // This method returns the books' status to check for its  availability
    private boolean isBookAvailable(Book book) {
        return book.getStatus();
    }

    // This method sets the issued book's due date depending on its demand,
    // if the book is in high demand (1) then it can only be borrowed for 3 days
    // if the book is in low demand (0) then it can be borrowed for 15 days
    private void setDueDate(Book book) {
        int daysToIssue = (book.getDemand() == 1) ? 3 : 15;
        book.setDays(daysToIssue);
    }

    // This method checks if the student number parallels with the student ID on the library card
    public boolean isStudentMatch(int studentNumber) {
        return this.student.getId() == studentNumber;
    }
}
