package com.Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public interface Constants {
    String TITLE = "World Rally Cross Championship Management System";
    int MAX_DRIVERS_PER_RACE = 5;
    interface Images{
        Image CAR_1 = Resources.getImage("images/car1.png");
        Image CAR_2 = Resources.getImage("images/car2.png");
        Image CAR_3 = Resources.getImage("images/car3.png");
        Image CAR_4 = Resources.getImage("images/car4.png");
        Image CAR_5 = Resources.getImage("images/car5.png");
        Image TRACK_1 = Resources.getImage("images/track.png");
        Image TRACK_2 = Resources.getImage("images/trackEdited.png");
        Image FINISH_LINE = Resources.getImage("images/finishLine.jpg");
    }

    interface Credentials {
        String USERNAME = "username";
        String PASSWORD = "password";
    }
}
