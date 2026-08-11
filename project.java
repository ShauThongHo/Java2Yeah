import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
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

public class project extends Application {  
    @Override
    public void start(Stage primaryStage) throws Exception {
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
        rectanglebgd.setFill(Color.rgb(0, 0, 0, 0.3));
        
        rootLayout.getChildren().addAll(imageView, rectanglebgd);

        //front shape
        HBox topBar = new HBox(15); 
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 25)); 
        topBar.setPrefHeight(90);
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85);");
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85);");

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