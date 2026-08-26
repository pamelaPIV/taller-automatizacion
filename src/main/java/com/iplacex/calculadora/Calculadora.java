package com.iplacex.calculadora;

/**
 * Clase de negocio simple utilizada como base para
 * demostrar pruebas unitarias atómicas e independientes.
 */
public class Calculadora {

    /**
     * Suma dos números enteros.
     *
     * @param a primer sumando
     * @param b segundo sumando
     * @return resultado de la suma
     */
    public int sumar(int a, int b) {
        return a + b;
    }

    /**
     * Resta dos números enteros.
     *
     * @param a minuendo
     * @param b sustraendo
     * @return resultado de la resta
     */
    public int restar(int a, int b) {
        return a - b;
    }
}