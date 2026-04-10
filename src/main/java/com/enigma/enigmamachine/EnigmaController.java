package com.enigma.enigmamachine;

import com.enigma.enigmamachine.model.EnigmaEngine;
import com.enigma.enigmamachine.model.PlugBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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


    @FXML
    void initialize() {
        macchina = new EnigmaEngine();
        creaGridButton();
        creaGridLight();
        creaGridPlugBoard();
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
        PlugBoard plugBoard = new PlugBoard();
        int index = lettera - 'A';

        // Se la lettera è già collegata, rimuovi la coppia
        char partner = plugBoard.codifica(lettera);
        if (partner != lettera) {
            // Decolora entrambi i bottoni della coppia
            plugButtons[index].setStyle("");
            plugButtons[partner - 'A'].setStyle("");
            plugBoard.rimuoviCoppia(lettera);
            // Se stava aspettando proprio questa come seconda scelta, annulla la selezione
            if (primaLetteraSelezionata != null && primaLetteraSelezionata == lettera) {
                primaLetteraSelezionata = null;
            }
            return;
        }

        // Nessuna lettera selezionata ancora → prima scelta
        if (primaLetteraSelezionata == null) {
            primaLetteraSelezionata = lettera;
            plugButtons[index].setStyle("-fx-background-color: orange;");
            return;
        }

        // Stessa lettera cliccata di nuovo → deseleziona
        if (primaLetteraSelezionata == lettera) {
            primaLetteraSelezionata = null;
            plugButtons[index].setStyle("");
            return;
        }

        // Seconda lettera scelta → crea la coppia
        boolean successo = plugBoard.aggiungiCoppia(primaLetteraSelezionata, lettera);
        if (successo) {
            plugButtons[primaLetteraSelezionata - 'A'].setStyle("-fx-background-color: lightblue;");
            plugButtons[index].setStyle("-fx-background-color: lightblue;");
        }
        // Se fallisce (non dovrebbe con i controlli sopra) non fa nulla
        primaLetteraSelezionata = null;
    }

    private void cifratura(char lettera) {
        char cifrata = macchina.cifraLettera(lettera); // adatta al metodo reale di EnigmaEngine
        int outputIndex = cifrata - 'A';
        // Accendi la label corrispondente alla lettera cifrata
        spegniTutteLeLuci();
        labels[outputIndex].setStyle("-fx-background-color: yellow;");
        System.out.println(lettera + " -> " + cifrata);
    }

    private void spegniTutteLeLuci() {
        for (Label l : labels) {
            if (l != null) l.setStyle("");
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().isLetterKey()) {
            // getCode().toString() restituisce il nome del tasto (es. "A"), sempre maiuscolo
            char lettera = keyEvent.getCode().toString().charAt(0);
            int pos = lettera - 'A';
            if (pos >= 0 && pos < 26) {
                buttons[pos].fire();
                buttons[pos].requestFocus();
            }
        }
    }
}