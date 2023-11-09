package com.Utilities;

import com.world_rally_cross_championship.WorldRallyCrossChampionship;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class Resources {
    public static Image getImage(String imageFileName) {
        return new Image(WorldRallyCrossChampionship.class.getResourceAsStream(imageFileName));
    }

    public static FXMLLoader getScreen(String screenFileName) {
        return new FXMLLoader(WorldRallyCrossChampionship.class.getResource(screenFileName));
    }
}
