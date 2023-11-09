package com.Models;

import com.Utilities.Resources;
import javafx.scene.image.ImageView;

public class SideObject {
    private double speed;
    private double position;
    private final ImageView image;

    public SideObject(double speed, double position, String imageName) {
        //displays the objects image
        this.speed = speed;
        this.position = position;
        this.image = new ImageView(Resources.getImage(imageName));
        // load the image from the file and create the ImageView object.
    }

    //getter and setter methods for the speed and position instance variables.
    public double getSpeed() {
        return speed;
    }

    public double getPosition() {
        return position;
    }

    public ImageView getImage() {
        return image;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPosition(double position) {
        this.position = position;
    }
}
