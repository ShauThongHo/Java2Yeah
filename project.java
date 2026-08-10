//package application;   
import javafx.application.Application;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;  

public class project extends Application {  
  
    @Override  
    public void start(Stage primaryStage) throws Exception { 
        Button btn1 = new Button("Say, Hello World"); 
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        
        StackPane root = new StackPane();  
        root.getChildren().add(btn1);
        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("First JavaFX Application");  
        primaryStage.setScene(scene);  
        primaryStage.show();
    }
    public static void main (String[] args)  
    {  
        launch(args);  
    } 
}