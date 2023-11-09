package com.Controllers;

import com.Models.Race;
import com.Utilities.Constants;
import com.Utilities.RaceManager;
import com.Utilities.Resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RaceHistoryController {
    public TableView<Race> racesTable;  //display the table
    public TextField searchField;
    public Button searchBtn; //search button to search for specific races
    private final ObservableList<Race> races = FXCollections.observableArrayList();

    public void initialize() {
        racesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //columns will be resized to
        // fill the available space.
        racesTable.setItems(RaceManager.retrieveAllRaces()); //race objects received from a tables class

        racesTable.setOnMouseClicked(event -> { //it is an event listener to click the button so u can retrieve
            // more details of the race
            if (event.getClickCount() == 2) { // so u can retrieve more details of the race
                Race race = racesTable.getSelectionModel().getSelectedItem();
                goToRaceViewScreen(null, event, race);
            }
        });

        TableColumn<Race, Void> seeMoreColumn = new TableColumn<>("");
        seeMoreColumn.setCellFactory(param -> new TableCell<Race, Void>() {
            //seeMoreColumn is colum which refers to another
            private final Button seeMoreButton = new Button("See more..."); //this is the button for see more

            {
                seeMoreButton.setOnAction(event -> {
                    Race race = getTableView().getItems().get(getIndex());
                    goToRaceViewScreen(event, null, race);
                });
                seeMoreButton.prefWidthProperty().bind(seeMoreColumn.widthProperty());
                seeMoreButton.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) { //update the content of each cell in the
                // additional column
                super.updateItem(item, empty); //ensure cell is updated correctly

                if (empty) {
                    setGraphic(null);  //if the cell is empty it will set to null
                } else {
                    setGraphic(seeMoreButton);
                }
            }
        });
        seeMoreColumn.setStyle("-fx-alignment: CENTER;");
        racesTable.getColumns().addAll(seeMoreColumn);
    }

    public void goToRaceViewScreen(ActionEvent event, MouseEvent mEvent, Race race) {
        //this is the stage for viewing further details of the race
        Stage stage;
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            stage = (Stage) ((Node) mEvent.getSource()).getScene().getWindow();
        }

        FXMLLoader loader = Resources.getScreen("race.fxml"); //loads the fxml file for the
        //race view screen
        Scene scene = null;
        try {
            scene = new Scene(loader.load());  //creates a new Scene by loading the FXML file for the race
            // view screen.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RaceController controller = loader.getController(); //gets the controller for race view screen
        controller.setRace(race); // Race object to be displayed on the screen.
        stage.setScene(scene);  //sets the scene for the stage and shows the stage.
        stage.show();
    }

    public void onSearch(ActionEvent actionEvent) { //this method is called when the user clicks the search
        String searchText = searchField.getText().toLowerCase(); //It gets the text from a search field and
        // converts it to lowercase.
        if (searchText.isEmpty()) racesTable.setItems(RaceManager.retrieveAllRaces());
        //If the search text is empty, it sets the table items to all races using this method
        else racesTable.setItems(RaceManager.searchRaces(searchText)); // sets the table items to a
        // filtered list of races that match the search text
    }

    public void refreshTable() { //method is called to refresh the table data.
        searchField.setText("");  //clears the search field by setting its text to an empty string.
        racesTable.setItems(RaceManager.retrieveAllRaces()); //sets the table items to all races
    }

    public void onRefresh(ActionEvent actionEvent) { //this method is called when user clicks the refresh button

        refreshTable(); //refreshes the table
    }
}
