/**
 * BorrowedBook: represents a single book currently borrowed by a member.
 *
 * It inherits all the Book fields (title, author, isbn, category, quantity)
 * and adds the borrower information (name, borrow date, borrow duration, due date).
 */
class BorrowedBook extends Book {

    private final String borrowerName;
    private final String borrowDate;
    private final int borrowDays;
    private final String dueDate;

    public BorrowedBook(String title, String author, String isbn, String category, int quantity,
                        String borrowerName, String borrowDate, int borrowDays, String dueDate) {
        super(title, author, isbn, category, quantity);
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.borrowDays = borrowDays;
        this.dueDate = dueDate;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public int getBorrowDays() {
        return borrowDays;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo()
                + "\nBorrower: " + borrowerName
                + "\nBorrowed on: " + borrowDate
                + "\nBorrow duration: " + borrowDays + " days"
                + "\nDue date: " + dueDate;
    }
}
