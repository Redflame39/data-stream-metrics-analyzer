package com.company;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    public static int totalOperators = 0;
    public static int totalOperands = 0;

    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textfield;

    @FXML
    private Button analyze_button;

    @FXML
    private Button load_button;

    @FXML
    private Button clear_button;

    @FXML
    private Button about_button;

    @FXML
    private ImageView background;

    Stage primaryStage;

    @FXML
    void initialize()  {
        Image image = new Image(getClass().getResource("/com/company/pic/background.png").toExternalForm());
        background.setImage(image);
    }

    @FXML
    void about_action(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("about.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void load_action(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            loadFrom(file, textfield);
        }
    }

    @FXML
    void analyze_action() throws IOException {
        Metrics.code.addAll(textfield.getText().split("\n"));
        Metrics.calculateMetrics();

        for (Spen s : Metrics.spenList) {
            System.out.println(s.identifier + s.count);
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("analyze.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Метрики потока данных");
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/com/company/pic/scala-icon.png")));
        stage.showAndWait();
    }

    @FXML
    void clear_action(ActionEvent event) {
        textfield.clear();
    }

    private void loadFrom(File file, TextArea textfield) throws IOException {
        if (file == null) {
            return;
        }
        FileReader fr = new FileReader(file);
        int c;
        textfield.clear();
        while ((c = fr.read()) != -1) {
            textfield.appendText(String.valueOf((char) c));
        }
    }

}
