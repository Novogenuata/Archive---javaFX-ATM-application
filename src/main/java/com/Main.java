package com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//créé par : sigourney wamback + Hountondji Orphéric Montel
//24 décembre 2023
//pour se connecter à l'administrateur, tapez "admin admin"
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}