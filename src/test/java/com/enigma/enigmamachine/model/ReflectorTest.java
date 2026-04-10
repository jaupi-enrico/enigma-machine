package com.enigma.enigmamachine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ReflectorTest {

    @Test
    void rifletti() {
        Reflector riflettore = Reflector.creaUKW_B();
        assertEquals('A', riflettore.rifletti(riflettore.rifletti('A')));
    }

    @Test
    void creaUKW_B() {
        Reflector riflettore = new Reflector("Y");
        assertEquals(riflettore.rifletti('A'), Reflector.creaUKW_B().rifletti('A'));
    }
}