package com.enigma.enigmamachine.model;

import java.util.HashMap;
import java.util.Map;

public class Rotor {
    private Map<Character, Character> mappaDavanti;
    private Map<Character, Character> mappaIndietro;
    private int sizeAlfabeto;
    private char letteraAvanzamento;
    private int posizioneAttuale;
    private int indiceRing;

    public Rotor(String cablaggio, char letteraAvanzamento, int posizione, int indiceRing) {
        this.letteraAvanzamento = letteraAvanzamento;
        this.sizeAlfabeto = cablaggio.length();
        setIndiceRing(indiceRing);

        mappaDavanti = new HashMap<>();
        mappaIndietro = new HashMap<>();

        for (int i = 0; i < sizeAlfabeto; i++) {
            char input = (char) ('A' + i);
            char output = cablaggio.charAt(i);

            mappaDavanti.put(input, output);
            mappaIndietro.put(output, input);
        }

        setPosizioneAttuale(posizione);
    }

    public void ruota() {
        posizioneAttuale = (posizioneAttuale + 1) % sizeAlfabeto;
    }

    public boolean isLetteraAvanzamento() {
        int pos = (posizioneAttuale - indiceRing + sizeAlfabeto) % sizeAlfabeto;
        return (char) ('A' + pos) == letteraAvanzamento;
    }

    public char cifraAvanti(char c) {
        c = Character.toUpperCase(c);

        int spostamento = (c - 'A' + posizioneAttuale - indiceRing + sizeAlfabeto) % sizeAlfabeto;
        char carattereSpostato = (char) ('A' + spostamento);

        Character carattereMappato = mappaDavanti.get(carattereSpostato);
        if (carattereMappato == null) {
            throw new IllegalArgumentException("Carattere non valido: " + c);
        }

        int output = (carattereMappato - 'A' - posizioneAttuale + indiceRing + sizeAlfabeto) % sizeAlfabeto;

        return (char) ('A' + output);
    }

    public char cifraIndietro(char c) {
        c = Character.toUpperCase(c);

        int spostamento = (c - 'A' + posizioneAttuale - indiceRing + sizeAlfabeto) % sizeAlfabeto;
        char carattereSpostato = (char) ('A' + spostamento);

        Character carattereMappato = mappaIndietro.get(carattereSpostato);
        if (carattereMappato == null) {
            throw new IllegalArgumentException("Carattere non valido: " + c);
        }

        int output = (carattereMappato - 'A' - posizioneAttuale + indiceRing + sizeAlfabeto) % sizeAlfabeto;

        return (char) ('A' + output);
    }

    public int getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public void setPosizioneAttuale(int posizioneAttuale) {
        this.posizioneAttuale = (posizioneAttuale % sizeAlfabeto + sizeAlfabeto) % sizeAlfabeto;
    }

    public void setIndiceRing(int indiceRing) {
        this.indiceRing = (indiceRing % sizeAlfabeto + sizeAlfabeto) % sizeAlfabeto;
    }

    public int getIndiceRing() {
        return indiceRing;
    }

    public int getSizeAlfabeto() {
        return sizeAlfabeto;
    }

    static public Rotor creaRotore1() {
        return new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q', 0, 0);
    }

    static public Rotor creaRotore2() {
        return new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E', 0, 0);
    }

    static public Rotor creaRotore3() {
        return new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V', 0, 0);
    }
}