import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class statsView {
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
