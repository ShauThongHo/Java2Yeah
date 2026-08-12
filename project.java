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
    private BookManager bookManager = new BookManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //main menu
        //background
        Image imagebgd = new Image("background.jpg");
        ImageView imageView = new ImageView(imagebgd);
        imageView.setPreserveRatio(false);

        StackPane rootLayout = new StackPane();
        imageView.fitWidthProperty().bind(rootLayout.widthProperty());
        imageView.fitHeightProperty().bind(rootLayout.heightProperty());

        //background shape
        Rectangle rectanglebgd = new Rectangle(1000, 700);
        rectanglebgd.widthProperty().bind(rootLayout.widthProperty());
        rectanglebgd.heightProperty().bind(rootLayout.heightProperty());
        rectanglebgd.setFill(Color.rgb(0, 0, 0, 0.5));
        
        rootLayout.getChildren().addAll(imageView, rectanglebgd);

        //front shape
        HBox topBar = new HBox(15); 
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 25)); 
        topBar.setPrefHeight(90);
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");

        //book icon
        Image bookIcon = new Image("book.png");
        ImageView bookIconView = new ImageView(bookIcon);
        bookIconView.setFitWidth(45);
        bookIconView.setFitHeight(45);
        bookIconView.setPreserveRatio(true);

        //text1: Donation System
        Text text1 = new Text("System Menu --");
        text1.setFill(Color.BLACK);
        text1.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        text1.setStyle("-fx-cursor: hand;");
        text1.setOnMouseEntered(e -> text1.setFill(Color.valueOf("#4d6177")));
        text1.setOnMouseExited(e -> text1.setFill(Color.BLACK));

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

        //logic change different interface
        StackPane centerContentArea = new StackPane();
        centerContentArea.setPadding(new Insets(20));

        //prompt text
        Text welcomeText = new Text("Welcome to Book Donation & Management System \nPlease select a menu above.");
        welcomeText.setFill(Color.WHITE);
        welcomeText.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        centerContentArea.getChildren().add(welcomeText);

        //menu text click events
        text1.setOnMouseClicked(e -> {
            centerContentArea.getChildren().clear();
            centerContentArea.getChildren().add(welcomeText);
        });

        //catalog part
        //click catalog status
        buttonCatalog.setOnAction(e -> {
            centerContentArea.getChildren().clear();
            VBox catalogBox = new VBox(15);
            catalogBox.setAlignment(Pos.CENTER);

            Text title = new Text("Book Catelog & Inventory");
            title.setFill(Color.WHITE);
            title.setFont(Font.font("Courier New", FontWeight.BOLD, 22));

            TextArea txtAreaCatalog = new TextArea();
            txtAreaCatalog.setMaxWidth(600);
            txtAreaCatalog.setMaxHeight(350);
            txtAreaCatalog.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px;");
            txtAreaCatalog.setEditable(false);

            if (bookManager.bookList.isEmpty()) {
                txtAreaCatalog.setText("No books registered in the system yet.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Book b : bookManager.bookList) {
                    sb.append(b.displayInfo());
                }
                txtAreaCatalog.setText(sb.toString());
            }

            catalogBox.getChildren().addAll(title, txtAreaCatalog);
            centerContentArea.getChildren().add(catalogBox);
        });

        //donate status
        buttonDonate.setOnAction(e -> {
            centerContentArea.getChildren().clear();
            VBox formContainer = new VBox(15);
            formContainer.setAlignment(Pos.CENTER);
            formContainer.setMaxWidth(550);
            formContainer.setMaxHeight(500);
            formContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 30px;");

            Text donateTitle = new Text("Donate New Book Form");
            donateTitle.setFill(Color.valueOf("#2d3748"));
            donateTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 22));

            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setAlignment(Pos.CENTER);

            String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold;";
            String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";

            Label lblTitle = new Label("Book Title:");
            lblTitle.setStyle(labelStyle);
            TextField txtTitle = new TextField();
            txtTitle.setStyle(fldStyle);

            Label lblAuthor = new Label("Author:");
            lblAuthor.setStyle(labelStyle);
            TextField txtAuthor = new TextField();
            txtAuthor.setStyle(fldStyle);

            Label lblISBN = new Label("ISBN:");
            lblISBN.setStyle(labelStyle);
            TextField txtISBN = new TextField();
            txtISBN.setPromptText("Numbers only (e.g. 10 or 13 digits)");
            txtISBN.setStyle(fldStyle);

            Label lblCategory = new Label("Category:");
            lblCategory.setStyle(labelStyle);
            ComboBox<String> cmbCategory = new ComboBox<>();
            cmbCategory.getItems().addAll("计算机科学", "文学小说", "自然科学", "历史哲学", "少儿读物");
            cmbCategory.setPromptText("SELECT CATEGORY");
            cmbCategory.setStyle(fldStyle);

            grid.add(lblTitle, 0, 0); grid.add(txtTitle, 1, 0);
            grid.add(lblAuthor, 0, 1); grid.add(txtAuthor, 1, 1);
            grid.add(lblISBN, 0, 2); grid.add(txtISBN, 1, 2);
            grid.add(lblCategory, 0, 3); grid.add(cmbCategory, 1, 3);

            TextArea txtFeedback = new TextArea();
            txtFeedback.setMaxWidth(450);
            txtFeedback.setMaxHeight(70);
            txtFeedback.setEditable(false);
            txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px;");
            txtFeedback.setPromptText("System feedback will appear here...");

            HBox btnBox = new HBox(20);
            btnBox.setAlignment(Pos.CENTER);
            Button btnAdd = new Button("Confirm Add");
            Button btnClear = new Button("Clear Form");

            btnAdd.setStyle("-fx-background-color: #2b6cb0; -fx-text-fill: white; -fx-font-family: 'Courier New'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-padding: 6 15;");
            btnClear.setStyle("-fx-background-color: #a0aec0; -fx-text-fill: white; -fx-font-family: 'Courier New'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-padding: 6 15;");

            btnClear.setOnAction(event -> {
                txtTitle.clear();
                txtAuthor.clear();
                txtISBN.clear();
                cmbCategory.setValue(null);
                txtFeedback.clear();
            });

            btnAdd.setOnAction(event -> {
                if (txtTitle.getText().isBlank() || txtAuthor.getText().isBlank() || 
                    txtISBN.getText().isBlank() || cmbCategory.getValue() == null) {
                    txtFeedback.setStyle("-fx-text-fill: red;");
                    txtFeedback.setText("Error: Please fill in all text fields and select a category!");
                    return;
                }

                String isbnText = txtISBN.getText().trim();

                try {
                    Long.parseLong(isbnText);
                } catch (NumberFormatException ex) {
                    txtFeedback.setStyle("-fx-text-fill: red;");
                    txtFeedback.setText("Error: ISBN must contain numbers only (no letters/symbols)!");
                    return;
                }

                if (isbnText.length() != 10 && isbnText.length() != 13) {
                    txtFeedback.setStyle("-fx-text-fill: red;");
                    txtFeedback.setText("Warning: Standard ISBN should be 10 or 13 digits long.");
                }

                String titleStr = txtTitle.getText().trim();
                String authorStr = txtAuthor.getText().trim();
                String categoryStr = cmbCategory.getValue();

                String resultMsg = bookManager.addOrUpdateBook(titleStr, authorStr, isbnText, categoryStr);
                
                txtFeedback.setStyle("-fx-text-fill: green;");
                txtFeedback.setText(resultMsg);

                txtTitle.clear();
                txtAuthor.clear();
                txtISBN.clear();
                cmbCategory.setValue(null);
            });

            btnBox.getChildren().addAll(btnAdd, btnClear);
            formContainer.getChildren().addAll(donateTitle, grid, btnBox, txtFeedback);
            centerContentArea.getChildren().add(formContainer);
        });

        topBar.getChildren().addAll(bookIconView, text1, spacer, buttonCatalog, buttonDonate, buttonBorrow, buttonReturn, buttonDashboard);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(centerContentArea);

        rootLayout.getChildren().add(mainLayout);

        Scene scene = new Scene(rootLayout, 1200, 700);

        primaryStage.setTitle("Book Donation & Management system");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}