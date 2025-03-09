package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

    private double x = 0;
    private double y = 0;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void encode(ActionEvent event) {

    }

    @FXML
    void decode(ActionEvent event) {

    }

    @FXML
    void generateKeys(ActionEvent event) {
        keyField1.setText(generateKey());
        keyField2.setText(generateKey());
        keyField3.setText(generateKey());
    }

    @FXML
    void loadFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                String content = Files.readString(file.toPath());

                if (event.getSource() == decryptedTextLoader) {
                    decodedTextArea.setText(content);
                } else if (event.getSource() == encryptedTextLoader) {
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                if (event.getSource() == decryptedTextSaver) {
                    Files.writeString(file.toPath(), decodedTextArea.getText(), StandardOpenOption.CREATE);
                } else if (event.getSource() == encryptedTextSaver) {
                    Files.writeString(file.toPath(), encodedTextArea.getText(), StandardOpenOption.CREATE);
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
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

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
        File file = fileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());

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
