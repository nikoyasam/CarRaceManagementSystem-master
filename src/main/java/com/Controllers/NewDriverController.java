package com.Controllers;

import com.Models.Driver;
import com.Utilities.Constants;
import com.Utilities.DriversManager;
import com.Utilities.Messages;
import com.Utilities.Resources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;

public class NewDriverController {
    public Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField teamField;

    @FXML
    private TextField carField;


    @FXML
    private Button saveButton;

    private DriversController driversController;
    private Driver selectedDriver;  //driver object user has selected
    private boolean isUpdate; //boolean flag indicating whether the user is updating an existing
    // driver or creating a new one.

    public void initialize() {
        TextFormatter<String> numericOnlyFormatter = new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null);
        ageField.setTextFormatter(numericOnlyFormatter);
        //"TextFormatter" object that only allows numeric characters in the age field.
    }

    @FXML
    public void onSaveButtonClicked(ActionEvent actionEvent) throws IOException {
        //gets the text that the user entered into the nameField, ageField, teamField, and carField text fields
        String name = nameField.getText();
        int age = 0;
        try { age = Integer.parseInt(ageField.getText()); } catch (Exception e) { e.printStackTrace(); }
        //parse the age field as an integer
        String team = teamField.getText();
        String car = carField.getText();

        if (name.equals("")) {   //validates name
            Messages.error("Invalid Name", "Please enter a valid name!");
            return;
        }

        if (team.equals("")) {  //validates team
            Messages.error("Invalid Team", "Please enter a valid team name!");
            return;
        }

        if (car.equals("")) {   //validates car
            Messages.error("Invalid Car", "Please enter a valid car name!");
            return;
        }

        if (age < 20) {    //validates age
            Messages.error("Invalid Age", "Please enter an age greater than or equal to 20!");
            return;
        }

        Driver newDriver = new Driver(name, age, team, car, isUpdate ? selectedDriver.getPoints() : 20);
        //all the fields are valid, a new Driver object is created with the entered information.
        if (isUpdate) DriversManager.updateDriver(selectedDriver, newDriver);
        else DriversManager.addDriver(newDriver);
        Messages.information("Success", "Driver saved successfully!");

        nameField.setText("");
        ageField.setText("");
        teamField.setText("");
        carField.setText("");
        onCancel(actionEvent);
    }

    public void setDriver(Driver driver) {
        nameField.setText(driver.getName()); //sets the value of the name field to the driver's name using
        // the setName() method.
        ageField.setText(driver.getAge() + "");//: sets the value of the age field to the driver's age
        // using the setAge() method. Since the setText() method accepts only
        teamField.setText(driver.getTeam()); // sets the value of the team field to the driver's team
        // using the setTeam() method.
        carField.setText(driver.getCar());
        this.isUpdate = true;  //user is updating an existing driver.
        this.selectedDriver = driver;
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = Resources.getScreen("home.fxml"); //loads the home.fxml
        // file using the Resources class
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e); //if an exception occurs while loading the fxml file,
            // a runtime exception is thrown.
        }
        HomeController homeController = loader.getController();
        stage.setScene(scene);
        stage.show();
    }
}
