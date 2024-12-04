package com.beginsecure.hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/beginsecure/hellofx/example.fxml"));
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("JavaFX Anwendung ohne WebView");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
