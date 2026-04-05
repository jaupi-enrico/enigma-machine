package com.enigma.enigmamachine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RotorTest {

    @Test
    void cifratura_decifratura() {
        Rotor r = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q', 0, 0);

        for (char c = 'A'; c <= 'Z'; c++) {
            char enc = r.cifraAvanti(c);
            char dec = r.cifraIndietro(enc);

            assertEquals(c, dec, "Errore su carattere: " + c);
        }
    }

    @Test
    void cifratura_completa_reversibile() {
        Rotor r1 = Rotor.creaRotore1();
        Rotor r2 = Rotor.creaRotore2();
        Rotor r3 = Rotor.creaRotore3();
        //Riflessore

        String input = "CIAOTECHEGUARDIQUESTOCODICE";
        String cifrato = "";

        for (char c : input.toCharArray()) {

            boolean r1Avanzamento = r1.isLetteraAvanzamento();
            boolean r2Avanzamento = r2.isLetteraAvanzamento();

            if (r2Avanzamento) {
                r2.ruota();
                r3.ruota();
            } else if (r1Avanzamento) {
                r2.ruota();
            }

            r1.ruota();

            char x = r1.cifraAvanti(c);
            x = r2.cifraAvanti(x);
            x = r3.cifraAvanti(x);

            //riflessore

            x = r3.cifraIndietro(x);
            x = r2.cifraIndietro(x);
            x = r1.cifraIndietro(x);

            cifrato += x;
        }

        r1 = Rotor.creaRotore1();
        r2 = Rotor.creaRotore2();
        r3 = Rotor.creaRotore3();

        String decifrato = "";

        for (char c : cifrato.toCharArray()) {

            boolean r1Avanzamento = r1.isLetteraAvanzamento();
            boolean r2Avanzamento = r2.isLetteraAvanzamento();

            if (r2Avanzamento) {
                r2.ruota();
                r3.ruota();
            } else if (r1Avanzamento) {
                r2.ruota();
            }

            r1.ruota();

            char x = r1.cifraAvanti(c);
            x = r2.cifraAvanti(x);
            x = r3.cifraAvanti(x);

            //riflessore

            x = r3.cifraIndietro(x);
            x = r2.cifraIndietro(x);
            x = r1.cifraIndietro(x);

            decifrato += x;
        }

        assertEquals(input, decifrato);
    }
}