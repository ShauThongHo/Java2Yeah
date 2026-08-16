import java.util.ArrayList;
import java.time.LocalDate;

public class BookManager {
    public ArrayList<Book> bookList;
    public ArrayList<BorrowedBook> borrowedBooks;

    public BookManager() {
        this.bookList = bookDataFile.loadBooks();
        this.borrowedBooks = borrowDataFile.loadBorrows();
    }

    public String addOrUpdateBook(String title, String author, String isbn, String category) {
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
}