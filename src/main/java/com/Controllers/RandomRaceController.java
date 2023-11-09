package com.Controllers;

import com.Models.*;
import com.Utilities.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.Date;

public class RandomRaceController {
    public TextField raceNameField; //text field for entering the race name.
    public TextField raceLocationField;  //text field for entering the race location.
    public Button startBtn; //button to start the race
    public TableView<Result> resultsTable; //display the results after the race
    public Label resultsLabel; //table view to represent the results
    public Pane randomRacePane;// pane containing race components
    public ImageView trackImageView; //represents the image view to display the race track.
    public TableView driversTable; //represents the table view to display the list of drivers.
    public Button finishBtn; //button to finish the race
    public Label countLabel; //represents the label to display the countdown.

    private LinkedList<ImageView> carImageViews;//private LinkedList of ImageView instances
    // named carImageViews that represents the list of car image views.
    private LinkedList<ImageView> treeImageViews; //represents the list of tree image views.
    private ImageView finishLine;//represents the image view for the finish line.

    private LinkedList<Driver> selectedDrivers; //represents the list of selected drivers.
    private TableColumn<Driver, Boolean> checkboxColumn; // table column for the driver
    // selection checkbox.


    @FXML
    public void initialize() {
        resultsTable.setVisible(false);
        resultsLabel.setVisible(false);
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //columns resize to fill the available space.
        trackImageView.setImage(Constants.Images.TRACK_2);
        trackImageView.setPreserveRatio(false);
        trackImageView.setVisible(false);
        finishBtn.setVisible(false);
        selectedDrivers = new LinkedList<Driver>(); // hold instances of Driver.
        countLabel.setTextFill(Color.RED); //set the text fill, visibility, text alignment,
        // and text of countLabel.
        countLabel.setVisible(false);
        countLabel.setTextAlignment(TextAlignment.CENTER);
        countLabel.setText("");

        driversTable.setItems(DriversManager.retrieveAllDrivers());
        //ets the items of driversTable to a list of drivers retrieved using the
        // retrieveAllDrivers() method of the DriversManager class.
        driversTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        checkboxColumn = new TableColumn<>();
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn)); //checkbox column
        checkboxColumn.setText("Select");
        checkboxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        checkboxColumn.setCellFactory(column -> new TableCell<Driver, Boolean>() {
            private final CheckBox checkBox = new CheckBox();
            {
                checkBox.setOnAction(event -> {
                    if (selectedDrivers.size() >= Constants.MAX_DRIVERS_PER_RACE && checkBox.isSelected()) {
                        checkBox.setSelected(false);
                        Messages.warning("Maximum limit reached", "You can select only up to " + Constants.MAX_DRIVERS_PER_RACE + " drivers!");
                        return;
                    }
                    Driver driver = getTableView().getItems().get(getIndex());
                    driver.setSelected(checkBox.isSelected());
                    if (checkBox.isSelected()) selectedDrivers.add(driver);
                    else selectedDrivers.remove(driver);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });
        driversTable.getColumns().add(checkboxColumn);

    }

    public void onStart(ActionEvent actionEvent) {
        String btnText = startBtn.getText();
        if (btnText.equals("Start")) {
            String name = raceNameField.getText();//get the text in the raceNameField and store
            // in name variable
            String location = raceLocationField.getText(); //gets the text of the
            // raceLocationField and store in name variable

            if (name.equals("")) { //checks if name is empty
                Messages.error("Invalid Name", "Please enter a valid name!");
                return;
            }

            if (location.equals("")) { // checks if location is empty
                Messages.error("Invalid Location", "Please enter a valid location!");
                return;
            }

            if (selectedDrivers.size() < 2) {//checks amount of drivers
                Messages.error("Insufficient Drivers", "Please select at least two drivers!");
                return;
            }

            Race newRace = new Race(name, location, new Date(), DriverSerialization.load());
            //create a new Race object using the name, location, the current date, and a
            // list of Driver objects loaded using the DriverSerialization class
            LinkedList<Result> results = newRace.simulateRace(selectedDrivers);
            //method is called on the newRace object, passing in the selectedDrivers linked list.
            LinkedList<Car> cars = newRace.setCars(results);
            //his method returns a linked list of Result objects, which are then used to
            // create a linked list of Car objects using the setCars method on the newRace object.
            displayRace(newRace, cars, results);
            startBtn.setText("Processing...");
            driversTable.setVisible(false); //hides the drivers table
        } else if (btnText.equals("Reset")) {
            startBtn.setText("Start");
            resultsTable.setVisible(false);
            resultsLabel.setVisible(false);
            raceNameField.setText("");
            raceLocationField.setText("");
            trackImageView.setVisible(false);
            driversTable.setVisible(true);
            DriversManager.resetSelectedStatus();
            driversTable.setItems(DriversManager.retrieveAllDrivers());
            driversTable.refresh();
        }
    }

    public void displayRace(Race race, LinkedList<Car> cars, LinkedList<Result> results) {
        finishLine = new ImageView(Constants.Images.FINISH_LINE);
        finishLine.setFitWidth(850);
        finishLine.setFitHeight(40);
        finishLine.setLayoutX(210);
        finishLine.setLayoutY(80);
        finishLine.setVisible(false);
        carImageViews = new LinkedList<ImageView>(); //the car objects
        trackImageView.setVisible(true);
        finishBtn.setOnAction(e -> {  //action button when the button is clicked race ends
            onRaceEnd(race, results);
            race.finish();
        });

        randomRacePane.getChildren().add(finishLine);

        int i = 0;
        double spacing = cars.size() == 5 ? 140 : cars.size() == 2 ? 320 : cars.size() == 3 ? 230 : 180;
        //space between cars depending on number
        double leftSpace = cars.size() == 5 ? 340 : cars.size() == 2 ? 450 : cars.size() == 3 ? 395 : 365;
        //space from the car to the side track depending on number of cars.
        for(Car car : cars) {
            ImageView carImageView = car.getImage();
            carImageView.setLayoutX(leftSpace + spacing * i);
            carImageView.setLayoutY(700);
            randomRacePane.getChildren().add(carImageView);
            carImageViews.add(carImageView);
            i++;
        }

        i = 1;
        for(SideObject sideObject : race.getSideObjects()) { //sets the position of the trees along
            //the side track
            ImageView treeImageView = sideObject.getImage();
            if (i % 2 == 1) treeImageView.setLayoutX(20); //sets x coordinate of image to 20
            else treeImageView.setLayoutX(1150); //or else sets to 1150
            treeImageView.setLayoutY(sideObject.getPosition());
            //Sets the Y coordinate of the image to the position property of the SideObject.
            randomRacePane.getChildren().add(treeImageView);
            //Adds the treeImageView to the randomRacePane container.
            carImageViews.add(treeImageView);//Adds the treeImageView to the carImageViews list.
            i++;
        }

        finishBtn.toFront();

        Runnable onRaceFinish = () -> { //defines what should happen when the race finishes.
            Platform.runLater(() -> {
                onRaceEnd(race, results);
            });
        };
        countLabel.setVisible(true); //makes count label visible
        startRace(race, onRaceFinish, cars); //calls startRace method passing race object
    }

    public void onRaceEnd(Race race, LinkedList<Result> results) {
        RaceManager.addRace(race, results); //dds the current Race object and its Result objects to the RaceManager.
        resultsTable.setVisible(true); //in this the results table will be visible
        resultsLabel.setVisible(true);
        trackImageView.setVisible(false); //And the racetrack will not be visible
        resultsTable.setItems(ResultsManager.transformToObservableList(results));
        //This line sets the items of the resultsTable to an observable list generated
        // by the ResultsManager.
        if (carImageViews != null) for (ImageView carImageView : carImageViews) carImageView.setVisible(false);
        startBtn.setText("Reset");
        finishBtn.setVisible(false);
        if (finishBtn != null) finishLine.setVisible(false); //finish button will not be visible
        selectedDrivers = new LinkedList<Driver>();
    }

    public void startRace(Race race, Runnable onRaceFinish, LinkedList<Car> cars) {
        countLabel.toFront(); //makes the countlabel visible
        Timeline timeline = new Timeline( //to create the animation
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    int count = 0;
                    @Override
                    public void handle(ActionEvent event) {
                        count++;
                        countLabel.setText(Integer.toString(count));
                        //Sets the text of the countLabel to the current value of the counter variable.
                        if (count == 4) countLabel.setText("GO"); //so after 3 the 4th will be GO
                        if (count == 5) {
                            race.startRace(onRaceFinish, finishLine, randomRacePane, cars);
                            //calls the startRace method on race object passing these objects
                            countLabel.setVisible(false);//count label will be not visible
                            countLabel.setText("");
                            finishBtn.setVisible(true); //finish button will be visible
                        }
                    }
                })
        );
        timeline.setCycleCount(5);
        timeline.play();
        //lays the Timeline, which will execute the event handler every second for 5 seconds.
    }
}
// Runnable onRaceFinish, ImageView finishLine, Pane randomRacePane
