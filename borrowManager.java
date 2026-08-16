import java.time.LocalDate;
import java.util.ArrayList;

public class borrowManager {
    public ArrayList <borrowedBook> borrowedBooks;

    public borrowManager() {
        this.borrowedBooks = borrowDataFile.loadBorrows();
    }

    public String borrowBook(String isbn, String borrowerName, int borrowDays, BookManager bookManager) throws borrowException {
        BookManager.sanitizeIsbn(isbn);
        int index = bookManager.findBookIndex(isbn);

        if(index == -1) {
            throw new borrowException("Book not found for ISBN: " + isbn);
        }

        if(borrowDays < 1 || borrowDays > 7) {
            throw new borrowException("Borrow duration must be between 1 and 7 days!");
        }

        Book targetBook = bookManager.bookList.get(index);
        if(targetBook.getQuantity() <= 0) {
            throw new borrowException("Sorry, \""+ targetBook.getTitle() + "\" is out of stock right now.");
        }

        String borrowDate = LocalDate.now().toString();
        String dueDate = LocalDate.now().plusDays(borrowDays).toString();

        borrowedBooks.add(new borrowedBook(
            targetBook.getTitle(), targetBook.getAuthor(), targetBook.getIsbn(), targetBook.getCategory(), 1, borrowerName, borrowDate, borrowDays, dueDate
        ));

        targetBook.setQuantity(targetBook.getQuantity() -1);

        bookDataFile.saveBooks(bookManager.bookList);
        borrowDataFile.saveBorrows(borrowedBooks);

        return "Sucessfully borrowed: \"" + targetBook.getTitle() + "\" by " + borrowerName;
    }

    public String returnBook(String isbn, BookManager bookManager) throws borrowException {
        BookManager.sanitizeIsbn(isbn);
        if(isbn.isEmpty()) {
            throw new borrowException("Error: ISBN field cannot be empty!");
        }

        borrowedBook matchedBorrow = null;
        for(borrowedBook bb : borrowedBooks) {
            if(bb.getIsbn().equals(isbn)) {
                matchedBorrow = bb;
                break;
            }
        }

        if(matchedBorrow == null) {
            throw new borrowException("Error: No active borrow record found for ISBN " + isbn + ". This book is not currently on loan.");
        }

        int index = bookManager.findBookIndex(isbn);
        if(index == -1) {
            throw new borrowException("Error: Book with ISBN \" + isbn + \" is not registered in the system.");
        }

        Book targetBook = bookManager.bookList.get(index);
        targetBook.setQuantity(targetBook.getQuantity() +1);
        borrowedBooks.remove(matchedBorrow);

        bookDataFile.saveBooks(bookManager.bookList);
        borrowDataFile.saveBorrows(borrowedBooks);

        return "Success: \"" + targetBook.getTitle() + "\" returned by " + matchedBorrow.getBorrowerName() + ". Inventory quantity incraesed by 1.";
    }
}
