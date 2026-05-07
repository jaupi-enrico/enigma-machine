package com.enigma.enigmamachine.model;

import java.util.HashMap;
import java.util.Map;

public class PlugBoard {

    private final Map<Character, Character> cablaggio = new HashMap<>();


    public boolean aggiungiCoppia(char a, char b) {
        a = Character.toUpperCase(a);
        b = Character.toUpperCase(b);

        if (a == b)
            return false;
        if (cablaggio.containsKey(a))
            return false;
        if (cablaggio.containsKey(b))
            return false;

        cablaggio.put(a, b);
        cablaggio.put(b, a);
        return true;
    }


    public boolean rimuoviCoppia(char a) {
        a = Character.toUpperCase(a);
        if (!cablaggio.containsKey(a)) return false;

        char b = cablaggio.remove(a);
        cablaggio.remove(b);
        return true;
    }

    public char codifica(char c) {
        c = Character.toUpperCase(c);
        return cablaggio.getOrDefault(c, c);
    }

    public boolean haCoppia(char a) {
        return cablaggio.containsKey(Character.toUpperCase(a));
    }
}