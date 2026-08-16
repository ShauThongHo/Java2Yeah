import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class memberView {

    @SuppressWarnings("unchecked")
    public static VBox createView(BookManager bookManager, String btnStyle, String btnHoverStyle) {
        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(850);
        container.setMaxHeight(550);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 20px;");

        Text title = new Text("My Account - Borrow History");
        title.setFill(Color.valueOf("#2d3748"));
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        // search row
        String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold;";
        String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";

        Label lblSearch = new Label("Search by Borrower Name:");
        lblSearch.setStyle(labelStyle);

        TextField txtSearch = new TextField();
        txtSearch.setPromptText("Type a borrower name...");
        txtSearch.setStyle(fldStyle);

        Button btnRefresh = new Button("Refresh");
        btnRefresh.setStyle(btnStyle);
        btnRefresh.setOnMouseEntered(e -> btnRefresh.setStyle(btnHoverStyle));
        btnRefresh.setOnMouseExited(e -> btnRefresh.setStyle(btnStyle));

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(lblSearch, txtSearch, btnRefresh);

        // borrow history table
        TableView<BorrowedBook> table = new TableView<>();
        table.setPrefHeight(300);
        table.setStyle("-fx-font-family: 'Courier New';");

        TableColumn<BorrowedBook, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTitle.setPrefWidth(180);

        TableColumn<BorrowedBook, String> colBorrower = new TableColumn<>("Borrower");
        colBorrower.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        colBorrower.setPrefWidth(130);

        TableColumn<BorrowedBook, String> colBorrowDate = new TableColumn<>("Borrowed On");
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colBorrowDate.setPrefWidth(120);

        TableColumn<BorrowedBook, Integer> colDays = new TableColumn<>("Days");
        colDays.setCellValueFactory(new PropertyValueFactory<>("borrowDays"));
        colDays.setPrefWidth(70);

        TableColumn<BorrowedBook, String> colDue = new TableColumn<>("Due Date");
        colDue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colDue.setPrefWidth(120);

        table.getColumns().addAll(colTitle, colBorrower, colBorrowDate, colDays, colDue);

        TextArea txtFeedback = new TextArea();
        txtFeedback.setMaxWidth(650);
        txtFeedback.setMaxHeight(60);
        txtFeedback.setEditable(false);
        txtFeedback.setWrapText(true);
        txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        txtFeedback.setPromptText("System feedback will appear here...");

        // helper: fill the table with all records, or only those matching the search text
        // (the filter is applied client-side; BookManager stores the records as an array)
        Runnable refreshTable = () -> {
            String filter = txtSearch.getText().trim();
            BorrowedBook[] all = bookManager.borrowedBooks;
            List<BorrowedBook> filtered = new ArrayList<>();
            for (BorrowedBook b : all) {
                if (filter.isEmpty() || b.getBorrowerName().toLowerCase().contains(filter.toLowerCase())) {
                    filtered.add(b);
                }
            }
            table.getItems().setAll(filtered);
            txtFeedback.setText("Found " + filtered.size() + " borrow record(s).");
        };

        btnRefresh.setOnAction(e -> refreshTable.run());
        txtSearch.setOnAction(e -> refreshTable.run());
        refreshTable.run();

        container.getChildren().addAll(title, searchBox, table, txtFeedback);
        return container;
    }
}
