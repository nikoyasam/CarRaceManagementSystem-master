package com.Controllers;

import com.Models.Driver;
import com.Utilities.DriversManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StandingsController {
    private ObservableList<Driver> drivers = FXCollections.observableArrayList();
    //creates an ObservableList of Driver objects to hold the drivers' standings.
    @FXML
    public Button searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    private TableView<Driver> driversTable;

    public void initialize() {
        driversTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //allows the table to automatically adjust its columns' width to fit the table's width.
        driversTable.setItems(DriversManager.retrieveAllStandings());
        //sets the table's items to the list of drivers' standings retrieved from the DriversManager.
    }

    public void onSearch(ActionEvent actionEvent) {
        //retrieves the text entered in search field
        String searchText = searchField.getText().toLowerCase();// converts it to lowercase
        if (searchText.isEmpty()) driversTable.setItems(DriversManager.retrieveAllStandings());
        //passes it to the DriversManager to retrieve a filtered list of drivers' standings
        else driversTable.setItems(DriversManager.searchStandings(searchText));
        //retrieve the full list of drivers' standings.
    }

    public void onRefresh(ActionEvent actionEvent) {
        searchField.setText("");//clears the search field
        driversTable.setItems(DriversManager.retrieveAllStandings());
        //refreshes the driversTable with the full list of drivers' standings retrieved
        // from the DriversManager.
    }
}
