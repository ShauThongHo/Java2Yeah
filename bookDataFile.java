import java.io.*;
import java.util.ArrayList;

public class bookDataFile {
    private static final String FILE_NAME = "books_data.csv";

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(FILE_NAME);

        try {
            if (!file.exists()) {
                file.createNewFile();
                books.add(new Book("Harry Potter Philosopher Stone", "J.K.Rowling", "9780747532699", "Fantasy", 1));
                books.add(new Book("Harry Potter Chamber of Secrets", "J.K.Rowling", "0747538492", "Fantasy", 1));
                books.add(new Book("Harry Potter Goblet of Fire", "J.K.Rowling", "0747550794", "Fantasy", 1));
                saveBooks(books);
                return books;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String isbn = parts[2].trim();
                    String category = parts[3].trim();
                    int quantity = Integer.parseInt(parts[4].trim());
                    books.add(new Book(title, author, isbn, category, quantity));
                }
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading book data: " + e.getMessage());
        }
        return books;
    }

    public static void saveBooks(ArrayList<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                writer.write(b.getTitle() + "," + b.getAuthor() + "," + b.getIsbn() + "," + b.getCategory() + "," + b.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving book data: " + e.getMessage());
        }
    }
}