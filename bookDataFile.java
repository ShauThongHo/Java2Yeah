import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * bookDataFile: handles saving and loading the Book inventory
 * using a plain text CSV file (books_data.csv).
 *
 * Each line in the file stores:
 *   Title, Author, ISBN, Category, Quantity
 *
 * We use Scanner (to read) and PrintWriter (to write).
 * No databases are used - only a simple text file.
 */
public class bookDataFile {

    // the file where the book inventory is stored
    private static final String FILE_NAME = "books_data.csv";

    // ------------------------------------------------------------------
    // loadBooks(): reads every book from the CSV file and returns them
    // as a Book[] array. If the file does not exist yet, it is created
    // and filled with a few default books.
    // ------------------------------------------------------------------
    public static Book[] loadBooks() {
        Book[] books = new Book[0];   // start with an empty array

        try {
            File file = new File(FILE_NAME);

            // STEP 1: if the file does not exist, create it with default books
            if (!file.exists()) {
                file.createNewFile();

                Book[] defaultBooks = {
                    new Book("Harry Potter Philosopher Stone", "J.K.Rowling", "9780747532699", "Fantasy", 1),
                    new Book("Harry Potter Chamber of Secrets", "J.K.Rowling", "0747538492", "Fantasy", 1),
                    new Book("Harry Potter Goblet of Fire", "J.K.Rowling", "0747550794", "Fantasy", 1)
                };

                saveBooks(defaultBooks);   // write the defaults into the file
                return defaultBooks;       // and return them to the program
            }

            // STEP 2: first pass - count how many books are in the file.
            // We need the count first because arrays have a fixed size.
            int count = 0;
            Scanner counter = new Scanner(file);
            while (counter.hasNextLine()) {
                if (!counter.nextLine().trim().isEmpty()) {
                    count++;
                }
            }
            counter.close();

            // STEP 3: create an array that is exactly the right size
            books = new Book[count];

            // STEP 4: second pass - read each line and turn it into a Book
            Scanner reader = new Scanner(file);
            int index = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();

                // skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // each line is: Title,Author,ISBN,Category,Quantity
                String[] parts = line.split(",");

                // only use the line if it really has 5 parts
                if (parts.length == 5) {
                    String title = parts[0];
                    String author = parts[1];
                    String isbn = parts[2];
                    String category = parts[3];
                    int quantity = Integer.parseInt(parts[4]);

                    books[index] = new Book(title, author, isbn, category, quantity);
                    index++;
                }
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: a quantity in the file is not a number: " + e.getMessage());
        }

        return books;
    }

    // ------------------------------------------------------------------
    // saveBooks(): writes every book in the Book[] array into the CSV file.
    // ------------------------------------------------------------------
    public static void saveBooks(Book[] books) {
        PrintWriter writer = null;

        try {
            // PrintWriter can create the file if it does not exist
            writer = new PrintWriter(FILE_NAME);

            // write one line per book
            for (int i = 0; i < books.length; i++) {
                Book b = books[i];
                writer.println(b.getTitle() + ","
                        + b.getAuthor() + ","
                        + b.getIsbn() + ","
                        + b.getCategory() + ","
                        + b.getQuantity());
            }

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        } finally {
            // always close the file, even if an error happened
            if (writer != null) {
                writer.close();
            }
        }
    }
}