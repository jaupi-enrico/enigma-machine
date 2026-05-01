package com.enigma.enigmamachine.model;

import java.util.ArrayList;

public class EnigmaEngine {
    ArrayList<Rotor> rotori;
    Reflector reflector;
    PlugBoard plugBoard;
    int numeroCoppie;
    public EnigmaEngine() {
        rotori = new ArrayList<>(3);
        reflector = Reflector.creaUKW_B();
        plugBoard = new PlugBoard();
        numeroCoppie = 0;

        rotori.add(Rotor.creaRotore1());
        rotori.add(Rotor.creaRotore2());
        rotori.add(Rotor.creaRotore3());
    }

    public void swapRotori(int idxA, int idxB) {
        Rotor tmp = rotori.get(idxA);
        rotori.set(idxA, rotori.get(idxB));
        rotori.set(idxB, tmp);
    }

    public char cifraLettera(char letteraDaCifrare) {
        letteraDaCifrare = plugBoard.codifica(letteraDaCifrare);

        boolean r3Avanzamento = rotori.get(2).isLetteraAvanzamento();
        boolean r2Avanzamento = rotori.get(1).isLetteraAvanzamento();

        if (r2Avanzamento) {
            rotori.get(0).ruota();
        }

        if (r3Avanzamento || r2Avanzamento) {
            rotori.get(1).ruota();
        }

        rotori.get(2).ruota();

        for (int i = rotori.size() - 1; i >= 0; i--) {
            letteraDaCifrare = rotori.get(i).cifraAvanti(letteraDaCifrare);
        }
        letteraDaCifrare = reflector.rifletti(letteraDaCifrare);

        for (int i = 0; i < rotori.size(); i++) {
            letteraDaCifrare = rotori.get(i).cifraIndietro(letteraDaCifrare);
        }

        return letteraDaCifrare;
    }

    public void setPosizione(int indiceRotore, int delta) {
        rotori.get(indiceRotore).setPosizioneAttuale(rotori.get(indiceRotore).getPosizioneAttuale() + delta);
    }

    public void setIndiceRing(int indiceRotore, char lettera) {
        rotori.get(indiceRotore).setIndiceRing(lettera - 'A');
    }

    public char getPosizione(int indiceRotore, int delta) {
        return (char) (
                ((rotori.get(indiceRotore).getPosizioneAttuale() + delta) % rotori.get(indiceRotore).getSizeAlfabeto()
                        + rotori.get(indiceRotore).getSizeAlfabeto()) % rotori.get(indiceRotore).getSizeAlfabeto() + 'A');
    }

    public char getIndiceRing(int indiceRotore) {
        return (char) (rotori.get(indiceRotore).getIndiceRing() + 'A');
    }

    public int getSizeRotori() {
        return rotori.size();
    }

    public boolean aggiungiCoppia(char letteraIdx, char lettera) {
        return plugBoard.aggiungiCoppia(letteraIdx, lettera);
    }

    public boolean rimuoviCoppia(char lettera) {
        return plugBoard.rimuoviCoppia(lettera);
    }

    public char getCoppia(char lettera) {
        return plugBoard.codifica(lettera);
    }

    public String getColoreCoppia() {
        String colore;
        switch (numeroCoppie) {
            case 0  -> { colore = "lightblue"; }
            case 1  -> { colore = "#FF6B6B"; }
            case 2  -> { colore = "#FF9F43"; }
            case 3  -> { colore = "#FFEAA7"; }
            case 4  -> { colore = "#A8E6CF"; }
            case 5  -> { colore = "#6BCB77"; }
            case 6  -> { colore = "#4D96FF"; }
            case 7  -> { colore = "#845EC2"; }
            case 8  -> { colore = "#FF85A1"; }
            case 9  -> { colore = "#00C9A7"; }
            case 10 -> { colore = "#F9C74F"; }
            case 11 -> { colore = "#F4845F"; }
            case 12 -> { colore = "#90E0EF"; }
            case 13 -> { colore = "#B5E48C"; }
            default -> { colore = ""; }
        }
        numeroCoppie = (numeroCoppie + 1) % 14;
        return colore;
    }
}
