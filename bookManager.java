import java.time.LocalDate;

/**
 * BookManager: holds all the Book data and all the BorrowedBook records.
 *
 * It uses plain arrays (Book[] and BorrowedBook[]) because ArrayList
 * is not allowed. Since arrays cannot grow by themselves, we copy the
 * whole array into a new, bigger array whenever we need to add an item.
 */
class BookManager {

    // all books in the library (array is always exactly the right size)
    public Book[] bookList;

    // all current borrow records (array is always exactly the right size)
    public BorrowedBook[] borrowedBooks;

    // ------------------------------------------------------------------
    // constructor: load the books and the borrows from the CSV files
    // ------------------------------------------------------------------
    public BookManager() {
        bookList = bookDataFile.loadBooks();
        borrowedBooks = borrowDataFile.loadBorrows();
    }

    // helper: copy a Book[] into a new array that is one bigger
    private Book[] growBookArray(Book[] oldArray) {
        Book[] newArray = new Book[oldArray.length + 1];
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }

    // helper: copy a BorrowedBook[] into a new array that is one bigger
    private BorrowedBook[] growBorrowArray(BorrowedBook[] oldArray) {
        BorrowedBook[] newArray = new BorrowedBook[oldArray.length + 1];
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }

    // ------------------------------------------------------------------
    // addOrUpdateBook(): used by the Donate feature.
    // If the ISBN already exists, just increase the quantity.
    // Otherwise add a brand-new Book to the array.
    // ------------------------------------------------------------------
    public String addOrUpdateBook(String title, String author, String isbn, String category) {
        // search for an existing book with the same ISBN
        for (int i = 0; i < bookList.length; i++) {
            if (bookList[i].getIsbn().equals(isbn)) {
                bookList[i].setQuantity(bookList[i].getQuantity() + 1);
                return "Book already exists! Updated stock for ISBN: " + isbn
                        + " (Total Qty: " + bookList[i].getQuantity() + ")";
            }
        }

        // not found, so add a new book (grow the array first)
        bookList = growBookArray(bookList);
        bookList[bookList.length - 1] = new Book(title, author, isbn, category, 1);
        return "Successfully donated and added: " + title;
    }

    // ------------------------------------------------------------------
    // findBookIndex(): search the array by ISBN.
    // Returns the index of the book, or -1 if it is not found.
    // ------------------------------------------------------------------
    public int findBookIndex(String isbn) {
        for (int i = 0; i < bookList.length; i++) {
            if (bookList[i].getIsbn().equals(isbn)) {
                return i;
            }
        }
        return -1;
    }

    // ------------------------------------------------------------------
    // borrowBook(): the core of the Borrow feature.
    //   1. Find the book by ISBN.
    //   2. If it does not exist  -> throw BorrowException.
    //   3. If the borrow days are not between 1 and 7 -> throw BorrowException.
    //   4. If there is no stock  -> throw BorrowException.
    //   5. Otherwise: record the borrow and reduce the stock by 1.
    // ------------------------------------------------------------------
    public String borrowBook(String isbn, String borrowerName, int borrowDays) throws BorrowException {
        int index = findBookIndex(isbn);

        // the book was not found
        if (index == -1) {
            throw new BorrowException("Book not found for ISBN: " + isbn);
        }

        // the duration must be between 1 and 7 days
        if (borrowDays < 1 || borrowDays > 7) {
            throw new BorrowException("Borrow duration must be between 1 and 7 days!");
        }

        // the book exists but has no copies left
        if (bookList[index].getQuantity() <= 0) {
            throw new BorrowException("Sorry, \"" + bookList[index].getTitle()
                    + "\" is out of stock right now.");
        }

        // record the borrow as a BorrowedBook (inherits from Book)
        String borrowDate = LocalDate.now().toString();                     // today, e.g. 2026-08-15
        String dueDate = LocalDate.now().plusDays(borrowDays).toString();   // e.g. 2026-08-22
        borrowedBooks = growBorrowArray(borrowedBooks);
        borrowedBooks[borrowedBooks.length - 1] = new BorrowedBook(
                bookList[index].getTitle(),
                bookList[index].getAuthor(),
                bookList[index].getIsbn(),
                bookList[index].getCategory(),
                1,                          // the borrowed copy itself
                borrowerName,
                borrowDate,
                borrowDays,
                dueDate);

        // reduce the available stock by one
        bookList[index].setQuantity(bookList[index].getQuantity() - 1);

        return "Successfully borrowed: \"" + bookList[index].getTitle()
                + "\" by " + borrowerName
                + " for " + borrowDays + " days"
                + " (Due: " + dueDate
                + ", Remaining stock: " + bookList[index].getQuantity() + ")";
    }

    // getter for the borrow view so it can show the borrow history list
    public BorrowedBook getBorrowedBook(int i) {
        return borrowedBooks[i];
    }

    // ------------------------------------------------------------------
    // removeBorrowedBook(): removes the first borrow record that matches
    // the given ISBN (used when a book is returned). Returns true if a
    // record was removed, false otherwise.
    // ------------------------------------------------------------------
    public boolean removeBorrowedBook(String isbn) {
        int found = -1;
        for (int i = 0; i < borrowedBooks.length; i++) {
            if (borrowedBooks[i].getIsbn().equals(isbn)) {
                found = i;
                break;
            }
        }

        if (found == -1) {
            return false;
        }

        BorrowedBook[] newArray = new BorrowedBook[borrowedBooks.length - 1];
        for (int i = 0, j = 0; i < borrowedBooks.length; i++) {
            if (i != found) {
                newArray[j] = borrowedBooks[i];
                j++;
            }
        }
        borrowedBooks = newArray;
        return true;
    }
}