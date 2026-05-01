# Enigma M3

JavaFX simulation of the Enigma M3 cipher machine.

## Features

- 3 rotors (I, II, III) with historically accurate wirings and notch stepping
- Double-stepping anomaly
- UKW-B reflector
- Plugboard (Steckerbrett) — up to 13 pairs
- Keyboard input and mouse input
- Rotor position control via UI

## Requirements

- Java 17+
- JavaFX 17+
- Maven

## Usage

1. Set rotor positions using the arrows in the rotor panel
2. Optionally connect letter pairs in the plugboard
3. Type on keyboard or click the buttons to encrypt
4. The lit lamp shows the encrypted letter
5. Input/output appear in the text areas below, grouped in blocks of 5

## Notes

- Swapping rotors changes their order, not their internal position
- Clicking an active plugboard pair removes it
- The machine is symmetric: encrypting ciphertext with the same settings returns plaintext
