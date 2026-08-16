import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class returnView {

    @SuppressWarnings("unchecked")
    public static VBox createReturnView(BookManager bookManager, String btnStyle, String btnHoverStyle) {
        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(850);
        container.setMaxHeight(550);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 20px;");

        Text title = new Text("Book Return & Stock Update");
        title.setFill(Color.valueOf("#2d3748"));
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        TableView<Book> table = new TableView<>();
        table.setPrefHeight(220);
        table.setStyle("-fx-font-family: 'Courier New';");

        TableColumn<Book, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTitle.setPrefWidth(200);

        TableColumn<Book, String> colAuthor = new TableColumn<>("Author");
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAuthor.setPrefWidth(150);

        TableColumn<Book, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colIsbn.setPrefWidth(130);

        TableColumn<Book, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCategory.setPrefWidth(120);

        TableColumn<Book, Integer> colQty = new TableColumn<>("Quantity");
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setPrefWidth(100);

        table.getColumns().addAll(colTitle, colAuthor, colIsbn, colCategory, colQty);
        table.getItems().setAll(bookManager.bookList);

        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER);

        String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold;";
        String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";

        Label lblIsbn = new Label("Enter ISBN to Return:");
        lblIsbn.setStyle(labelStyle);

        TextField txtIsbn = new TextField();
        txtIsbn.setPromptText("ISBN numbers...");
        txtIsbn.setStyle(fldStyle);

        Button btnReturn = new Button("Confirm Return");
        Button btnClear = new Button("Clear");

        btnReturn.setStyle(btnStyle);
        btnReturn.setOnMouseEntered(e -> btnReturn.setStyle(btnHoverStyle));
        btnReturn.setOnMouseExited(e -> btnReturn.setStyle(btnStyle));

        btnClear.setStyle(btnStyle);
        btnClear.setOnMouseEntered(e -> btnClear.setStyle(btnHoverStyle));
        btnClear.setOnMouseExited(e -> btnClear.setStyle(btnStyle));

        TextArea txtFeedback = new TextArea();
        txtFeedback.setMaxWidth(650);
        txtFeedback.setMaxHeight(60);
        txtFeedback.setEditable(false);
        txtFeedback.setWrapText(true);
        txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        txtFeedback.setPromptText("System feedback will appear here...");

        btnClear.setOnAction(e -> {
            txtIsbn.clear();
            txtFeedback.clear();
        });

        btnReturn.setOnAction(e -> {
            try {
                String rawIsbn = txtIsbn.getText().trim();
                if (rawIsbn.isEmpty()) {
                    throw new IllegalArgumentException("Error: ISBN field cannot be empty!");
                }
                String isbnInput = BookManager.sanitizeIsbn(rawIsbn);
                if (!BookManager.isValidIsbn(isbnInput)) {
                    throw new IllegalArgumentException("Error: ISBN must be a standard 10 or 13 digit number (hyphens and spaces are allowed).");
                }

                // only an ISBN with an active borrow record may be returned;
                // BookManager rejects phantom returns without touching inventory
                borrowManager bm = new borrowManager();

                String resultMsg = bm.returnBook(isbnInput, bookManager);

                // refresh the stock table so the incremented quantity is visible
                table.getItems().setAll(bookManager.bookList);

                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: green;");
                txtFeedback.setText(resultMsg);
                txtIsbn.clear();

            } catch (borrowException ex) {
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: red;");
                txtFeedback.setText(ex.getMessage());
            } catch (IllegalArgumentException ex) {
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: red;");
                txtFeedback.setText(ex.getMessage());
            }
        });

        actionBox.getChildren().addAll(lblIsbn, txtIsbn, btnReturn, btnClear);
        container.getChildren().addAll(title, table, actionBox, txtFeedback);
        return container;
    }

    public static VBox createStatsView(BookManager bookManager, String btnStyle, String btnHoverStyle) {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(650);
        container.setMaxHeight(500);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 25px;");

        Text title = new Text("System Statistics & Summary");
        title.setFill(Color.valueOf("#2d3748"));
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(30);
        statsGrid.setVgap(15);
        statsGrid.setAlignment(Pos.CENTER);

        Label lblTitlesTitle = new Label("Total Book Titles:");
        lblTitlesTitle.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-font-weight: bold;");
        Label lblTitlesVal = new Label("0");
        lblTitlesVal.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: #3498db; -fx-font-weight: bold;");

        Label lblQtyTitle = new Label("Total Inventory Quantity:");
        lblQtyTitle.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-font-weight: bold;");
        Label lblQtyVal = new Label("0");
        lblQtyVal.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        statsGrid.add(lblTitlesTitle, 0, 0);
        statsGrid.add(lblTitlesVal, 1, 0);
        statsGrid.add(lblQtyTitle, 0, 1);
        statsGrid.add(lblQtyVal, 1, 1);

        TextArea txtSummary = new TextArea();
        txtSummary.setMaxWidth(550);
        txtSummary.setMaxHeight(120);
        txtSummary.setEditable(false);
        txtSummary.setWrapText(true);
        txtSummary.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px;");

        Button btnRefresh = new Button("Refresh Summary");
        btnRefresh.setStyle(btnStyle);
        btnRefresh.setOnMouseEntered(e -> btnRefresh.setStyle(btnHoverStyle));
        btnRefresh.setOnMouseExited(e -> btnRefresh.setStyle(btnStyle));

        Runnable calculateStats = () -> {
            int titles = bookManager.bookList.size();
            int totalQty = bookManager.bookList.stream().mapToInt(Book::getQuantity).sum();

            lblTitlesVal.setText(String.valueOf(titles));
            lblQtyVal.setText(String.valueOf(totalQty));

            StringBuilder report = new StringBuilder();
            report.append("=== COMMUNITY LIBRARY SUMMARY REPORT ===\n");
            report.append("• Total Unique Book Titles: ").append(titles).append("\n");
            report.append("• Overall Physical Stock Quantity: ").append(totalQty).append(" copies\n");
            report.append("• System Status: Operational & synchronized with local data.");
            txtSummary.setText(report.toString());
        };

        btnRefresh.setOnAction(e -> calculateStats.run());
        calculateStats.run();

        container.getChildren().addAll(title, statsGrid, txtSummary, btnRefresh);
        return container;
    }
}
