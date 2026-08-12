import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;

class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int quantity;
    
    public Book(String title, String author, String isbn, String category, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String displayInfo() {
        return "Title: " + title + "\nAuthor: " + author + "\nISBN: " + isbn + "\nCategory: " + category + "\nQuantity: " + quantity;
    }
}

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

public class project extends Application {  
    private BookManager bookManager = new BookManager() {
        //instance initializer
        {
            bookList = bookDataFile.loadBooks();
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        //main menu
        //background part
        //background image
        Image imagebgd = new Image("background.jpg");
        ImageView imageView = new ImageView(imagebgd);
        imageView.setPreserveRatio(false);

        //background layout shape
        Rectangle rectanglebgd = new Rectangle(1000, 700);
        rectanglebgd.setFill(Color.rgb(0, 0, 0, 0.5));

        StackPane rootLayout = new StackPane();

        //background scale window size
        imageView.fitWidthProperty().bind(rootLayout.widthProperty());
        imageView.fitHeightProperty().bind(rootLayout.heightProperty());

        rectanglebgd.widthProperty().bind(rootLayout.widthProperty());
        rectanglebgd.heightProperty().bind(rootLayout.heightProperty());

        //topbar part
        //topbar layout
        HBox topBar = new HBox(15); 
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 25)); 
        topBar.setPrefHeight(90);
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");

        //book icon image
        Image bookIcon = new Image("book.png");
        ImageView bookIconView = new ImageView(bookIcon);
        bookIconView.setFitWidth(45);
        bookIconView.setFitHeight(45);
        bookIconView.setPreserveRatio(true);

        //textSystemMenu
        Text textSystemMenu = new Text("System Menu --");
        textSystemMenu.setFill(Color.BLACK);
        textSystemMenu.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        textSystemMenu.setStyle("-fx-cursor: hand;");
        textSystemMenu.setOnMouseEntered(e -> textSystemMenu.setFill(Color.valueOf("#4d6177")));
        textSystemMenu.setOnMouseExited(e -> textSystemMenu.setFill(Color.BLACK));

        //make the text and image move to left and right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        //button style
        String btnstyle = "-fx-background-color: #4d6177;" + 
                         "-fx-text-fill: white;" + 
                         "-fx-font-family: 'Courier New';" + 
                         "-fx-font-weight: bold;" + 
                         "-fx-font-size: 15px;" + 
                         "-fx-background-radius: 8px;" + 
                         "-fx-cursor: hand;" +          
                         "-fx-padding: 8 20 8 20;";

        String btnHoverStyle = "-fx-background-color: #7b90a4;" + 
                             "-fx-text-fill: white;" + 
                             "-fx-font-family: 'Courier New';" + 
                             "-fx-font-weight: bold;" + 
                             "-fx-font-size: 15px;" + 
                             "-fx-background-radius: 8px;" + 
                             "-fx-cursor: hand;" + 
                             "-fx-padding: 8 20 8 20;" +
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);";

        //button catalog
        Button buttonCatalog = new Button("Catalog");

        //button donate
        Button buttonDonate = new Button("Donate");

        //button borrow
        Button buttonBorrow = new Button("Borrow");

        //button return
        Button buttonReturn = new Button("Return");

        //button dashboard
        Button buttonDashboard = new Button("Dashboard");

        //both button style
        Button [] menuButtons = {buttonCatalog, buttonDonate, buttonBorrow, buttonReturn, buttonDashboard};
        for (Button btn : menuButtons) {
            btn.setStyle(btnstyle);
            btn.setOnMouseEntered(e -> btn.setStyle(btnHoverStyle));
            btn.setOnMouseExited(e -> btn.setStyle(btnstyle));
        }

        //menu content
        StackPane centerContentArea = new StackPane();
        centerContentArea.setPadding(new Insets(20));

        //prompt text
        Text welcomeText = new Text("Welcome to Book Donation & Management System \nPlease select a menu above.");
        welcomeText.setFill(Color.WHITE);
        welcomeText.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        centerContentArea.getChildren().addAll(welcomeText);

        //menu text click events
        textSystemMenu.setOnMouseClicked(e -> {
            centerContentArea.getChildren().clear();
            centerContentArea.getChildren().add(welcomeText);
        });

        //main menu pane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(centerContentArea);
        rootLayout.getChildren().addAll(imageView, rectanglebgd, mainLayout);

        //catalog part
        //click catalog status
        buttonCatalog.setOnAction(e -> {
            centerContentArea.getChildren().clear();

            //scroll pane
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
            if(bookManager.bookList.isEmpty()) {
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
            centerContentArea.getChildren().add(scrollPane);
        });

        //donation part
        //donate status
        buttonDonate.setOnAction(e -> {
            centerContentArea.getChildren().clear();
            VBox formContainer = new VBox(15);
            formContainer.setAlignment(Pos.CENTER);
            formContainer.setMaxWidth(550);
            formContainer.setMaxHeight(500);
            formContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 20px;");

            //donate title text
            Text donateTitle = new Text("Donate New Book Form");
            donateTitle.setFill(Color.valueOf("#2d3748"));
            donateTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

            //form grid layout
            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setAlignment(Pos.CENTER);

            //label style
            String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-font-weight: bold;";
            
            //field style
            String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 16px;";

            //title part
            Label lblTitle = new Label("Book Title:");
            lblTitle.setStyle(labelStyle);
            TextField txtTitle = new TextField();
            txtTitle.setStyle(fldStyle);

            //author part
            Label lblAuthor = new Label("Author:");
            lblAuthor.setStyle(labelStyle);
            TextField txtAuthor = new TextField();
            txtAuthor.setStyle(fldStyle);

            //ISBN part
            Label lblISBN = new Label("ISBN:");
            lblISBN.setStyle(labelStyle);
            TextField txtISBN = new TextField();
            txtISBN.setPromptText("Numbers only...");
            txtISBN.setStyle(fldStyle);

            //category part
            Label lblCategory = new Label("Category:");
            lblCategory.setStyle(labelStyle);
            ComboBox<String> cmbCategory = new ComboBox<>();
            cmbCategory.getItems().addAll("Fantasy", "Science Fiction", "Mystery", "Horror", "History", "Story", "Literature");
            cmbCategory.setPromptText("SELECT CATEGORY");
            cmbCategory.setStyle(fldStyle);

            //combine form content
            grid.add(lblTitle, 0, 0); grid.add(txtTitle, 1, 0);
            grid.add(lblAuthor, 0, 1); grid.add(txtAuthor, 1, 1);
            grid.add(lblISBN, 0, 2); grid.add(txtISBN, 1, 2);
            grid.add(lblCategory, 0, 3); grid.add(cmbCategory, 1, 3);

            //feedback text area
            TextArea txtFeedback = new TextArea();
            txtFeedback.setMaxWidth(450);
            txtFeedback.setMaxHeight(70);
            txtFeedback.setEditable(false);
            txtFeedback.setWrapText(true);
            txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px;");
            txtFeedback.setPromptText("System feedback will appear here...");

            //button box part
            HBox btnBox = new HBox(20);
            btnBox.setAlignment(Pos.CENTER);

            //button add
            Button btnAdd = new Button("Confirm Add");

            //button clear
            Button btnClear = new Button("Clear Form");

            //donation button part style
            Button [] DonationButtons = {btnAdd, btnClear};
            for (Button btn : DonationButtons) {
                btn.setStyle(btnstyle);
                btn.setOnMouseEntered(event -> btn.setStyle(btnHoverStyle));
                btn.setOnMouseExited(event -> btn.setStyle(btnstyle));
            }

            //button clear status
            btnClear.setOnAction(event -> {
                txtTitle.clear();
                txtAuthor.clear();
                txtISBN.clear();
                cmbCategory.setValue(null);
                txtFeedback.clear();
            });

            //button add status
            btnAdd.setOnAction(event -> {
                if (txtTitle.getText().isBlank() || txtAuthor.getText().isBlank() || 
                    txtISBN.getText().isBlank() || cmbCategory.getValue() == null) {
                    txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: red;");
                    txtFeedback.setText("Error: Please fill in all text fields and select a category!");
                    return;
                }

                //clear isbn front and back spaces
                String isbnText = txtISBN.getText().trim();

                //check isbn numeric
                try {
                    Long.parseLong(isbnText);
                } catch (NumberFormatException ex) {
                    txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: red;");
                    txtFeedback.setText("Error: ISBN must contain numbers only (no letters/symbols)!");
                    return;
                }

                //check the isbn standard
                if (isbnText.length() != 10 && isbnText.length() != 13) {
                    txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: red;");
                    txtFeedback.setText("Warning: Standard ISBN should be 10 or 13 digits long.");
                    return;
                }

                //catch the content
                String titleStr = txtTitle.getText().trim();
                String authorStr = txtAuthor.getText().trim();
                String categoryStr = cmbCategory.getValue();

                //send the content
                String resultMsg = bookManager.addOrUpdateBook(titleStr, authorStr, isbnText, categoryStr);

                //save updated list to local file
                bookDataFile.saveBooks(bookManager.bookList);
                
                //feedback part
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: green;");
                txtFeedback.setText(resultMsg);

                //clear after success
                txtTitle.clear();
                txtAuthor.clear();
                txtISBN.clear();
                cmbCategory.setValue(null);
            });

            //combine all donation part
            btnBox.getChildren().addAll(btnAdd, btnClear);
            formContainer.getChildren().addAll(donateTitle, grid, btnBox, txtFeedback);
            centerContentArea.getChildren().add(formContainer);
        });

        //combine topbar button
        topBar.getChildren().addAll(bookIconView, textSystemMenu, spacer, buttonCatalog, buttonDonate, buttonBorrow, buttonReturn, buttonDashboard);

        Scene scene = new Scene(rootLayout, 1200, 700);

        primaryStage.setTitle("Book Donation & Management system");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}