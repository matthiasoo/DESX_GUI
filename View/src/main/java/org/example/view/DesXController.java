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
import org.example.model.DesException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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

    private byte[] encodedBytes = new byte[]{};
    private byte[] decodedBytes = new byte[]{};
    private boolean fileCheck = false;
    final int maxTextAreaLength = 1024;

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
    void showAuthors(ActionEvent event) throws IOException {
        MessageWindow.authorsWindow();
    }

    @FXML
    void encode(ActionEvent event) throws IOException {
        String[] keys = {
                keyField1.getText(),
                keyField2.getText(),
                keyField3.getText()
        };
        DataEncryptionStandard d;

        if (this.fileCheck) {
            if (this.decodedBytes.length == 0) {
                MessageWindow.errorMessageWindow("Empty buffer");
                return;
            }
            try {
                d = new DataEncryptionStandard(keys, this.decodedBytes);
            } catch (DesException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
                return;
            }
        } else {
            String text = decodedTextArea.getText();
            if (text == null) {
                MessageWindow.errorMessageWindow("Text is empty");
                return;
            }
            if (text.isEmpty()) {
                MessageWindow.errorMessageWindow("Text is empty");
                return;
            }
            try {
                d = new DataEncryptionStandard(keys, DataEncryptionStandard.stringToBytes(text));
            } catch (DesException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
                return;
            }
        }
        this.encodedBytes = d.desXEncryption();
        setTextArea(encodedTextArea, DataEncryptionStandard.bytesToHexString(this.encodedBytes));
    }

    @FXML
    void decode(ActionEvent event) throws IOException {
        String[] keys = {
                keyField1.getText(),
                keyField2.getText(),
                keyField3.getText()
        };
        DataEncryptionStandard d;

        if (this.fileCheck) {
            if (this.encodedBytes.length == 0) {
                MessageWindow.errorMessageWindow("Empty buffer");
                return;
            }
            try {
                d = new DataEncryptionStandard(keys, this.encodedBytes);
            } catch (DesException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
                return;
            }
        } else {
            String text = encodedTextArea.getText();
            if (text == null) {
                MessageWindow.errorMessageWindow("Text is empty");
                return;
            }
            if (text.isEmpty()) {
                MessageWindow.errorMessageWindow("Text is empty");
                return;
            }
            try {
                d = new DataEncryptionStandard(keys, DataEncryptionStandard.hexStringToBytes(text));
            } catch (DesException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
                return;
            }
        }

        this.decodedBytes = d.desXDecryption();
        setTextArea(decodedTextArea, DataEncryptionStandard.bytesToString(this.decodedBytes));
    }

    @FXML
    void fileChecker(ActionEvent event) {
        decodedTextArea.setEditable(fileCheck);
        encodedTextArea.setEditable(fileCheck);

        this.decodedBytes = new byte[]{};
        setTextArea(decodedTextArea, DataEncryptionStandard.bytesToHexString(this.decodedBytes));
        this.encodedBytes = new byte[]{};
        setTextArea(encodedTextArea, DataEncryptionStandard.bytesToHexString(this.encodedBytes));

        this.fileCheck = !this.fileCheck;
    }

    @FXML
    void generateKeys(ActionEvent event) {
        keyField1.setText(generateKey());
        keyField2.setText(generateKey());
        keyField3.setText(generateKey());
    }

    @FXML
    void loadFromFile(ActionEvent event) throws IOException {
        if (!fileCheck) {
            MessageWindow.errorMessageWindow("File checkbox not checked");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Text Files", event.getSource() == decryptedTextLoader ? "*.*" : "*.desx"
        ));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                if (event.getSource() == decryptedTextLoader) {
                    this.decodedBytes = Files.readAllBytes(file.toPath());
                    String content = new String(this.decodedBytes);
                    setTextArea(decodedTextArea, content);
                } else if (event.getSource() == encryptedTextLoader) {
                    if (Files.size(file.toPath()) % 8 != 0) {
                        MessageWindow.errorMessageWindow("Wrong encrypted file size");
                        return;
                    }
                    this.encodedBytes = Files.readAllBytes(file.toPath());
                    StringBuilder hexString = new StringBuilder();
                    for (byte b : this.encodedBytes) {
                        hexString.append(String.format("%02X", b));
                        if (hexString.length() > this.maxTextAreaLength / 2) {
                            break;
                        }
                    }
                    String content = hexString.toString();
                    setTextArea(encodedTextArea, content);
                }
            } catch (IOException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
            }
        }
    }

    @FXML
    void saveToFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Text Files", event.getSource() == decryptedTextSaver ? "*.*" : "*.desx"
        ));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                if (event.getSource() == decryptedTextSaver) {
                    Files.write(file.toPath(), this.decodedBytes);

                } else if (event.getSource() == encryptedTextSaver) {
                    Files.write(file.toPath(), this.encodedBytes);
                }
            } catch (IOException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
            }
        }
    }

    @FXML
    void loadKeyFromFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hex Keys", "*.hexkey"));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                List<String> keys = Files.readAllLines(file.toPath());

                if (keys.size() != 3) {
                    MessageWindow.errorMessageWindow("Incorrect number of keys in file");
                    return;
                }
                for (int i = 0; i < keys.size(); i++) {
                    if (keys.get(i).length() != 16) {
                        MessageWindow.errorMessageWindow("Length of key " + (i + 1) + " is incorrect");
                        return;
                    }
                }

                keyField1.setText(keys.get(0));
                keyField2.setText(keys.get(1));
                keyField3.setText(keys.get(2));
            } catch (IOException e) {
                MessageWindow.errorMessageWindow(e.getMessage());
            }
        }
    }

    @FXML
    void saveKeyToFile(ActionEvent event) throws IOException {
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
                MessageWindow.errorMessageWindow(e.getMessage());
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

    private void setTextArea(TextArea ta, String str) {
        if (str == null) {
            return;
        }
        if (str.length() > this.maxTextAreaLength) {
            str = str.substring(0, this.maxTextAreaLength);
        }
        ta.setText(str);
    }
}
