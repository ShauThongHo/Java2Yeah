import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class Vehicle {
    int vehicleID;
    String model;
    String brand;
    double engineCapacity;

    public Vehicle (int vehicleID, String model, String brand, double engineCapacity) {
        this.vehicleID= vehicleID;
        this.model= model;
        this.brand = brand;
        this.engineCapacity = engineCapacity;
    }

    //getter
    public int getVehicleID() {
        return vehicleID;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    //setter
    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String displayInfo() {
        return "\n\n==Vehicle Information==" + "\nVehicle ID: VH" + getVehicleID() + "\nModel: " + getModel() + "\nBrand: " + getBrand() + "\nEngine Capacity: " + getEngineCapacity();
    }
}

class Motorcycle extends Vehicle {
    boolean hasCarrier;

    public Motorcycle(int vehicleID, String model, String brand, double engineCapacity, boolean hasCarrier) {
        super(vehicleID, model, brand, engineCapacity);
        this.hasCarrier = hasCarrier;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + "\nHas Carrier?: " + hasCarrier;
    }
}

class Car extends Vehicle {
    int numberOfDoors;

    public Car(int vehicleID, String model, String brand, double engineCapacity, int numberOfDoors) {
        super(vehicleID, model, brand, engineCapacity);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + "\nNumber of Doors: " + numberOfDoors;
    }
}

class Van extends Vehicle {
    double loadCapacity;

    public Van(int vehicleID, String model, String brand, double engineCapacity, double loadCapacity) {
        super(vehicleID, model, brand, engineCapacity);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + "\nLoad Capacity: " + loadCapacity;
    }
}

class VehicleManager {
    ArrayList <Vehicle> vehicleList = new ArrayList<>();

    public String addVehicle(Vehicle v) {
        for(Vehicle existingVehicle : vehicleList) {
            if(existingVehicle.getModel().equalsIgnoreCase(v.getModel())) {
                return "Error: This car is existing, cannont add repeatly";
            }
        }
        vehicleList.add(v);
        return "Successfully added: " + v.getModel();
    }

    public String searchCar(String model) {
        for(Vehicle v : vehicleList) {
            if(v instanceof Car && v.getModel().equalsIgnoreCase(model)) {
                return "Car Found:\n\n" + v.displayInfo();
            }
        }
        return "Error: Car with model " + model + " not found.";
    }

    public String displayAllVehicles() {
        if(vehicleList.isEmpty()) {
            return "No vehicles registered in the system";
        }
        String output = "\n==All Vehicles List==";
        for(Vehicle v : vehicleList) {
            output += v.displayInfo();
        }
        return output;
    }
}

public class ASGM_B250035A extends Application {
    TextField txtfldVehicleID;
    TextField txtfldModel;
    TextField txtfldBrand;
    TextField txtfldEngineCapacity;
    VehicleManager manager = new VehicleManager();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //labelvehicleid
        Label lblVehicleID = new Label("Vehicle ID: ");
        lblVehicleID.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //textfieldvehicleid
        txtfldVehicleID = new TextField();
        txtfldVehicleID.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //labelmodel
        Label lblModel = new Label("Model: ");
        lblModel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //textfieldmodel
        txtfldModel = new TextField();
        txtfldModel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //labelbrand
        Label lblBrand = new Label("Brand: ");
        lblBrand.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //textfieldbrand
        txtfldBrand = new TextField();
        txtfldBrand.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //labelenginecapacity
        Label lblEngineCapacity = new Label("Engine Capacity: ");
        lblEngineCapacity.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //textfieldenginecapacity
        txtfldEngineCapacity = new TextField();
        txtfldEngineCapacity.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //labeltype
        Label lblType = new Label("Type: ");
        lblType.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //combobox
        ComboBox <String> cbVehicleType = new ComboBox<>();
        cbVehicleType.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");
        cbVehicleType.setPromptText("SELECT");
        cbVehicleType.setMaxWidth(125);
        cbVehicleType.getItems().addAll(
            "Car",
            "Motorcycle",
            "Van"
        );

        //buttonaddvehicle
        Button btnAddVehicle = new Button("Add Vehicle");
        btnAddVehicle.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //buttonsearchcar
        Button btnSearchCar = new Button("Search Car");
        btnSearchCar.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //buttondisplayall
        Button btnDisplayAll = new Button("Display All");
        btnDisplayAll.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //labeloutput
        Label lblOutput = new Label("Output: ");
        lblOutput.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");

        //textareaoutput
        TextArea txtareaOutput = new TextArea();
        txtareaOutput.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px;");
        txtareaOutput.setEditable(false);

        //statusaddvehicle
        btnAddVehicle.setOnAction(e -> {
            if(txtfldVehicleID.getText().isBlank() || txtfldModel.getText().isBlank() || txtfldBrand.getText().isBlank() || txtfldEngineCapacity.getText().isBlank() || cbVehicleType.getValue() == null) {
                txtareaOutput.setText("Error: Please fill in all fields and select vehicle type");
                return;
            }

            try {
                int id = Integer.parseInt(txtfldVehicleID.getText().trim());
                double capacity = Double.parseDouble(txtfldEngineCapacity.getText().trim());
                String model = txtfldModel.getText().trim();
                String brand = txtfldBrand.getText().trim();
                String type = cbVehicleType.getValue();

                Vehicle newVehicle = null;
                if(type.equals ("Car")) {
                    newVehicle = new Car(id, model, brand, capacity, 4);
                } else if(type.equals("Motorcycle")) {
                    newVehicle = new Motorcycle(id, model, brand, capacity, false);
                } else if(type.equals("Van")) {
                    newVehicle = new Van(id, model, brand, capacity, 1500.0);
                }

                String resultMessage = manager.addVehicle(newVehicle);
                txtareaOutput.setText(resultMessage);

                if(resultMessage.startsWith("Successfully")) {
                    txtfldVehicleID.clear();
                    txtfldModel.clear();
                    txtfldBrand.clear();
                    txtfldEngineCapacity.clear();
                    cbVehicleType.setValue(null);
                }
            } catch(NumberFormatException exception) {
                txtareaOutput.setText("Error: Vehicle ID and Engine Capacity must be valid numbers");
            }
        });

        //statussearchcar
        btnSearchCar.setOnAction(e -> {
            String searchModel = txtfldModel.getText().trim();
            if(searchModel.isBlank()) {
                txtareaOutput.setText("Error: Please enter a Model to search");
                return;
            }

            String resultMessage = manager.searchCar(searchModel);
            txtareaOutput.setText(resultMessage);
        });

        //statusdisplayall
        btnDisplayAll.setOnAction(e -> {
            String allData = manager.displayAllVehicles();
            txtareaOutput.setText(allData);
        });

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setVgap(10);
        gridpane.setHgap(30);

        gridpane.add(lblVehicleID, 0, 0);
        gridpane.add(txtfldVehicleID, 1, 0);

        gridpane.add(lblBrand, 0, 2);
        gridpane.add(txtfldBrand, 1, 2);

        gridpane.add(lblModel, 0, 1);
        gridpane.add(txtfldModel, 1, 1);

        gridpane.add(lblEngineCapacity, 2, 0);
        gridpane.add(txtfldEngineCapacity, 3, 0);

        gridpane.add(lblType, 2, 1);
        gridpane.add(cbVehicleType, 3, 1);

        Pane pane = new Pane();
        
        //paneposition
        btnAddVehicle.setLayoutX(70);
        btnAddVehicle.setLayoutY(10);
        btnSearchCar.setLayoutX(270);
        btnSearchCar.setLayoutY(10);
        btnDisplayAll.setLayoutX(470);
        btnDisplayAll.setLayoutY(10);
        
        pane.getChildren().addAll(btnAddVehicle, btnSearchCar, btnDisplayAll);

        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 15;");
        vbox.getChildren().addAll(gridpane, pane, lblOutput, txtareaOutput);

        Scene scene = new Scene(vbox, 700, 700);

        primaryStage.setTitle("Vehicle Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
