package com.daac.crypto.trivium.app;

import com.aquafx_project.AquaFx;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        /*AnchorPane rootPane = new AnchorPane();

        VBox elementsContainer = new VBox();

        UIRegister r1 = new UIRegister(93);
        UIRegister r2 = new UIRegister(84);
        UIRegister r3 = new UIRegister(111);


        HBox configurationBox = new HBox();
        Label labelInitVector = new Label("Init Vector: ");
        configurationBox.getChildren().add(labelInitVector);
        TextField inputInitVector = new TextField();
        configurationBox.getChildren().add(inputInitVector);
        Button buttonStartEncryption = new Button("Start Encryption");
        configurationBox.getChildren().add(buttonStartEncryption);

        elementsContainer.getChildren().add(configurationBox);
        elementsContainer.getChildren().add(new Separator());


        VBox registersBox = new VBox();
        registersBox.getChildren().add(r1.buildRegisterPane(2, 47, "UIRegister One"));
        registersBox.getChildren().add(new Separator());
        registersBox.getChildren().add(r2.buildRegisterPane(2, 42, "UIRegister Two"));
        registersBox.getChildren().add(new Separator());
        registersBox.getChildren().add(r3.buildRegisterPane(2, 56, "UIRegister Three"));

        elementsContainer.getChildren().add(registersBox);


        rootPane.getChildren().addAll(elementsContainer);

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("css/styles.css");
        primaryStage.setScene(scene);



        primaryStage.show();*/
    }

    public static void main(String [] args) {
        Application.launch(args);
    }

    private static GridPane createRegisterGridPane(int registerSize)    {
        GridPane newGridPane = new GridPane();

        Label [] titles = new Label [registerSize];
        Label [] registerValues = new Label[registerSize];

        for(int i = 0; i < registerSize; i++)   {
            titles[i] = new Label(String.valueOf(i + 1));
            registerValues[i] = new Label("0");
            newGridPane.add(titles[i], i, 0, 1, 1);
            newGridPane.add(registerValues[i], i, 1, 1, 1);
        }

        return newGridPane;
    }
}
