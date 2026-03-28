package com.enigma.enigmamachine.model;

import java.util.HashMap;
import java.util.Map;



public class PlugBoard {


    private final Map<Character, Character> cablaggio;

    public PlugBoard() {
        this.cablaggio = new HashMap<>();
    }


    public void aggiungiCoppia(char a, char b) {
        a = Character.toUpperCase(a);
        b = Character.toUpperCase(b);

        if (!Character.isLetter(a) || !Character.isLetter(b)) {
            throw new IllegalArgumentException("Entrambi i caratteri devono essere lettere.");
        }
        if (a == b) {
            throw new IllegalArgumentException("Non puoi collegare una lettera a se stessa.");
        }
        if (cablaggio.containsKey(a)) {
            throw new IllegalArgumentException("La lettera '" + a + "' è già collegata.");
        }
        if (cablaggio.containsKey(b)) {
            throw new IllegalArgumentException("La lettera '" + b + "' è già collegata.");
        }

        cablaggio.put(a, b);
        cablaggio.put(b, a);
    }

    public void rimuoviCoppia(char a) {
        a = Character.toUpperCase(a);

        if (!cablaggio.containsKey(a)) {
            throw new IllegalArgumentException("La lettera '" + a + "' non è collegata.");
        }

        char b = cablaggio.get(a);
        cablaggio.remove(a);
        cablaggio.remove(b);
    }


    public void reimposta() {
        cablaggio.clear();
    }



    public char codifica(char c) {
        c = Character.toUpperCase(c);
        return cablaggio.getOrDefault(c, c);
    }


    @Override
    public String toString() {
        if (cablaggio.isEmpty()) {
            return "PlugBoard: nessuna coppia configurata";
        }

        StringBuilder sb = new StringBuilder("PlugBoard: ");
        boolean prima = true;

        for (Map.Entry<Character, Character> voce : cablaggio.entrySet()) {
            if (voce.getKey() < voce.getValue()) {
                if (!prima) sb.append(", ");
                sb.append(voce.getKey()).append("<->").append(voce.getValue());
                prima = false;
            }
        }

        return sb.toString();
    }


}