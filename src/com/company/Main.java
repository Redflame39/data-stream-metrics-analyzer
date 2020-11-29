package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pattern p = Pattern.compile("\\s*if\\s*(.*).*");
        Matcher m = p.matcher("    if (x > y) {");
        System.out.println(m.find());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Анализатор Scala");
        primaryStage.setScene(new Scene(root, 664, 520));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/company/pic/scala-icon.png")));
        primaryStage.setMaxHeight(520);
        primaryStage.setMaxWidth(664);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
