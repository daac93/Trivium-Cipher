package com.daac.crypto.trivium.app;

import com.aquafx_project.AquaFx;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class TriviumFxApp extends Application {

    //Encrypt Tab Objects
    //Configuration
    @FXML
    public TextField encrypt_initVector;
    @FXML
    public TextField encrypt_SecretKey;
    @FXML
    public ChoiceBox encrypt_speed;

    @FXML
    public TextArea textToEncrypt;
    @FXML
    public Button btnEncrypt;

    //RegisterObjects
    @FXML
    public GridPane registerOneGridPane;
    @FXML
    public GridPane registerTwoGridPane;
    @FXML
    public GridPane registerThreeGridPane;

    //Decrypt Tab Objects
    @FXML
    public TextField decrypt_initVector;
    @FXML
    public TextArea textToDecrypt;
    @FXML
    public TextField decrypt_SecretKey;
    @FXML
    public TextArea decryptedText;


    @Override
    public void start(Stage primaryStage)   {
        primaryStage.setTitle("Trivium Cypher Tool");
        primaryStage.setMaximized(true);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream in = getClass().getResourceAsStream("/Trivium.fxml");
            Parent root = fxmlLoader.load(in);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            AquaFx.style();

            primaryStage.show();

        }   catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String [] args) {
        Application.launch(args);
    }

}
