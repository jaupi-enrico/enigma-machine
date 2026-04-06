package com.enigma.enigmamachine.model;

import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;

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
        Reflector riflessore = Reflector.creaUKW_B();

        String input = "CIAOTECHEGUARDIQUESTOCODICE";
        String cifrato = "";

        for (char c : input.toCharArray()) {

            boolean r3Avanzamento = r3.isLetteraAvanzamento(); // fast
            boolean r2Avanzamento = r2.isLetteraAvanzamento(); // middle

            if (r2Avanzamento) {
                r1.ruota();
            }

            if (r3Avanzamento || r2Avanzamento) {
                r2.ruota();
            }

            r3.ruota();

            System.out.println((char) (r1.getPosizioneAttuale() + 'A') + "" + (char) (r2.getPosizioneAttuale() + 'A') + "" + (char) (r3.getPosizioneAttuale() + 'A'));


            char x = r3.cifraAvanti(c);
            System.out.println(x);
            x = r2.cifraAvanti(x);
            System.out.println(x);
            x = r1.cifraAvanti(x);
            System.out.println(x);

            x = riflessore.rifletti(x);
            System.out.println(x);

            x = r1.cifraIndietro(x);
            System.out.println(x);
            x = r2.cifraIndietro(x);
            System.out.println(x);
            x = r3.cifraIndietro(x);
            System.out.println(x);

            cifrato += x;
        }

        r1 = Rotor.creaRotore1();
        r2 = Rotor.creaRotore2();
        r3 = Rotor.creaRotore3();

        String decifrato = "";

        for (char c : cifrato.toCharArray()) {
            boolean r2Avanzamento = r3.isLetteraAvanzamento();
            boolean r1Avanzamento = r2.isLetteraAvanzamento();

            if (r1Avanzamento && r2Avanzamento) {
                r1.ruota();
            }

            if (r2Avanzamento) {
                r2.ruota();
            }

            r3.ruota();

            char x = r1.cifraAvanti(c);
            x = r2.cifraAvanti(x);
            x = r3.cifraAvanti(x);

            x = riflessore.rifletti(x);

            x = r3.cifraIndietro(x);
            x = r2.cifraIndietro(x);
            x = r1.cifraIndietro(x);

            decifrato += x;
        }

        assertEquals(input, decifrato);
    }
}