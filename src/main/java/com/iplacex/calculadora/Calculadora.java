package com.iplacex.calculadora;

/**
 * Clase de negocio simple utilizada como base para
 * demostrar pruebas unitarias atómicas e independientes,
 * y escenarios de comportamiento (BDD) con Cucumber.
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

    /**
     * Divide dos números enteros.
     *
     * Criterio de aceptación (definido en sesión Three Amigos):
     * Si el divisor es cero, se debe lanzar una excepción controlada
     * en lugar de romper la aplicación silenciosamente.
     *
     * @param dividendo número a dividir
     * @param divisor número por el cual dividir
     * @return resultado de la división
     * @throws ArithmeticException si el divisor es cero
     */
    public double dividir(double dividendo, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return dividendo / divisor;
    }
}