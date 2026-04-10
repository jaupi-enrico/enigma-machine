package com.enigma.enigmamachine;

import com.enigma.enigmamachine.model.EnigmaEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class EnigmaController {
    EnigmaEngine macchina;

    @FXML
    private GridPane gridButton;
    @FXML
    private GridPane gridLight;
    @FXML
    private GridPane gridPlugBoard;

    private Button[] buttons;
    private Label[] labels;
    @FXML
    void initialize() {
        macchina = new EnigmaEngine();

        creaGridButton();
        creaGridPlugBoard();
    }

    private void creaGridButton(){
        //Crea il vettore di bottoni
        buttons = new Button[26];
        //Inizializza alla prima lettera, cioè la A
        char lettera = 'A';
        //Imposta le dimensioni dei separatori della griglia
        gridButton.setHgap(10);
        gridButton.setVgap(10);
        //Scorre ogni riga della tastiera
        for (int i = 0; i < 3; i++) {
            //Per ogni riga aggiunge 9 bottoni, fino ad arrivare alla Z
            for (int j = 0; j < 9; j++) {
                //Crea il nuovo bottone con la lettera corrispondente
                buttons[i * 9 + j] = new Button("" + lettera);
                //Stabilisce la dimensione del bottone in modo che occupi 1/10 dello spazio disponibile
                buttons[i * 9 + j].setPrefWidth(800.0 / 10);
                //Variabile finale per poter essere usata all'interno della lambda
                final char finalLettera = lettera;
                //Aggiunge un evento al bottone che stampa la lettera corrispondente quando viene premuto
                //Il bottone viene identificato tramite l'evento e la lettera viene stampata tramite la variabile finale
                //Nella versione definitiva, invece di stampare la lettera, verrà chiamato il metodo per criptare la lettera e visualizzare il risultato
                buttons[i * 9 + j].setOnAction(e -> {
                    System.out.println(e.getSource());
                    System.out.println(finalLettera);
                });
                //Aggiunge il bottone alla griglia nella posizione corrispondente
                gridButton.add(buttons[i * 9 + j], j, i, 1, 1);
                //Incrementa la lettera per passare alla successiva
                lettera++;
                //Se la lettera è arrivata alla Z, esce dal ciclo
                //Ovviamente si potrebbe anche aggiungere ad esempio il carattere per lo spazio
                //ma, per quanto detto, non verrebbe usato
                if (lettera == '[') return;
            }
        }
    }

    private void creaGridPlugBoard(){
        gridPlugBoard.setHgap(10);
        gridPlugBoard.setVgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {;
                //Variabile finale per poter essere usata all'interno della lambda
                final char finalLettera;
                //Aggiunge un evento al bottone che stampa la lettera corrispondente quando viene premuto
                //Il bottone viene identificato tramite l'evento e la lettera viene stampata tramite la variabile finale
                //Nella versione definitiva, invece di stampare la lettera, verrà chiamato il metodo per criptare la lettera e visualizzare il risultato
                buttons[i * 9 + j].setOnAction(e -> {

                });
                //Aggiunge il bottone alla griglia nella posizione corrispondente
                gridPlugBoard.add(buttons[i * 9 + j], j, i, 1, 1);
            }
        }
    }

    private void creaGridLight(){
        //Crea il vettore di bottoni
        labels = new Label[26];
        //Inizializza alla prima lettera, cioè la A
        char lettera = 'A';
        //Imposta le dimensioni dei separatori della griglia
        gridLight.setHgap(10);
        gridLight.setVgap(10);
        //Scorre ogni riga della tastiera
        for (int i = 0; i < 3; i++) {
            //Per ogni riga aggiunge 9 bottoni, fino ad arrivare alla Z
            for (int j = 0; j < 9; j++) {
                //Crea il nuovo bottone con la lettera corrispondente
                labels[i * 9 + j] = new Label("" + lettera);
                //Stabilisce la dimensione del bottone in modo che occupi 1/10 dello spazio disponibile
                labels[i * 9 + j].setPrefWidth(800.0 / 10);
                //Variabile finale per poter essere usata all'interno della lambda
                final char finalLettera = lettera;
                //Aggiunge il bottone alla griglia nella posizione corrispondente
                gridLight.add(labels[i * 9 + j], j, i, 1, 1);
                //Incrementa la lettera per passare alla successiva
                lettera++;
                //Se la lettera è arrivata alla Z, esce dal ciclo
                //Ovviamente si potrebbe anche aggiungere ad esempio il carattere per lo spazio
                //ma, per quanto detto, non verrebbe usato
                if (lettera == '[') return;
            }
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        //Controlla se il tasto premuto è una lettera
        if (keyEvent.getCode().isLetterKey()) {
            //Calcola l'indice nel vettore del bottone corrispondente alla lettera premuta
            int pos = (keyEvent.getCode().getChar().charAt(0) - 'A');
            //Simula la pressione del bottone corrispondente alla lettera premuta
            buttons[pos].fire();
            //Mette a fuoco il bottone corrispondente alla lettera premuta per evidenziarlo
            buttons[pos].requestFocus();
            //Stampa la posizione del bottone premuto (opzionale, per debug)
            System.out.println("" + pos);
        }
    }
}