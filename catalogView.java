import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

public class catalogView {
    public static ScrollPane createView(bookManager bookManager) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        //title text
        Text title = new Text("Book Catelog & Inventory");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        //show each book card
        VBox catalogBox = new VBox(20);
        catalogBox.setAlignment(Pos.TOP_CENTER);
        catalogBox.setPadding(new Insets(20));
        catalogBox.getChildren().addAll(title);

        //display catalog content
        if(bookManager.bookList.size() == 0) {
            Text emptyText = new Text("No books available in the catalog.");
            emptyText.setFill(Color.LIGHTGRAY);
            emptyText.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
            catalogBox.getChildren().addAll(emptyText);
        } else {
            for(Book b : bookManager.bookList) {
                //create bookcard
                HBox bookCard = new HBox(20);
                bookCard.setAlignment(Pos.CENTER_LEFT);
                bookCard.setPadding(new Insets(15));
                bookCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85); -fx-background-radius: 10px;");
                bookCard.setMaxWidth(850);

                //book image placeholder
                ImageView bookImageView = new ImageView(new Image("bookCover.png"));
                bookImageView.setFitWidth(50);
                bookImageView.setFitHeight(60);
                bookImageView.setPreserveRatio(true);

                //center content part
                VBox infoBox = new VBox(5);

                //book title text
                Text bookTitle = new Text(b.getTitle());
                bookTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                bookTitle.setFill(Color.valueOf("#506377"));

                //author text
                Text bookAuthor = new Text("Author: " + b.getAuthor());
                bookAuthor.setFont(Font.font("Courier New", 14));
                bookAuthor.setFill(Color.valueOf("#506377"));

                //category text
                Text bookCategory = new Text("Category: " + b.getCategory());
                bookCategory.setFont(Font.font("Courier New", 14));
                bookCategory.setFill(Color.valueOf("#506377"));

                infoBox.getChildren().addAll(bookTitle, bookAuthor, bookCategory);

                //separate spacer
                Region cardSpacer = new Region();
                HBox.setHgrow(cardSpacer, Priority.ALWAYS);

                //right content part
                VBox rightBox = new VBox(5);
                rightBox.setAlignment(Pos.CENTER_RIGHT);

                //quantity text
                Text bookQty = new Text("Stock Qty: " + b.getQuantity());
                bookQty.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
                bookQty.setFill(Color.valueOf("#506377"));

                //isbn text
                Text bookIsbn = new Text("ISBN: " + b.getIsbn());
                bookIsbn.setFont(Font.font("Courier New", 14));
                bookIsbn.setFill(Color.valueOf("#506377"));

                rightBox.getChildren().addAll(bookQty, bookIsbn);

                //combine card content
                bookCard.getChildren().addAll(bookImageView, infoBox, cardSpacer, rightBox);
                catalogBox.getChildren().addAll(bookCard);
            }
        }

        scrollPane.setContent(catalogBox);
        return scrollPane;
    }
}