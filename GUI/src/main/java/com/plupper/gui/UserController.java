package com.plupper.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class UserController {

    @FXML
    Text nameText;

    public void displayName (String username) {
        nameText.setText("Hello: " + username);
    }

}
