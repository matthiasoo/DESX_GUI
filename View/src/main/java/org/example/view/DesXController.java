package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.model.DataEncryptionStandard;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DesXController {

    @FXML
    private TextField keyField1;

    @FXML
    private TextField keyField2;

    @FXML
    private TextField keyField3;

    @FXML
    private TextArea decodedTextArea;

    @FXML
    private TextArea encodedTextArea;

    @FXML
    private Button decryptedTextLoader;

    @FXML
    private Button decryptedTextSaver;

    @FXML
    private Button encryptedTextLoader;

    @FXML
    private Button encryptedTextSaver;

    @FXML
    private Button keyLoader;

    @FXML
    private Button keySaver;

    private double xxCord = 0;
    private double yyCord = 0;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void pressed(MouseEvent event) {
        xxCord = event.getSceneX();
        yyCord = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xxCord);
        stage.setY(event.getScreenY() - yyCord);
    }

    @FXML
    void encode(ActionEvent event) {
        String[] keys = {
                keyField1.getText(),
                keyField2.getText(),
                keyField3.getText()
        };
        DataEncryptionStandard d;

        if (this.fileCheck) {
            d = new DataEncryptionStandard(keys, this.decodedBytes);
        } else {
            String text = decodedTextArea.getText();
            d = new DataEncryptionStandard(keys, DataEncryptionStandard.stringToBytes(text));
        }

        this.encodedBytes = d.desXEncryption();
        encodedTextArea.setText(DataEncryptionStandard.bytesToHexString(this.encodedBytes));
    }

    private byte[] encodedBytes;
    private byte[] decodedBytes;

    @FXML
    void decode(ActionEvent event) {
        String[] keys = {
                keyField1.getText(),
                keyField2.getText(),
                keyField3.getText()
        };
        DataEncryptionStandard d;

        if (this.fileCheck) {
            d = new DataEncryptionStandard(keys, this.encodedBytes);
        } else {
            String text = encodedTextArea.getText();
            d = new DataEncryptionStandard(keys, DataEncryptionStandard.hexStringToBytes(text));
        }

        this.decodedBytes = d.desXDecryption();
        decodedTextArea.setText(DataEncryptionStandard.bytesToString(this.decodedBytes));
    }

    private boolean fileCheck = false;

    @FXML
    void fileChecker(ActionEvent event) {
        decodedTextArea.setEditable(fileCheck);
        encodedTextArea.setEditable(fileCheck);

        this.decodedBytes = new byte[]{};
        decodedTextArea.setText(DataEncryptionStandard.bytesToHexString(this.decodedBytes));
        this.encodedBytes = new byte[]{};
        encodedTextArea.setText(DataEncryptionStandard.bytesToHexString(this.encodedBytes));

        this.fileCheck = !this.fileCheck;
    }

    @FXML
    void generateKeys(ActionEvent event) {
        /*
        keyField1.setText(generateKey());
        keyField2.setText(generateKey());
        keyField3.setText(generateKey());
        */
        keyField1.setText("0022446688AACCEE");
        keyField2.setText("1133557799BBDDFF");
        keyField3.setText("0123456789ABCDEF");
    }

    @FXML
    void loadFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.*"));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                if (event.getSource() == decryptedTextLoader) {
                    this.decodedBytes = Files.readAllBytes(file.toPath());
                    String content = new String(this.decodedBytes);
                    decodedTextArea.setText(content);
                } else if (event.getSource() == encryptedTextLoader) {
                    this.encodedBytes = Files.readAllBytes(file.toPath());
                    StringBuilder hexString = new StringBuilder();
                    for (byte b : this.encodedBytes) {
                        hexString.append(String.format("%02X", b));
                    }
                    String content = hexString.toString();
                    encodedTextArea.setText(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.*"));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                if (event.getSource() == decryptedTextSaver) {
                    Files.write(file.toPath(), this.decodedBytes);

                } else if (event.getSource() == encryptedTextSaver) {
                    Files.write(file.toPath(), this.encodedBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void loadKeyFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hex Keys", "*.hexkey"));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                List<String> keys = Files.readAllLines(file.toPath());

                keyField1.setText(keys.get(0));
                keyField2.setText(keys.get(1));
                keyField3.setText(keys.get(2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveKeyToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hex Keys", "*.hexkey"));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                List<String> keys = new ArrayList<>();

                keys.add(keyField1.getText());
                keys.add(keyField2.getText());
                keys.add(keyField3.getText());

                Files.write(file.toPath(), keys, StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            key.append(Integer.toHexString(random.nextInt(16)).toUpperCase());
        }

        return key.toString();
    }
}
