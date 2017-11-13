package com.daac.crypto.trivium.app;

import com.daac.crypto.trivium.pojo.Register;
import com.daac.crypto.trivium.ui.UIRegister;
import com.daac.crypto.trivium.utils.TriviumCypherThread;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Initializable {

    //Encrypt Tab Objects
    //Configuration
    public TextField encrypt_initVector;
    public TextField encrypt_SecretKey;
    public ChoiceBox encrypt_speed;


    public TextArea textToEncrypt;
    public TextArea encryptedText;
    public Button btnEncrypt;

    //RegisterObjects
    public GridPane registerOneGridPane;
    public GridPane registerTwoGridPane;
    public GridPane registerThreeGridPane;

    //Decrypt Tab Objects
    public TextField decrypt_initVector;
    public TextArea textToDecrypt;
    public TextArea decryptedText;
    public Button btnDecrypt;


    UIRegister r1 = new UIRegister(93);
    UIRegister r2 = new UIRegister(84);
    UIRegister r3 = new UIRegister(111);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        r1.buildRegisterPane(registerOneGridPane, 2, 47, "UIRegister One");
        r2.buildRegisterPane(registerTwoGridPane, 2, 42, "UIRegister Two");
        r3.buildRegisterPane(registerThreeGridPane, 3, 38, "UIRegister Three");

        encrypt_speed.getItems().addAll("Slow", "Fast");
        encrypt_speed.getSelectionModel().selectFirst();
    }


    public void startEncryption() {
        String initVector = RandomStringUtils.randomAlphanumeric(20);
        String text = textToEncrypt.getText();
        String encryptionSpeed = (String) encrypt_speed.getSelectionModel().getSelectedItem();

        encrypt_initVector.setText(initVector);

        Lock uiLock = new ReentrantLock();

        TriviumCypherThread triviumCypherThread = new TriviumCypherThread(initVector);
        triviumCypherThread.setCypheringSpeed(encryptionSpeed);
        triviumCypherThread.setAppController(this);
        triviumCypherThread.setStringToCypher(text);


        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!triviumCypherThread.isDone()) {
                    if(uiLock.tryLock()) {
                        Platform.runLater(() -> {
                            updateRegistersState(triviumCypherThread.getRegisters());
                        });
                        uiLock.unlock();
                    }
                }
                return null;
            }
        };

        triviumCypherThread.setUiLock(uiLock);

        new Thread(triviumCypherThread, "Trivium Cypher Thread").start();
        Thread th = new Thread(task);
        th.start();




    }


    public void startDecryption(MouseEvent mouseEvent) {
        String initVector = encrypt_initVector.getText();
        String text = textToDecrypt.getText();
        /*triviumCypherThread = new TriviumCypherThread(initVector);
        triviumCypherThread.setAppController(this);
        triviumCypherThread.setStringToCypher(text);*/

        //triviumCypherThread.startDecryption();
    }

    public void updateRegistersState(List<Register> registers)   {
        r1.updateRegisterPane(registers.get(0));
        r2.updateRegisterPane(registers.get(1));
        r3.updateRegisterPane(registers.get(2));
    }

    public void updateCypheredText(String textToSet)    {
        encryptedText.setText(textToSet);
    }

    public void updateDecryptedText(String textToSet)   {
        decryptedText.setText(textToSet);
    }

    public Button getBtnEncrypt() {
        return btnEncrypt;
    }

    public Button getBtnDecrypt() {
        return btnDecrypt;
    }
}
