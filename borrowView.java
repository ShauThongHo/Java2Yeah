import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class borrowView {

    @SuppressWarnings("unchecked")
    public static VBox createView(BookManager bookManager, String btnStyle, String btnHoverStyle) {
        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(850);
        container.setMaxHeight(550);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15px; -fx-padding: 20px;");

        Text title = new Text("Borrow Book Form");
        title.setFill(Color.valueOf("#2d3748"));
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        // current borrow history table
        TableView<BorrowedBook> table = new TableView<>();
        table.setPrefHeight(220);
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
        table.getItems().setAll(bookManager.borrowedBooks);

        // input area
        String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold;";
        String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";

        TextField txtIsbn = new TextField();
        txtIsbn.setPromptText("ISBN");
        txtIsbn.setStyle(fldStyle);

        TextField txtName = new TextField();
        txtName.setPromptText("Borrower name");
        txtName.setStyle(fldStyle);

        Spinner<Integer> spnDays = new Spinner<>(1, 7, 3);
        spnDays.setStyle(fldStyle);
        spnDays.setMaxWidth(80);

        Button btnBorrow = new Button("Confirm Borrow");
        Button btnClear = new Button("Clear");

        btnBorrow.setStyle(btnStyle);
        btnBorrow.setOnMouseEntered(e -> btnBorrow.setStyle(btnHoverStyle));
        btnBorrow.setOnMouseExited(e -> btnBorrow.setStyle(btnStyle));

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
            txtName.clear();
            spnDays.getValueFactory().setValue(3);
            txtFeedback.clear();
        });

        btnBorrow.setOnAction(e -> {
            try {
                String isbnInput = txtIsbn.getText().trim();
                String nameInput = txtName.getText().trim();
                int days = spnDays.getValue();

                if (isbnInput.isEmpty() || nameInput.isEmpty()) {
                    throw new IllegalArgumentException("Error: Please fill in both ISBN and borrower name!");
                }

                String resultMsg = bookManager.borrowBook(isbnInput, nameInput, days);

                // persist both the updated stock and the new borrow record
                bookDataFile.saveBooks(bookManager.bookList);
                borrowDataFile.saveBorrows(bookManager.borrowedBooks);

                table.getItems().setAll(bookManager.borrowedBooks);

                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: green;");
                txtFeedback.setText(resultMsg);
                txtIsbn.clear();
                txtName.clear();
                spnDays.getValueFactory().setValue(3);

            } catch (BorrowException ex) {
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: red;");
                txtFeedback.setText(ex.getMessage());
            } catch (NumberFormatException ex) {
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: red;");
                txtFeedback.setText("Error: ISBN must contain numbers only!");
            } catch (IllegalArgumentException ex) {
                txtFeedback.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: red;");
                txtFeedback.setText(ex.getMessage());
            }
        });

        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().addAll(txtIsbn, txtName, spnDays, btnBorrow, btnClear);

        container.getChildren().addAll(title, table, actionBox, txtFeedback);
        return container;
    }
}
