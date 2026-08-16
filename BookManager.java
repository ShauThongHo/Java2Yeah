import java.util.ArrayList;
import java.time.LocalDate;

public class BookManager {
    public ArrayList<Book> bookList;
    public ArrayList<BorrowedBook> borrowedBooks;

    public BookManager() {
        this.bookList = bookDataFile.loadBooks();
        this.borrowedBooks = borrowDataFile.loadBorrows();
    }

    /**
     * Normalizes an ISBN typed by the user so that formatted inputs such as
     * "978-0-7475-3269-9" or "978 0 7475 3269 9" are matched against the plain
     * digit strings stored in the CSV files. A trailing 'X' check digit of an
     * ISBN-10 is preserved (e.g. "0-306-40615-2" -> "0306406152").
     */
    public static String sanitizeIsbn(String raw) {
        if (raw == null) {
            return "";
        }
        String cleaned = raw.trim().replaceAll("[\\s-]", "");
        if (cleaned.matches("\\d{9}[Xx]")) {
            return cleaned.toUpperCase();
        }
        return cleaned.replaceAll("[^0-9]", "");
    }

    /** A valid ISBN is a standard 10 or 13 digit number after sanitizing. */
    public static boolean isValidIsbn(String isbn) {
        String cleaned = sanitizeIsbn(isbn);
        return cleaned.length() == 10 || cleaned.length() == 13;
    }

    public String addOrUpdateBook(String title, String author, String isbn, String category) {
        isbn = sanitizeIsbn(isbn);
        for (Book b : bookList) {
            if (b.getIsbn().equals(isbn)) {
                b.setQuantity(b.getQuantity() + 1);
                bookDataFile.saveBooks(bookList);
                return "Book already exists! Updated stock for ISBN: " + isbn + " (Total Qty: " + b.getQuantity() + ")";
            }
        }

        bookList.add(new Book(title, author, isbn, category, 1));
        bookDataFile.saveBooks(bookList);
        return "Successfully donated and added: " + title;
    }

    public String borrowBook(String isbn, String borrowerName, int borrowDays) throws BorrowException {
        isbn = sanitizeIsbn(isbn);
        Book targetBook = null;
        for (Book b : bookList) {
            if (b.getIsbn().equals(isbn)) {
                targetBook = b;
                break;
            }
        }

        if (targetBook == null) {
            throw new BorrowException("Book not found for ISBN: " + isbn);
        }
        if (borrowDays < 1 || borrowDays > 7) {
            throw new BorrowException("Borrow duration must be between 1 and 7 days!");
        }
        if (targetBook.getQuantity() <= 0) {
            throw new BorrowException("Sorry, \"" + targetBook.getTitle() + "\" is out of stock right now.");
        }

        String borrowDate = LocalDate.now().toString();
        String dueDate = LocalDate.now().plusDays(borrowDays).toString();

        borrowedBooks.add(new BorrowedBook(
                targetBook.getTitle(), targetBook.getAuthor(), targetBook.getIsbn(),
                targetBook.getCategory(), 1, borrowerName, borrowDate, borrowDays, dueDate
        ));

        targetBook.setQuantity(targetBook.getQuantity() - 1);

        bookDataFile.saveBooks(bookList);
        borrowDataFile.saveBorrows(borrowedBooks);

        return "Successfully borrowed: \"" + targetBook.getTitle() + "\" by " + borrowerName;
    }

    /**
     * Processes a book return. Only an ISBN with an active borrow record can be
     * returned; otherwise the request is rejected and the inventory is left
     * untouched. On success the stock is incremented by one, the matching borrow
     * record is removed and both CSV files are re-saved.
     */
    public String returnBook(String isbn) throws BorrowException {
        isbn = sanitizeIsbn(isbn);
        if (isbn.isEmpty()) {
            throw new BorrowException("Error: ISBN field cannot be empty!");
        }

        BorrowedBook matchedBorrow = null;
        for (BorrowedBook bb : borrowedBooks) {
            if (bb.getIsbn().equals(isbn)) {
                matchedBorrow = bb;
                break;
            }
        }

        if (matchedBorrow == null) {
            throw new BorrowException("Error: No active borrow record found for ISBN " + isbn + ". This book is not currently on loan.");
        }

        Book targetBook = null;
        for (Book b : bookList) {
            if (b.getIsbn().equals(isbn)) {
                targetBook = b;
                break;
            }
        }

        if (targetBook == null) {
            throw new BorrowException("Error: Book with ISBN " + isbn + " is not registered in the system.");
        }

        targetBook.setQuantity(targetBook.getQuantity() + 1);
        borrowedBooks.remove(matchedBorrow);

        bookDataFile.saveBooks(bookList);
        borrowDataFile.saveBorrows(borrowedBooks);

        return "Success: \"" + targetBook.getTitle() + "\" returned by " + matchedBorrow.getBorrowerName()
                + ". Inventory quantity increased by 1.";
    }
}