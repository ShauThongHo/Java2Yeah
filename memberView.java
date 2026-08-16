import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class memberView {

    public static ScrollPane createView(bookManager bookManager, String btnStyle, String btnHoverStyle) {
        // explicit -fx-text-fill keeps the labels readable (dark text) even
        // inside the ScrollPane, where the theme's derived text color can
        // otherwise resolve to white.
        String labelStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2d3748;";
        String fldStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";
        String feedbackStyle = "-fx-font-family: 'Courier New'; -fx-font-size: 14px;";

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // outer centering box (the ScrollPane needs exactly one child)
        VBox outer = new VBox();
        outer.setAlignment(Pos.CENTER);
        outer.setFillWidth(false);
        outer.setPadding(new Insets(10));

        // main account card
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(720);
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.92); -fx-background-radius: 15px; -fx-padding: 20px;");

        Text title = new Text("My Library Account");
        title.setFill(Color.valueOf("#2d3748"));
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 25));

        // --- account form ---
        Label lblName = new Label("Your Name:");
        lblName.setStyle(labelStyle);
        TextField txtName = new TextField();
        txtName.setPromptText("Enter your name...");
        txtName.setPrefWidth(320);
        txtName.setStyle(fldStyle);

        Label lblPhone = new Label("Phone (optional):");
        lblPhone.setStyle(labelStyle);
        TextField txtPhone = new TextField();
        txtPhone.setPromptText("Phone number");
        txtPhone.setPrefWidth(320);
        txtPhone.setStyle(fldStyle);

        Label lblEmail = new Label("Email (optional):");
        lblEmail.setStyle(labelStyle);
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email address");
        txtEmail.setPrefWidth(320);
        txtEmail.setStyle(fldStyle);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);
        grid.add(lblName, 0, 0);  grid.add(txtName, 1, 0);
        grid.add(lblPhone, 0, 1); grid.add(txtPhone, 1, 1);
        grid.add(lblEmail, 0, 2); grid.add(txtEmail, 1, 2);

        // --- buttons ---
        Button btnLogin = new Button("Login / Register");
        Button btnClear = new Button("Clear Form");

        btnLogin.setStyle(btnStyle);
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(btnHoverStyle));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(btnStyle));

        btnClear.setStyle(btnStyle);
        btnClear.setOnMouseEntered(e -> btnClear.setStyle(btnHoverStyle));
        btnClear.setOnMouseExited(e -> btnClear.setStyle(btnStyle));

        HBox btnBox = new HBox(20);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(btnLogin, btnClear);

        // --- feedback ---
        TextArea txtFeedback = new TextArea();
        txtFeedback.setMaxWidth(560);
        txtFeedback.setMaxHeight(60);
        txtFeedback.setEditable(false);
        txtFeedback.setWrapText(true);
        txtFeedback.setStyle(feedbackStyle);
        txtFeedback.setPromptText("System feedback will appear here...");

        // --- "My Borrowed Books" section ---
        Text sectionTitle = new Text("My Borrowed Books");
        sectionTitle.setFill(Color.valueOf("#2d3748"));
        sectionTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 18));

        VBox myBooksBox = new VBox(10);
        myBooksBox.setAlignment(Pos.TOP_CENTER);
        myBooksBox.setStyle("-fx-background-color: rgba(237, 242, 247, 0.85); -fx-background-radius: 10px; -fx-padding: 12px;");

        Text placeholder = new Text("Login / register above to see your borrowed books.");
        placeholder.setFill(Color.valueOf("#718096"));
        placeholder.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        myBooksBox.getChildren().add(placeholder);

        // shows the cards for the reader currently typed in the name field
        Runnable refreshBooks = () -> {
            String name = txtName.getText().trim();
            myBooksBox.getChildren().clear();
            boolean any = false;

            borrowManager bm = new borrowManager();

            for (borrowedBook bb : bm.borrowedBooks) {
                if (bb.getBorrowerName().equalsIgnoreCase(name)) {
                    any = true;
                    myBooksBox.getChildren().add(createBookCard(bb));
                }
            }
            if (!any) {
                Text empty = new Text("No borrowed books found for \"" + name + "\".");
                empty.setFill(Color.valueOf("#718096"));
                empty.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
                myBooksBox.getChildren().add(empty);
            }
        };

        btnLogin.setOnAction(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                txtFeedback.setStyle(feedbackStyle + " -fx-text-fill: red;");
                txtFeedback.setText("Error: Please enter your name!");
                return;
            }

            Member[] members = memberDataFile.loadMembers();
            Member found = null;
            for (Member m : members) {
                if (m != null && m.getName().equalsIgnoreCase(name)) {
                    found = m;
                    break;
                }
            }

            if (found == null) {
                // not registered yet -> create a new member profile
                String newId = memberDataFile.nextMemberId(members);
                Member newMember = new Member(
                        newId, name,
                        txtPhone.getText().trim(),
                        txtEmail.getText().trim(),
                        LocalDate.now().toString());
                Member[] updated = memberDataFile.growMemberArray(members);
                updated[updated.length - 1] = newMember;
                memberDataFile.saveMembers(updated);

                txtFeedback.setStyle(feedbackStyle + " -fx-text-fill: green;");
                txtFeedback.setText("Welcome, " + name + "! (Member ID: " + newId + ")");
            } else {
                txtFeedback.setStyle(feedbackStyle + " -fx-text-fill: green;");
                txtFeedback.setText("Welcome back, " + found.getName() + "! (Member ID: " + found.getMemberId() + ")");
            }

            refreshBooks.run();
        });

        btnClear.setOnAction(e -> {
            txtName.clear();
            txtPhone.clear();
            txtEmail.clear();
            txtFeedback.clear();
            myBooksBox.getChildren().clear();
            Text ph = new Text("Login / register above to see your borrowed books.");
            ph.setFill(Color.valueOf("#718096"));
            ph.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
            myBooksBox.getChildren().add(ph);
        });

        card.getChildren().addAll(title, grid, btnBox, txtFeedback, sectionTitle, myBooksBox);
        outer.getChildren().add(card);
        scrollPane.setContent(outer);
        return scrollPane;
    }

    /** Builds one small card for a single borrowed book. */
    private static VBox createBookCard(borrowedBook bb) {
        VBox bookCard = new VBox(4);
        bookCard.setMaxWidth(620);
        bookCard.setPadding(new Insets(10, 14, 10, 14));
        bookCard.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-border-color: #cbd5e0; -fx-border-radius: 8px;");

        Text txtTitle = new Text(bb.getTitle());
        txtTitle.setFill(Color.valueOf("#2d3748"));
        txtTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 16));

        Text txtBorrowed = new Text("Borrowed on: " + bb.getBorrowDate());
        txtBorrowed.setFill(Color.valueOf("#4a5568"));
        txtBorrowed.setFont(Font.font("Courier New", 13));

        Text txtDue = new Text("Due: " + bb.getDueDate());
        txtDue.setFill(Color.valueOf("#4a5568"));
        txtDue.setFont(Font.font("Courier New", 13));

        Text txtStatus = new Text();
        txtStatus.setFont(Font.font("Courier New", FontWeight.BOLD, 14));

        long daysLeft;
        try {
            daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(bb.getDueDate()));
        } catch (DateTimeParseException e) {
            txtStatus.setText("Unreadable date - check record");
            txtStatus.setFill(Color.valueOf("#e53e3e")); // red
            bookCard.getChildren().addAll(txtTitle, txtBorrowed, txtDue, txtStatus);
            return bookCard;
        }

        if (daysLeft < 0) {
            txtStatus.setText("Overdue by " + (-daysLeft) + " day(s)");
            txtStatus.setFill(Color.valueOf("#e53e3e")); // red
        } else if (daysLeft == 0) {
            txtStatus.setText("Due today");
            txtStatus.setFill(Color.valueOf("#e53e3e")); // red
        } else {
            txtStatus.setText("On time - " + daysLeft + " day(s) left");
            txtStatus.setFill(Color.valueOf("#38a169")); // green
        }

        bookCard.getChildren().addAll(txtTitle, txtBorrowed, txtDue, txtStatus);
        return bookCard;
    }
}
