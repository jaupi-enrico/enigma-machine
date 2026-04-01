package com.enigma.enigmamachine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RotorTest {

    @Test
    void cifratura_decifratura() {
        Rotor r = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q', 0);

        for (char c = 'A'; c <= 'Z'; c++) {
            char enc = r.cifraAvanti(c);
            char dec = r.cifraIndietro(enc);

            assertEquals(c, dec, "Errore su carattere: " + c);
        }
    }
}