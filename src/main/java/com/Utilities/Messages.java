package com.Utilities;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class Messages {
    public static void information(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setIcon(alert, "images/car1.png", null);
        alert.showAndWait();
    }

    public static void error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setIcon(alert, "images/car1.png", null);
        alert.showAndWait();
    }

    public static void warning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setIcon(alert, "images/car1.png", null);
        alert.showAndWait();
    }

    public static boolean confirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        setIcon(alert, "images/car1.png", null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void setIcon(Alert alert, String headerIcon, String icon) {
        DialogPane dialogPane = alert.getDialogPane();
        if (icon != null) dialogPane.setGraphic(new ImageView(Resources.getImage(icon)));

        if (headerIcon != null) {
            Node headerNode = dialogPane.lookup(".header-panel .header");
            if (headerNode instanceof Label) {
                Label headerLabel = (Label) headerNode;
                headerLabel.setGraphic(new ImageView(Resources.getImage(headerIcon)));
            }
        }
    }
}
