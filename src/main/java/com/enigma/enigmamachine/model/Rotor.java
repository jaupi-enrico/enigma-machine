package com.enigma.enigmamachine.model;

import java.util.HashMap;
import java.util.Map;

public class Rotor {
    private Map<Character, Character> mappaDavanti;
    private Map<Character, Character> mappaIndietro;
    private int sizeAlfabeto;
    private char letteraAvanzamento;
    private int posizioneAttuale;

    public Rotor(String cablaggio, char letteraAvanzamento, int posizione) {
        this.letteraAvanzamento = letteraAvanzamento;
        this.sizeAlfabeto  = cablaggio.length();
        setPosizioneAttuale(posizione);

        mappaDavanti = new HashMap<>();
        mappaIndietro = new HashMap<>();

        for (int i = 0; i < sizeAlfabeto; i++) {
            char input = (char) ('A' + i);
            char output = cablaggio.charAt(i);

            mappaDavanti.put(input, output);
            mappaIndietro.put(output, input);
        }
    }

    public void ruota() {
        posizioneAttuale = (posizioneAttuale + 1) % sizeAlfabeto;
    }

    public boolean isLetteraAvanzamento() {
        return (char) ('A' + posizioneAttuale) == letteraAvanzamento;
    }

    public char cifraAvanti(char c) {
        c = Character.toUpperCase(c);
        int spostamento = (c - 'A' + posizioneAttuale) % sizeAlfabeto;
        char carattereSpostato = (char) ('A' + spostamento);

        Character carattereMappato = mappaDavanti.get(carattereSpostato);
        if (carattereMappato == null) {
            throw new IllegalArgumentException("Carattere non valido: " + c);
        }

        int output = (carattereMappato - 'A' - posizioneAttuale + sizeAlfabeto) % sizeAlfabeto;

        return (char) ('A' + output);
    }

    public char cifraIndietro(char c) {
        c = Character.toUpperCase(c);

        int spostamento = (c - 'A' + posizioneAttuale) % sizeAlfabeto;
        char carattereSpostato = (char) ('A' + spostamento);

        Character carattereMappato = mappaIndietro.get(carattereSpostato);
        if (carattereMappato == null) {
            throw new IllegalArgumentException("Carattere non valido: " + c);
        }

        int output = (carattereMappato - 'A' - posizioneAttuale + sizeAlfabeto) % sizeAlfabeto;

        return (char) ('A' + output);
    }

    public int getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public void setPosizioneAttuale(int posizioneAttuale) {
        this.posizioneAttuale = (posizioneAttuale % sizeAlfabeto + sizeAlfabeto) % sizeAlfabeto;
    }

    static public Rotor creaRotore1(int pos) {
        return  new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q', pos);
    }

    static public Rotor creaRotore2(int pos) {
        return  new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E', pos);
    }

    static public Rotor creaRotore3(int pos) {
        return  new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V', pos);
    }
}