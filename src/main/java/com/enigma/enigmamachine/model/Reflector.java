package com.enigma.enigmamachine.model;

import java.util.HashMap;
import java.util.Map;

public class Reflector {

    // mappa del riflettore UKW_B: "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    // mappa del riflettore UKW-C: "FVPJIAOYEDRZXWGCTKUQSBNMHL"

    private Map<Character, Character> mappaSpecchio;
    private int sizeAlfabeto;

    public Reflector(String cablaggio){
        this.sizeAlfabeto = cablaggio.length();
        this.mappaSpecchio = new HashMap<>();

        for (int i = 0; i < sizeAlfabeto; i++) {
            char input = (char) ('A' + i);
            char output = cablaggio.charAt(i);

            mappaSpecchio.put(input, output);
        }
    }

    public char rifletti(char c){
        c = Character.toUpperCase(c);
        return mappaSpecchio.get(c);
    }

    static public Reflector creaUKW_B(){
        return new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");
    }

    static public Reflector creaUKW_C(){
        return new Reflector("FVPJIAOYEDRZXWGCTKUQSBNMHL");
    }
}
