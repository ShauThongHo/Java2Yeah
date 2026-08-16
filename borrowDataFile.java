import java.io.*;
import java.util.ArrayList;

public class borrowDataFile {
    private static final String FILE_NAME = "borrows_data.csv";

    public static ArrayList<borrowedBook> loadBorrows() {
        ArrayList<borrowedBook> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String isbn = parts[2].trim();
                    String category = parts[3].trim();
                    int quantity = Integer.parseInt(parts[4].trim());
                    String borrowerName = parts[5].trim();
                    String borrowDate = parts[6].trim();
                    int borrowDays = Integer.parseInt(parts[7].trim());
                    String dueDate = parts[8].trim();

                    list.add(new borrowedBook(title, author, isbn, category, quantity, borrowerName, borrowDate, borrowDays, dueDate));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading borrows data: " + e.getMessage());
        }

        return list;
    }

    public static void saveBorrows(ArrayList<borrowedBook> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (borrowedBook b : list) {
                writer.println(b.getTitle() + ","
                        + b.getAuthor() + ","
                        + b.getIsbn() + ","
                        + b.getCategory() + ","
                        + b.getQuantity() + ","
                        + b.getBorrowerName() + ","
                        + b.getBorrowDate() + ","
                        + b.getBorrowDays() + ","
                        + b.getDueDate());
            }
        } catch (IOException e) {
            System.err.println("Error saving borrows data: " + e.getMessage());
        }
    }
}
