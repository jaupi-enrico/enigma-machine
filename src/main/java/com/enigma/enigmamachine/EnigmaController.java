package com.enigma.enigmamachine;

import com.enigma.enigmamachine.model.EnigmaEngine;
import com.enigma.enigmamachine.model.PlugBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class EnigmaController {
    EnigmaEngine macchina;

    @FXML private GridPane gridButton;
    @FXML private GridPane gridLight;
    @FXML private GridPane gridPlugBoard;

    private Button[] buttons;
    private Label[] labels;
    private Button[] plugButtons;
    private Character primaLetteraSelezionata = null;

    @FXML private Label nextRotore1;
    @FXML private Label nextRotore2;
    @FXML private Label nextRotore3;
    @FXML private Label attualeRotore1;
    @FXML private Label attualeRotore2;
    @FXML private Label attualeRotore3;
    @FXML private Label previousRotore1;
    @FXML private Label previousRotore2;
    @FXML private Label previousRotore3;

    @FXML private TextArea inputTextArea;
    @FXML private TextArea outputTextArea;

    @FXML
    void initialize() {
        macchina = new EnigmaEngine();
        creaGridButton();
        creaGridLight();
        creaGridPlugBoard();
        updateLabelsRotori();
    }

    private void creaGridButton() {
        buttons = new Button[26];
        char lettera = 'A';
        gridButton.setHgap(10);
        gridButton.setVgap(10);
        gridButton.getColumnConstraints().clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                buttons[index] = new Button("" + lettera);
                buttons[index].setPrefWidth(800.0 / 10);

                buttons[index].setStyle("-fx-background-color: #25292A; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-font-size: 12; " +
                        "-fx-font-family: 'Courier new'; " +
                        "-fx-border-radius: 50; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-color: #666666; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-weight: bold; ");
                buttons[index].setPrefSize(40, 40);
                buttons[index].setMaxSize(40, 40);
                buttons[index].setMinSize(40, 40);

                final char finalLettera = lettera;
                buttons[index].setOnAction(e -> cifratura(finalLettera));

                gridButton.add(buttons[index], j, i, 1, 1);
                lettera++;
            }
        }
    }

    private void creaGridLight() {
        labels = new Label[26];
        char lettera = 'A';
        gridLight.setHgap(10);
        gridLight.setVgap(10);
        gridLight.getColumnConstraints().clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                labels[index] = new Label("" + lettera);
                labels[index].setPrefWidth(800.0 / 10);

                labels[index].setStyle("-fx-padding: 5; " +
                        "-fx-background-color: #ffffff; " +
                        "-fx-border-radius: 50; " +
                        "-fx-background-radius: 50; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12; " +
                        "-fx-font-family: 'Courier New'; " +
                        "-fx-border-color: #555555; " +
                        "-fx-border-width: 2; ");

                gridLight.add(labels[index], j, i, 1, 1);
                lettera++;
            }
        }
    }

    private void creaGridPlugBoard() {
        plugButtons = new Button[26];

        gridPlugBoard.setHgap(10);
        gridPlugBoard.setVgap(10);
        gridPlugBoard.getColumnConstraints().clear();

        char lettera = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                Button btn = new Button("" + lettera);
                btn.setPrefWidth(800.0 / 10);
                plugButtons[index] = btn;

                final char finalLettera = lettera;
                btn.setOnAction(e -> onPlugButtonClick(finalLettera));

                gridPlugBoard.add(btn, j, i, 1, 1);
                lettera++;
            }
        }
    }

    private void onPlugButtonClick(char lettera) {
        int index = lettera - 'A';

        char partner = macchina.getCoppia(lettera);
        if (partner != lettera) {
            plugButtons[index].setStyle("");
            plugButtons[partner - 'A'].setStyle("");
            macchina.rimuoviCoppia(lettera);
            macchina.rimuoviCoppia(partner);
            if (primaLetteraSelezionata != null && primaLetteraSelezionata == lettera) {
                primaLetteraSelezionata = null;
            }
            return;
        }

        if (primaLetteraSelezionata == null) {
            primaLetteraSelezionata = lettera;
            plugButtons[index].setStyle("-fx-background-color: orange;");
            return;
        }

        if (primaLetteraSelezionata == lettera) {
            primaLetteraSelezionata = null;
            plugButtons[index].setStyle("");
            return;
        }

        boolean successo = macchina.aggiungiCoppia(primaLetteraSelezionata, lettera);
        if (successo) {
            String colore = macchina.getColoreCoppia();
            plugButtons[primaLetteraSelezionata - 'A'].setStyle("-fx-background-color: " + colore + ";");
            plugButtons[index].setStyle("-fx-background-color: " + colore + ";");
        }
        primaLetteraSelezionata = null;
    }

    private void cifratura(char lettera) {
        char cifrata = macchina.cifraLettera(lettera);

        int outputIndex = cifrata - 'A';
        spegniTutteLeLuci();
        labels[outputIndex].setStyle("-fx-padding: 5; " +
                "-fx-background-color: yellow; " +
                "-fx-border-radius: 50; " +
                "-fx-background-radius: 50; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12; " +
                "-fx-font-family: 'Courier New'; " +
                "-fx-border-color: #555555; " +
                "-fx-border-width: 2; ");
        if (inputTextArea.getText().replace(" ", "").length() % 5 == 0 && !inputTextArea.getText().isEmpty()) {
            inputTextArea.appendText(" ");
            outputTextArea.appendText(" ");
        }
        inputTextArea.appendText(String.valueOf(lettera));
        outputTextArea.appendText(String.valueOf(cifrata));
    }

    private void spegniTutteLeLuci() {
        for (Label l : labels) {
            if (l != null) l.setStyle("-fx-padding: 5; " +
                    "-fx-background-color: #ffffff; " +
                    "-fx-border-radius: 50; " +
                    "-fx-background-radius: 50; " +
                    "-fx-text-fill: #000000; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 12; " +
                    "-fx-font-family: 'Courier New'; " +
                    "-fx-border-color: #555555; " +
                    "-fx-border-width: 2; ");
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().isLetterKey()) {
            char lettera = keyEvent.getCode().toString().charAt(0);
            int pos = lettera - 'A';
            if (pos >= 0 && pos < 26) {
                buttons[pos].fire();
                buttons[pos].requestFocus();
            }
        }
    }

    private void updateLabelsRotori() {
        attualeRotore1.setText(String.valueOf(macchina.getPosizione(0, 0)));
        attualeRotore2.setText(String.valueOf(macchina.getPosizione(1, 0)));
        attualeRotore3.setText(String.valueOf(macchina.getPosizione(2, 0)));
        nextRotore1.setText(String.valueOf(macchina.getPosizione(0, 1)));
        nextRotore2.setText(String.valueOf(macchina.getPosizione(1, 1)));
        nextRotore3.setText(String.valueOf(macchina.getPosizione(2, 1)));
        previousRotore1.setText(String.valueOf(macchina.getPosizione(0, -1)));
        previousRotore2.setText(String.valueOf(macchina.getPosizione(1, -1)));
        previousRotore3.setText(String.valueOf(macchina.getPosizione(2, -1)));
    }

    public void nextPosRotore1() {
        macchina.setPosizione(0, 1);
        updateLabelsRotori();
    }
    public void nextPosRotore2() {
        macchina.setPosizione(1, 1);
        updateLabelsRotori();
    }
    public void nextPosRotore3() {
        macchina.setPosizione(2, 1);
        updateLabelsRotori();
    }
    public void previousPosRotore1() {
        macchina.setPosizione(0, -1);
        updateLabelsRotori();
    }
    public void previousPosRotore2() {
        macchina.setPosizione(1, -1);
        updateLabelsRotori();
    }
    public void previousPosRotore3() {
        macchina.setPosizione(2, -1);
        updateLabelsRotori();
    }
}