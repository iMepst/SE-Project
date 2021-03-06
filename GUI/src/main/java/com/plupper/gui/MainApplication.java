package com.plupper.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("start-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage.setTitle("Plupper");

            //Set icon of stage
            stage.getIcons().add(new Image("https://ibb.co/CmKkLL3"));

            stage.setResizable(true);
            stage.setMinHeight(720);
            stage.setMinWidth(1280);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}