package com.enigma.enigmamachine.model;

import java.util.ArrayList;

public class EnigmaEngine {
    ArrayList<Rotor> rotori;
    Reflector reflector;
    public EnigmaEngine() {
        rotori = new ArrayList<>(3);

        rotori.add(Rotor.creaRotore1());
        rotori.add(Rotor.creaRotore2());
        rotori.add(Rotor.creaRotore3());
    }
}
