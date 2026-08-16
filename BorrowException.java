/**
 * BorrowException: a custom exception thrown by BookManager.borrowBook()
 * when a book cannot be borrowed (not found, no stock, or invalid duration).
 */
class BorrowException extends Exception {

    public BorrowException(String message) {
        super(message);
    }
}
