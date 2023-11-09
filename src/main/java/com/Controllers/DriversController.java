package com.Controllers;

import com.Models.Driver;
import com.Utilities.Constants;
import com.Utilities.DriversManager;
import com.Utilities.Messages;
import com.Utilities.Resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class DriversController {
    public Button newDriverBtn;
    private ObservableList<Driver> drivers = FXCollections.observableArrayList();
    @FXML
    public Button searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    private TableView<Driver> driversTable;  //Drivers table

    public void loadTable() {
        driversTable.setItems(DriversManager.retrieveAllDrivers());
    }
    //loads the drivers in the drivers table using the drivers manager class retreiveAllDrivers

    public void initialize() {
        driversTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // It sets the column resize policy for the driversTable TableView to CONSTRAINED_RESIZE_POLICY,
        // which means that columns will be resized to fill the available width of the table.
        loadTable();  //loads the table with data

        TableColumn<Driver, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new TableCell<Driver, Void>() {  //each cell says delete in the
            // column
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    //when the button is clicked
                    Driver driver = getTableView().getItems().get(getIndex());
                    //The handler retrieves the driver object associated with the clicked cell
                    boolean result = Messages.confirmation("Confirm Deletion",
                            "Are you sure you want to delete this item?");
                    if (result) {
                        DriversManager.removeDriver(driver); //removes the driver from the system
                        loadTable();
                    }
                });
                deleteButton.prefWidthProperty().bind(deleteColumn.widthProperty());
                deleteButton.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        deleteColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Driver, Void> updateColumn = new TableColumn<>("Update");
        // creates a new column in the table with the header text "Update".
        updateColumn.setCellFactory(param -> new TableCell<Driver, Void>() {
            //creates custom cells with update button for each row
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {  //action on click
                    Driver driver = getTableView().getItems().get(getIndex());
                    // create a new screen and pass the selected race object to its constructor
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    //gets the stage of the current window to use for displaying the update screen.
                    FXMLLoader loader = Resources.getScreen("new-driver.fxml");//loads an fxml
                    // file to update the screen
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    NewDriverController controller = loader.getController(); //gets the controller for the
                    // update screen.
                    controller.setDriver(driver);// sets the selected driver in the update screen controller.
                    stage.setScene(scene); //sets the scene for the update screen on the current stage.
                    stage.show();
                });
                updateButton.prefWidthProperty().bind(updateColumn.widthProperty());
                updateButton.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) { //method to set the graphic for the cell
                super.updateItem(item, empty); // calls the superclass method for updating the item.

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
        updateColumn.setStyle("-fx-alignment: CENTER;");

        // add the delete and update columns to the table view
        driversTable.getColumns().addAll(updateColumn, deleteColumn);
    }

    public void onSearch(ActionEvent actionEvent) {
        String searchText = searchField.getText().toLowerCase(); //lower case for easy comparison
        if (searchText.isEmpty()) driversTable.setItems(DriversManager.retrieveAllDrivers());
        //If the search field is empty, display all the available drivers in a table. driversTable
        else driversTable.setItems(DriversManager.searchDrivers(searchText));
    }

    public void onDelete(ActionEvent actionEvent) { //to click delete
    }

    public void onUpdate(ActionEvent actionEvent) { //to click update
    }

    public void onNewDriverClick(ActionEvent actionEvent) {
        // create a new screen and pass the selected race object to its constructor
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = Resources.getScreen("new-driver.fxml");
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NewDriverController controller = loader.getController();
//        controller.setDriver();
        stage.setScene(scene);
        stage.show();
    }

    public void onRefresh(ActionEvent actionEvent) {
        searchField.setText(""); //Clear the search field.
        driversTable.setItems(DriversManager.retrieveAllDrivers());
    }
}
