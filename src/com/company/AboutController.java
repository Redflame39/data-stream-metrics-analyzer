package com.company;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView img;

    @FXML
    void initialize() {
        img.setImage(new Image(getClass().getResource("/com/company/pic/dakak.png").toExternalForm()));
    }
}
