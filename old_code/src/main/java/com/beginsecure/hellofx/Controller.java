package com.beginsecure.hellofx;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Hyperlink;

public class Controller {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Hyperlink> linkList;

    @FXML
    private ListView<Hyperlink> socialLinks;


}