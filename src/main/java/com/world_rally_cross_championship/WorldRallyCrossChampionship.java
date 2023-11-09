package com.world_rally_cross_championship;

import com.Utilities.Constants;
import com.Utilities.Resources;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WorldRallyCrossChampionship extends Application {

    private Scene scene;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.getIcons().add(Constants.Images.CAR_1);
        scene = new Scene(Resources.getScreen("sign-in.fxml").load());
//      Scene scene = new Scene(Resources.getScreen("home.fxml").load());
        this.stage.setTitle(Constants.TITLE);
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setResizable(false);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}