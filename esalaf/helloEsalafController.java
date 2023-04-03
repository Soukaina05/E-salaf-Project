package com.example.esalaf;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class helloEsalafController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onCliButtonClick() {
            try {
                // Load the FXML file for the new interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                // Create a new stage to display the new interface
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Hello E-salaf");

                // Show the new interface
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void onProButtonClick() {
        try {
            // Load the FXML file for the new interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showProducts.fxml"));
            Parent root = loader.load();

            // Create a new stage to display the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hello E-salaf");

            // Show the new interface
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onCreditButtonClick() {
        try {
            // Load the FXML file for the new interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("showCredit.fxml"));
            Parent root = loader.load();

            // Create a new stage to display the new interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hello E-salaf");

            // Show the new interface
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
