package com.enigma.enigmamachine;

import com.enigma.enigmamachine.model.EnigmaEngine;
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
    @FXML private GridPane gridRotori;


    private Button[] buttons;
    private Label[] labels;
    private Button[] plugButtons;
    private Character primaLetteraSelezionata = null;
    private Label[][] rotoriLabels;



    @FXML private TextArea inputTextArea;
    @FXML private TextArea outputTextArea;

    @FXML
    void initialize() {
        macchina = new EnigmaEngine();
        creaGridButton();
        creaGridLight();
        creaGridPlugBoard();
        creaViewRotori();
        updateLabelsRotori();
    }

    private void creaViewRotori() {
        int n = macchina.getSizeRotori();
        rotoriLabels = new Label[n][3];

        gridRotori.getChildren().clear();
        gridRotori.getColumnConstraints().clear();

        for (int i = 0; i < n; i++) {
            int col = i * 2;

            Label title = new Label("ROTOR " + (i + 1));

            Label next = new Label();

            Label curr = new Label();

            Label prev = new Label();

            rotoriLabels[i][0] = prev;
            rotoriLabels[i][1] = curr;
            rotoriLabels[i][2] = next;

            final int index = i;

            next.setOnMouseClicked(e -> { macchina.setPosizione(index, 1);  updateLabelsRotori(); });
            prev.setOnMouseClicked(e -> { macchina.setPosizione(index, -1); updateLabelsRotori(); });

            gridRotori.add(title, col, 0);
            gridRotori.add(next,  col, 1);
            gridRotori.add(curr,  col, 2);
            gridRotori.add(prev,  col, 3);

            if (i < n - 1) {
                Button swapBtn = new Button("⇄");

                final int idxA = i;
                swapBtn.setOnAction(e -> {
                    macchina.swapRotori(idxA, idxA + 1);
                    updateLabelsRotori();
                });

                GridPane.setHalignment(swapBtn, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(swapBtn, javafx.geometry.VPos.CENTER);
                gridRotori.add(swapBtn, col + 1, 2);
            }
        }
    }

    private void updateLabelsRotori() {
        for (int i = 0; i < macchina.getSizeRotori(); i++) {
            rotoriLabels[i][0].setText(String.valueOf(macchina.getPosizione(i, -1)));
            rotoriLabels[i][1].setText(String.valueOf(macchina.getPosizione(i, 0)));
            rotoriLabels[i][2].setText(String.valueOf(macchina.getPosizione(i, 1)));
        }
    }

    private void creaGridButton() {
        buttons = new Button[26];
        char lettera = 'A';
        gridButton.setHgap(10);
        gridButton.setVgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                buttons[index] = new Button("" + lettera);
                buttons[index].setPrefWidth(800.0 / 10);

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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                labels[index] = new Label("" + lettera);
                labels[index].setPrefWidth(800.0 / 10);
                gridLight.add(labels[index], j, i, 1, 1);
                lettera++;
            }
        }
    }

    private void creaGridPlugBoard() {
        plugButtons = new Button[26];

        gridPlugBoard.setHgap(10);
        gridPlugBoard.setVgap(10);

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
        updateLabelsRotori();

        int outputIndex = cifrata - 'A';
        spegniTutteLeLuci();
        labels[outputIndex].setStyle("-fx-background-color: yellow;");
        if (inputTextArea.getText().replace(" ", "").length() % 5 == 0 && !inputTextArea.getText().isEmpty()) {
            inputTextArea.appendText(" ");
            outputTextArea.appendText(" ");
        }
        inputTextArea.appendText(String.valueOf(lettera));
        outputTextArea.appendText(String.valueOf(cifrata));
    }

    private void spegniTutteLeLuci() {
        for (Label l : labels) {
            if (l != null) l.setStyle("");
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
}