package com.Controllers;

import com.Utilities.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Pane driversController;
    public Pane newDriverController;
    public BorderPane root;
    public TabPane tabPane;
    public Tab driversTab;

    @FXML
    public void initialize() {
//        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
//            if (newTab != null && newTab.getId().equals("raceHistoryTab")) {
//                FXMLLoader loader = Resources.getScreen("race-history.fxml");
//                try {
//                    Parent root = loader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                RaceHistoryController controller = loader.getController();
//                controller.refreshTable();
//            }
//        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
