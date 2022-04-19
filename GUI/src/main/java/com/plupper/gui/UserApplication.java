package com.plupper.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UserApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Plupper");

        //Set icon of stage
        stage.getIcons().add(new Image("https://miro.com/app/board/uXjVO-zad6k=/?moveToWidget=3458764523192728281&cot=14"));

        stage.setResizable(true);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);

        stage.setScene(scene);
        stage.show();
    }
}
