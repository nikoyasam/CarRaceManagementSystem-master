package com.Controllers;

import com.Models.Race;
import com.Models.Result;
import com.Utilities.Constants;
import com.Utilities.Resources;
import com.Utilities.ResultsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class RaceController {
    public Button backButton; //This is the button back to go to the race history table
    public TableView<Result> resultsTable;
    public Label title;

    public void setRace(Race selectedRace) { //race object as parameter
        SimpleDateFormat sdf = new SimpleDateFormat(" (E, dd MMM yyyy HH:mm:ss z)");
        // use it to format the date of the selectedRace object
        String formattedDate = sdf.format(selectedRace.getDate()); //formatted date stored as string
        String screenTitle = selectedRace.getName() + " - " + selectedRace.getLocation() + formattedDate;
        //name, location, and formatted date of the selectedRace object
        title.setText(screenTitle);
        resultsTable.setItems(ResultsManager.retrieveAllResults(selectedRace));
    }

    public void initialize() {

        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //columns will automatically resize to fit the available space.
    }

    public void onBack(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow(); //gets the Stage object associated
        // with the backButton Button.
        FXMLLoader loader = Resources.getScreen("home.fxml"); //load the user interface defined
        // in the home.fxml file.
        Scene scene = null;
        try {
            scene = new Scene(loader.load()); // creates a new Scene object from the FXML file that was loaded
            // previously
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HomeController homeController = loader.getController();
        homeController.tabPane.getSelectionModel().select(3);
        //select the fourth tab in the home page which is to display the races
        stage.setScene(scene);
        stage.show();
    }
}
