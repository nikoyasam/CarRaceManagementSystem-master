package com.Models;

import com.world_rally_cross_championship.WorldRallyCrossChampionship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Car {
    private final String[] imageNames = new String[] {"images/car1.png", "images/car2.png", "images/car3.png", "images/car4.png", "images/car5.png"};
    //declares image names for the cars
    private final String name; //instance variables for the car object
    private double speed;
    private double position;
    private final ImageView image;

    private final String imageName; //variables for image name and the place of the car

    private final int place;

    private Timeline timeline;//variable for the animation timeline and its label

    private Label label;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Car(String name, double speed, double position, int place) { //constructor for car class
        String image = generateCarImage();
        this.name = name;
        this.speed = speed;
        this.position = position;
        this.image = new ImageView(new Image(WorldRallyCrossChampionship.class.getResourceAsStream(image)));
        this.imageName = image;
        this.place = place;
    }

    private String generateCarImage() { //This method generates a random car image from the imageNames array
        // using a Random object.
        Random rand = new Random();
        int randomNum = rand.nextInt(5);
        return imageNames[randomNum];
    }

    public String getName() {
        return name;
    } //getter methods for the car's name, speed, position, and image.

    public double getSpeed() {
        return speed;
    }

    public double getPosition() {
        return position;
    }

    public ImageView getImage() {
        return image;
    }

    public void setPosition(double position) { //setter methods for the car's position and speed.
        this.position = position;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getImageName() {
        return imageName;
    }

    public void accelerate(double acceleration) {
        speed += acceleration;
    }
    //This method increases the car's speed by a certain acceleration value.

    public void decelerate(double deceleration) {
        //This method decreases the car's speed by a certain deceleration value, and sets it to 0 if
        // it goes below that.
        speed -= deceleration;
        if (speed < 0) {
            speed = 0;
        }
    }

    public static LinkedList<Car> shuffle(LinkedList<Car> cars) {
        //This is a static method that shuffles a list of car objects randomly and returns the shuffled list.
        List<Car> carList = new ArrayList<>();// carList, which is an ArrayList of Car objects,
        LinkedList<Car> shuffledCarList = new LinkedList<Car>();//LinkedList of Car objects.
        for (Car car : cars) carList.add(car);//iterates over each Car object in the original cars list and
        // adds each car to the carList ArrayList.
        Collections.shuffle(carList);//randomly shuffle the cars
        for (int i = 0; i < carList.size(); i++) shuffledCarList.add(carList.get(i));
        //iterate over each element in the shuffled carList ArrayList and add it to the shuffledCarList LinkedList.
        return shuffledCarList;
    }

    public int getPlace() {
        return place;
    } //returns the current position of the car in the race

    public void increaseSpeedGradually() { //gradually increases the speed of a car over a set period of time.
        timeline = new Timeline(
                new KeyFrame(Duration.millis(100 * place), new EventHandler<ActionEvent>() {
                    //specifies how often to update the speed of the car and by how much
                    @Override
                    public void handle(ActionEvent event) {
                        if (speed <= 150) setSpeed(speed + 5);
                    }
                    //speed is increased by 5 every 100 milliseconds multiplied by the car's place variable
                })
        );
        timeline.setCycleCount(25); //sets the number of cycles to run the animation
        timeline.play();//method called to start the animation
    }

    public Timeline getTimeline() {// returns the timeline object that is used to gradually increase
        // the car's speed.

        return timeline;
    }
}
