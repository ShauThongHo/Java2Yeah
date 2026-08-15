import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class returnView {

    public void display() {
        Stage stage = new Stage();
        stage.setTitle("Library System - Return View");

        Label titleLabel = new Label("Please enter the book title or ISBN:");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField bookInput = new TextField();
        bookInput.setPromptText("e.g., Java Programming");
        bookInput.setMaxWidth(250);

        Button returnButton = new Button("Confirm Return");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: green;");

        returnButton.setOnAction(e -> {
            String bookName = bookInput.getText();
            if (bookName.isEmpty()) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Please enter a book title first!");
            } else {
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Successfully returned: \"" + bookName + "\"!");
                bookInput.clear();
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, bookInput, returnButton, messageLabel, backButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
