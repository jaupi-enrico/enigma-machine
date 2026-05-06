package com.enigma.enigmamachine;

import com.enigma.enigmamachine.model.EnigmaEngine;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

    private static final String STILE_ROTORE_TITOLO =
            "-fx-text-fill: #888888; " +
            "-fx-font-size: 12; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-font-weight: bold; " +
            "-fx-alignment: center; ";

    private static final String STILE_ROTORE_CORRENTE =
            "-fx-background-color: #25292A; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 16; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-font-weight: bold; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-border-width: 2; " +
            "-fx-border-color: #666666; " +
            "-fx-padding: 6; " +
            "-fx-min-width: 45; -fx-min-height: 45; " +
            "-fx-alignment: center; ";

    private static final String STILE_ROTORE_BOTTONE =
            "-fx-background-color: #1a1d1e; " +
            "-fx-text-fill: #888888; " +
            "-fx-font-size: 13; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-font-weight: bold; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-border-width: 1; " +
            "-fx-border-color: #444444; " +
            "-fx-padding: 4; " +
            "-fx-min-width: 38; -fx-min-height: 38; " +
            "-fx-alignment: center; " +
            "-fx-cursor: hand; ";

    private static final String STILE_ROTORE_BOTTONE_SOPRA =
            "-fx-background-color: #1a1d1e; " +
            "-fx-text-fill: #cccccc; " +
            "-fx-font-size: 15; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-font-weight: bold; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-border-width: 1; " +
            "-fx-border-color: #888888; " +
            "-fx-padding: 4; " +
            "-fx-min-width: 42; -fx-min-height: 42; " +
            "-fx-alignment: center; " +
            "-fx-cursor: hand; ";

    private static final String STILE_ROTORE_SCAMBIA =
            "-fx-background-color: #1a1d1e; " +
            "-fx-text-fill: #666666; " +
            "-fx-font-size: 18; " +
            "-fx-border-width: 1; " +
            "-fx-border-color: #444444; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 4 8; ";


    private static final String STILE_BOTTONE =
            "-fx-background-color: #25292A; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 16; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-border-width: 2; " +
            "-fx-border-color: #666666; " +
            "-fx-cursor: hand; " +
            "-fx-font-weight: bold; ";

    private static final String STILE_LUCE_OFF =
            "-fx-padding: 5; " +
            "-fx-background-color: #ffffff; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-text-fill: #000000; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-border-color: #555555; " +
            "-fx-border-width: 2; " +
            "-fx-alignment: center; ";

    private static final String STILE_LUCE_ON =
            "-fx-padding: 5; " +
            "-fx-background-color: yellow; " +
            "-fx-border-radius: 50; " +
            "-fx-background-radius: 50; " +
            "-fx-text-fill: #000000; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-border-color: #555555; " +
            "-fx-border-width: 2; " +
            "-fx-alignment: center; ";


    private static final String STILE_PLUG_BASE =
            "-fx-background-color: #000000; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 14; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-background-radius: 50; " +
            "-fx-cursor: hand; " +
            "-fx-font-weight: bold; ";

    private static final String STILE_PLUG_SELEZIONATO =
            "-fx-background-color: orange; " +
            "-fx-text-fill: #000000; " +
            "-fx-font-size: 14; " +
            "-fx-font-family: 'Courier New'; " +
            "-fx-background-radius: 50; " +
            "-fx-cursor: hand; " +
            "-fx-font-weight: bold; ";

    private String stilePlugColorato(String colore) {
            String s = "-fx-background-color: " + colore + "; " +
                    "-fx-text-fill: #000000; " +
                    "-fx-font-size: 14; " +
                    "-fx-font-family: 'Courier New'; " +
                    "-fx-background-radius: 50; " +
                    "-fx-cursor: hand; " +
                    "-fx-font-weight: bold; ";
            return s;
    }


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
            title.setStyle(STILE_ROTORE_TITOLO);

            Label next = new Label();
            next.setStyle(STILE_ROTORE_BOTTONE);

            Label curr = new Label();
            curr.setStyle(STILE_ROTORE_CORRENTE);

            Label prev = new Label();
            prev.setStyle(STILE_ROTORE_BOTTONE);

            rotoriLabels[i][0] = prev;
            rotoriLabels[i][1] = curr;
            rotoriLabels[i][2] = next;

            final int index = i;

            next.setOnMouseClicked(e -> { macchina.setPosizione(index,  1); updateLabelsRotori(); });
            prev.setOnMouseClicked(e -> { macchina.setPosizione(index, -1); updateLabelsRotori(); });

            next.setOnMouseEntered(e -> next.setStyle(STILE_ROTORE_BOTTONE_SOPRA));
            next.setOnMouseExited( e -> next.setStyle(STILE_ROTORE_BOTTONE));
            prev.setOnMouseEntered(e -> prev.setStyle(STILE_ROTORE_BOTTONE_SOPRA));
            prev.setOnMouseExited( e -> prev.setStyle(STILE_ROTORE_BOTTONE));

            GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
            GridPane.setHalignment(next,  javafx.geometry.HPos.CENTER);
            GridPane.setHalignment(curr,  javafx.geometry.HPos.CENTER);
            GridPane.setHalignment(prev,  javafx.geometry.HPos.CENTER);

            gridRotori.add(title, col, 0);
            gridRotori.add(next,  col, 1);
            gridRotori.add(curr,  col, 2);
            gridRotori.add(prev,  col, 3);

            if (i < n - 1) {
                Button swapBtn = new Button("⇄");
                swapBtn.setStyle(STILE_ROTORE_SCAMBIA);

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
            rotoriLabels[i][1].setText(String.valueOf(macchina.getPosizione(i,  0)));
            rotoriLabels[i][2].setText(String.valueOf(macchina.getPosizione(i,  1)));
        }
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
                buttons[index].setStyle(STILE_BOTTONE);
                buttons[index].setPrefSize(50, 50);
                buttons[index].setMaxSize(70, 70);
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
        gridLight.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index >= 26) return;

                labels[index] = new Label("" + lettera);
                labels[index].setPrefWidth(800.0 / 10);
                labels[index].setStyle(STILE_LUCE_OFF);
                labels[index].setPrefSize(50, 40);
                labels[index].setMaxSize(60, 50);
                labels[index].setMinSize(40, 30);

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

                plugButtons[index].setStyle(STILE_PLUG_BASE);
                plugButtons[index].setPrefSize(40, 40);
                plugButtons[index].setMaxSize(50, 50);
                plugButtons[index].setMinSize(30, 30);

                final char finalLettera = lettera;
                btn.setOnAction(e -> onPlugButtonClick(finalLettera));

                gridPlugBoard.add(btn, j, i, 1, 1);
                lettera++;
            }
        }
    }

    private void onPlugButtonClick(char lettera) {
        int index = lettera - 'A';

        if (macchina.haCoppia(lettera)) {
            char partner = macchina.getCoppia(lettera);
            macchina.rimuoviCoppia(lettera);

            plugButtons[index].setStyle(STILE_PLUG_BASE);
            plugButtons[partner - 'A'].setStyle(STILE_PLUG_BASE);

            if (primaLetteraSelezionata != null &&
                    (primaLetteraSelezionata == lettera || primaLetteraSelezionata == partner)) {
                primaLetteraSelezionata = null;
            }
            return;
        }

        if (primaLetteraSelezionata == null) {
            primaLetteraSelezionata = lettera;
            plugButtons[index].setStyle(STILE_PLUG_SELEZIONATO);
            return;
        }

        if (primaLetteraSelezionata == lettera) {
            primaLetteraSelezionata = null;
            plugButtons[index].setStyle(STILE_PLUG_BASE);
            return;
        }

        if (macchina.aggiungiCoppia(primaLetteraSelezionata, lettera)) {
            String colore = macchina.getColoreCoppia();
            plugButtons[primaLetteraSelezionata - 'A'].setStyle(stilePlugColorato(colore));
            plugButtons[index].setStyle(stilePlugColorato(colore));
        }
        primaLetteraSelezionata = null;
    }

    private void cifratura(char lettera) {
        char cifrata = macchina.cifraLettera(lettera);
        updateLabelsRotori();

        spegniTutteLeLuci();
        labels[cifrata - 'A'].setStyle(STILE_LUCE_ON);

        String inputPulito = inputTextArea.getText().replace(" ", "");
        if (!inputPulito.isEmpty() && inputPulito.length() % 5 == 0) {
            inputTextArea.appendText(" ");
            outputTextArea.appendText(" ");
        }

        inputTextArea.appendText(String.valueOf(lettera));
        outputTextArea.appendText(String.valueOf(cifrata));
    }

    private void spegniTutteLeLuci() {
        for (Label l : labels) {
            if (l != null) l.setStyle(STILE_LUCE_OFF);
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