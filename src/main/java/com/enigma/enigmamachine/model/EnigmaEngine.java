package com.enigma.enigmamachine.model;

import java.util.ArrayList;

public class EnigmaEngine {
    ArrayList<Rotor> rotori;
    Reflector reflector;
    public EnigmaEngine() {
        rotori = new ArrayList<>(3);
        reflector = Reflector.creaUKW_B();

        rotori.add(Rotor.creaRotore1());
        rotori.add(Rotor.creaRotore2());
        rotori.add(Rotor.creaRotore3());
    }

    public EnigmaEngine(Rotor r1, Rotor r2, Rotor r3) {
        rotori = new ArrayList<>(3);
        reflector = Reflector.creaUKW_B();

        rotori.add(r1);
        rotori.add(r2);
        rotori.add(r3);
    }

    public char cifraLettera(char letteraDaCifrare) {
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

}
