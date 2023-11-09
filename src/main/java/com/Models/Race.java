package com.Models;

import com.Utilities.CarManager;
import com.Utilities.DriversManager;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Race {
    public final int[] trackLengths = {5, 10, 15, 20, 25}; //represent the lengths of the tracks that can be used in the race.
    //private instance variables of the Race class that define the state of the race.
    private final String name;  //all these variables store information about the race
    private final String location;
    private final Date date;
    private final double trackLength;
    private final LinkedList<Driver> drivers;
    AnimationTimer timer; //keep track of the animation timer
    private boolean isPause = false;
    private final LinkedList<Car> cars; //stores objects that are used in the race
    private LinkedList<SideObject> sideObjects;

    private LinkedList<Label> labels;

    public Race(String name, String location, Date date, com.Models.LinkedList<Driver> drivers) { //constructor for the race class
        //LinkedList of Driver objects representing the drivers participating in the race.
        this.name = name;
        this.location = location;
        this.date = date;
        this.drivers = drivers;
        this.trackLength = generateTrackLength(); //randomly generated value using the generateTrackLength()
        this.cars = new LinkedList<Car>();//creates a new empty LinkedList of Car objects and assigns it to
        // the cars instance variable
        initalizeTrees(); // initializes a LinkedList of SideObject objects representing trees along the side
        // of the racetrack.
    }

    public Race(String name, String location, Date date, com.Models.LinkedList<Driver> drivers, double trackLength) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.drivers = drivers;
        this.trackLength = trackLength;
        this.cars = new com.Models.LinkedList<Car>();
        initalizeTrees();
    }

    private double generateTrackLength() {  //generate random track length
        Random rand = new Random();
        int randomNum = rand.nextInt(5);
        return trackLengths[randomNum];
    }

    private void initalizeTrees() {  //side objects which are along the sidetrack
        sideObjects = new LinkedList<SideObject>();
        sideObjects.add(new SideObject(10, 110, "images/tree1.png"));
        sideObjects.add(new SideObject(10, 130, "images/tree2.png"));
        sideObjects.add(new SideObject(10, 300, "images/tree3.png"));
        sideObjects.add(new SideObject(10, 400, "images/tree4.png"));
        sideObjects.add(new SideObject(10, 500, "images/tree5.png"));
    }

    public LinkedList<Result> simulateRace(LinkedList<Driver> drivers) { //linked list of Result objects.
        LinkedList<Result> results = new LinkedList<Result>(); //A variable named results is defined and assigned an
        // empty linked list of Result objects.
        int place = 1; //variable will be used to determine the points earned by each driver based on their
        // finishing position.
        for (Driver driver : drivers) {
            int points = place == 1 ? 10 : place == 2 ? 7 : place == 3 ? 5 : 0; //points is defined and
            // assigned a value based on the current
            // value of the place variable
            driver.setPoints(driver.getPoints() + points); //setPoints method of the Driver object is called
            // to update the points of the current driver.
            results.add(new Result(driver.getName(), driver.getAge(), driver.getTeam(), driver.getCar(), points, place));
            //Result object is created and added to the results linked list using the add method
            place++; //place variable is incremented to update the finishing position for the next driver.
        }
        DriversManager.syncStorage();  //save any changes made to the drivers' points to persistent storage.
        return results; //output of the simulation.
    }

    public String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); //format the current date
        String formattedDate = sdf.format(date);
        return "race" + "_" + formattedDate + ".txt";
    }

    public String toCsvString() {  //appends values of various properties of the race, separated by commas.
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(location).append(",").append(date.getTime()).append(",").append(trackLength).append("\n");
        return sb.toString();
    }

    public static Race fromCsvString(String csvString) {
        String[] parts = csvString.split(",");  // splitting the input CSV string using commas as the
        // delimiter.
        String name = parts[0];
        String location = parts[1];
        Date date = new Date(Long.parseLong(parts[2]));
        double trackLength = Double.parseDouble(parts[3]);
        return new Race(name, location, date, null, trackLength);
        //Race object is created with the properties obtained from the CSV string,
    }

    public void startRace(Runnable onRaceFinish, ImageView finishLine, Pane randomRacePane, LinkedList<Car> cars) {
        int i = 0;
        double spacing = cars.size() == 5 ? 140 : cars.size() == 2 ? 320 : cars.size() == 3 ? 230 : 180;
        //space between cars depending on the number
        double leftSpace = cars.size() == 5 ? 340 : cars.size() == 2 ? 450 : cars.size() == 3 ? 395 : 365;
        for (Car car : cars) { //This line starts a loop that iterates through each Car object in the cars list.
            car.increaseSpeedGradually(); //calls the increaseSpeedGradually() method on each Car object in
            // the list.
            Label label = new Label(car.getName()); //new Label object with the Car object's name,
            //set its properties such as width, position, color, and alignment,
            label.setPrefWidth(150); //
            label.setLayoutY(car.getPosition() + 100);
            label.setTextFill(Color.WHITE);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font("Arial", 17));
//          label.setStyle("-fx-font-weight: bold");
            randomRacePane.getChildren().add(label); //add the properties to the randomRacePane
            car.setLabel(label);
            label.setLayoutX(leftSpace - 50 + spacing * i);
            i++; //variable to keep track of the index of the current car.
        }
        timer = new AnimationTimer() {
            private long lastTime = 0;  //used to update the position of each Car object and animate its
            // movement

            @Override
            public void handle(long currentTime) { //updates the position of each Car object in the race
                if (lastTime == 0) {
                    lastTime = currentTime;
                    return;
                }

                double elapsedTime = (currentTime - lastTime) / 1e9;
                // time elapsed since the last frame of the animation using the currentTime and
                // lastTime variables.
                lastTime = currentTime;  //It then updates lastTime to the current time.

                int i = 0;  // These variables are used to keep track of the position and speed of
                // the cars in the race.
                double maxSpeed = 0;

                for (Car car : cars) {
                    double newPosition;
                    if (i > 0) { // if i is greater than 0, it means that at least one car has crossed the
                        // finish line,
                        newPosition = car.getPosition() - maxSpeed * elapsedTime;
                        //calculates the new position of the current Car object based on its current position
                        // and speed
                        //as well as the maximum speed of all the cars in the race.
                        finishLine.setVisible(true); // finish line is made visible.
                    }
                    else {
                        newPosition = car.getPosition() - (car.getSpeed() / 4) * elapsedTime;
                    }
                    if (newPosition < 120) {
                        newPosition = 120;
                        i += 1; // This is done to ensure that all cars cross the finish line before the race
                        // is considered finished.
                    }
                    car.setPosition(newPosition);
                    car.getImage().setLayoutY(newPosition);
                    car.getLabel().setLayoutY(newPosition + 100);
                    if (car.getSpeed() > maxSpeed) maxSpeed = car.getSpeed();
                    //It also updates the maxSpeed variable if the current car's speed is greater
                    // than the current maximum speed.
                }

                for (SideObject sideObject : sideObjects) {
                    // this line starts a for loop that iterates through a collection of SideObject o
                    // bjects, which likely represent the trees in the sidetrack
                    if (i > 0) break;
                    //i is a counter that keeps track of the number of cars that have crossed the finish line.
                    double newPosition = sideObject.getPosition() + maxSpeed * elapsedTime * 20;// line
                    // calculates the new position of the SideObject based on the elapsed time since
                    // the last update and the maximum speed of the cars in the race.
                    if (newPosition > 570) newPosition = 120; //his line checks if the SideObject has
                    // gone off the end of the race track.
                    sideObject.setPosition(newPosition); //updates the SideObject's position
                    sideObject.getImage().setLayoutY(newPosition);//updates the SideObject's image position to
                    // match its new position.
                }

                if (i == cars.size()) { //if all  the cars have crossed the finish line.
                    labels = new LinkedList<Label>(); // new LinkedList object to hold Label objects associated
                    // with the cars.
                    for (Car car : cars) {
                        car.getLabel().setText(car.getName() + "\n" + car.getPlace());
                        //updates the Label associated with the current car with its name and finishing
                        // position.
                    }
                    //onRaceFinish.run();
                    timer.stop(); // this line stops the animation timer
                    isPause = true; //race is finished and should not be resumed.
                }
            }
        };
        timer.start();
        isPause = false;
    }

    public void pause() {//checks if the race is currently running

        if (!isPause) timer.stop(); //animation timer stops
    }

    public void resume() { //checks if the race is currently paused

        if (isPause) timer.start(); //animation timer started

    }

    public void finish() { //this is called when race is finished
        timer.stop(); //checks if the race is currently paused
        for (Car car : cars) {
            if (car.getTimeline() != null) {
                car.getTimeline().stop();
                if (car.getLabel() != null) car.getLabel().setVisible(false);
            }
        }
    }

    public LinkedList<Car> setCars(com.Models.LinkedList<Result> results) {
        //This method accepts a linked list of Result objects as input and returns a linked list of Car
        int i = 1;
        //creates a new Car object for each Result object, sets the name and place of the car
        // using the Result object's properties, and adds the new car to the cars linked list.
        for (Result result : results) {
            Car newCar = new Car(result.getName(), 10, 500, result.getPlace());
            cars.add(newCar);
            i++; //// The method then shuffles the cars list and returns it.
        }
        return CarManager.shuffle(cars);// The method then shuffles the cars list and returns it.
    }

    public com.Models.LinkedList<Car> getCars() {

        return cars; //returns the linked list of Car objects.
    }

    public String getName() {

        return name; //returns name of race
    }

    public String getLocation() {

        return location; //returns the location of the race.
    }

    public Date getDate() {

        return date; // method returns the date of the race.
    }

    public LinkedList<Driver> getDrivers() {

        return drivers; //returns the linked list of Driver objects.
    }

    public double getTrackLength() {

        return trackLength; // method returns the length of the track.
    }

    public boolean isPause() {

        return isPause;//method returns a boolean indicating whether the race is currently paused.
    }

    public LinkedList<SideObject> getSideObjects() {
        return sideObjects;
    } // This method returns the linked list of SideObject objects.
}
