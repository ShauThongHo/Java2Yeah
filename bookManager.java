import java.util.ArrayList;

class BookManager {
    public ArrayList<Book> bookList = new ArrayList<>();

    public String addOrUpdateBook(String title, String author, String isbn, String category) {
        for(Book b : bookList) {
            if(b.getIsbn().equals(isbn)) {
                b.setQuantity(b.getQuantity() + 1);
                return "Book already exists! Updated stock for ISBN: " + isbn + "(Total Qty: " + b.getQuantity() + ")";
            }
        }

        bookList.add(new Book(title, author, isbn, category, 1));
        return "Successfully donated and added: " + title;
    }
}