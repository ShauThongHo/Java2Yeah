import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * borrowDataFile: handles saving and loading the BorrowedBook records
 * using a plain text CSV file (borrows_data.csv).
 *
 * Each line in the file stores:
 *   Title, Author, ISBN, Category, BorrowerName, BorrowDate, BorrowDays, DueDate
 *
 * We use Scanner (to read) and PrintWriter (to write).
 * No databases are used - only a simple text file.
 */
public class borrowDataFile {

    // the file where the borrow records are stored
    private static final String FILE_NAME = "borrows_data.csv";

    // ------------------------------------------------------------------
    // loadBorrows(): reads every borrow record from the CSV file and
    // returns them as a BorrowedBook[] array. If the file does not exist
    // yet, it is created as an empty file (no default borrows).
    // ------------------------------------------------------------------
    public static BorrowedBook[] loadBorrows() {
        BorrowedBook[] borrows = new BorrowedBook[0];   // start with an empty array

        try {
            File file = new File(FILE_NAME);

            // if the file does not exist, just create it empty
            if (!file.exists()) {
                file.createNewFile();
                return borrows;
            }

            // STEP 1: first pass - count how many borrow records are in the file.
            // We need the count first because arrays have a fixed size.
            int count = 0;
            Scanner counter = new Scanner(file);
            while (counter.hasNextLine()) {
                if (!counter.nextLine().trim().isEmpty()) {
                    count++;
                }
            }
            counter.close();

            // STEP 2: create an array that is exactly the right size
            borrows = new BorrowedBook[count];

            // STEP 3: second pass - read each line and turn it into a BorrowedBook
            Scanner reader = new Scanner(file);
            int index = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();

                // skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // each line is: Title,Author,ISBN,Category,BorrowerName,BorrowDate,BorrowDays,DueDate
                String[] parts = line.split(",");

                // only use the line if it really has 8 parts
                if (parts.length == 8) {
                    String title = parts[0];
                    String author = parts[1];
                    String isbn = parts[2];
                    String category = parts[3];
                    String borrowerName = parts[4];
                    String borrowDate = parts[5];
                    int borrowDays = Integer.parseInt(parts[6]);
                    String dueDate = parts[7];

                    borrows[index] = new BorrowedBook(title, author, isbn, category, 1,
                            borrowerName, borrowDate, borrowDays, dueDate);
                    index++;
                }
            }
            reader.close();

            // some lines may have been skipped (malformed) - trim the array
            // so there are no null holes left at the end
            if (index < borrows.length) {
                BorrowedBook[] trimmed = new BorrowedBook[index];
                System.arraycopy(borrows, 0, trimmed, 0, index);
                borrows = trimmed;
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: a borrow duration in the file is not a number: " + e.getMessage());
        }

        return borrows;
    }

    // ------------------------------------------------------------------
    // saveBorrows(): writes every borrow record in the BorrowedBook[]
    // array into the CSV file.
    // ------------------------------------------------------------------
    public static void saveBorrows(BorrowedBook[] borrows) {
        PrintWriter writer = null;

        try {
            // PrintWriter can create the file if it does not exist
            writer = new PrintWriter(FILE_NAME);

            // write one line per borrow record
            for (int i = 0; i < borrows.length; i++) {
                BorrowedBook b = borrows[i];
                writer.println(b.getTitle() + ","
                        + b.getAuthor() + ","
                        + b.getIsbn() + ","
                        + b.getCategory() + ","
                        + b.getBorrowerName() + ","
                        + b.getBorrowDate() + ","
                        + b.getBorrowDays() + ","
                        + b.getDueDate());
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
