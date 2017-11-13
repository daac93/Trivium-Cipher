package com.daac.crypto.trivium.utils;

import com.daac.crypto.trivium.app.Controller;
import com.daac.crypto.trivium.cypher.TriviumCypher;
import com.daac.crypto.trivium.exception.RegisterLoadException;
import com.daac.crypto.trivium.pojo.Register;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class TriviumCypherThread extends Task {

    private TriviumCypher triviumCypher;
    private int cypheringSpeed = 0;
    private String stringToCypher = "";
    private Controller appController;
    private String initVectorString = "";
    private Lock uiLock;

    public TriviumCypherThread(String initVectorString) {
        this.initVectorString = initVectorString;
        createTriviumCypher(initVectorString);

    }

    private void createTriviumCypher(String initVectorString)   {
        try {
            triviumCypher = new TriviumCypher(initVectorString);
        }   catch (RegisterLoadException rle)   {
            rle.printStackTrace();
        }
    }


    @Override
    public Void call()  {
        startEncryption();
        return null;
    }

    public void startEncryption() {
        byte[] stringAsByteArray = stringToCypher.getBytes(StandardCharsets.UTF_8);
        byte[] cypheredString = new byte[stringAsByteArray.length];

        for (int i = 0; i < stringAsByteArray.length; i++) {
            cypheredString[i] = triviumCypher.cypherByte(stringAsByteArray[i], cypheringSpeed);
        }

        appController.updateCypheredText(new String(cypheredString, StandardCharsets.UTF_8));
        appController.getBtnEncrypt().setDisable(false);

        startDecryption(cypheredString);
    }

    public void startDecryption(byte [] stringToDecypher)   {
        createTriviumCypher(initVectorString);
        byte [] stringAsByteArray = stringToDecypher;
        byte [] decypheredString = new byte[stringAsByteArray.length];

        for(int i = 0; i < stringAsByteArray.length; i++)   {
            decypheredString[i] = triviumCypher.cypherByte(stringAsByteArray[i]);
        }

        appController.updateDecryptedText(new String(decypheredString, StandardCharsets.UTF_8));
    }

   public Thread startUpdateDaemonTask() {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        appController.updateRegistersState(triviumCypher.getRegisters());
                    });
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        return th;
    }

    public List<Register> getRegisters()    {
        return triviumCypher.getRegisters();
    }

    public void setAppController(Controller appController) {
        this.appController = appController;
    }

    public void setCypheringSpeed(String cypheringSpeed) {
        switch(cypheringSpeed)  {
            case "Fast":
                this.cypheringSpeed = 0;
                break;
            case "Slow":
                this.cypheringSpeed = 3000;
                break;
        }
    }

    public void setStringToCypher(String stringToCypher) {
        this.stringToCypher = stringToCypher;
    }


    public void setUiLock(Lock uiLock) {
        this.uiLock = uiLock;
    }
}
