package com.beginsecure.hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HelloController {

    @FXML
    private Button clickButton;

    @FXML
    public void initialize() {
    
        clickButton.setOnAction(event -> {
    
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Button wurde geklickt!");
            alert.showAndWait();
        });
    }
}
