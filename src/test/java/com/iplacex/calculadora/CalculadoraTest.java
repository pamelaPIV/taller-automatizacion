package com.iplacex.calculadora;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Suite de pruebas unitarias para la clase Calculadora.
 *
 * Cada prueba es ATÓMICA: valida un único comportamiento.
 * Cada prueba es INDEPENDIENTE: no depende del estado dejado
 * por otra prueba (se crea una instancia nueva en @BeforeEach).
 */
class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada test: garantiza aislamiento (idempotencia)
        calculadora = new Calculadora();
    }

    @Test
    @DisplayName("Sumar dos números positivos debe retornar el resultado correcto")
    void testSuma_numerosPositivos() {
        int resultado = calculadora.sumar(5, 3);
        assertEquals(8, resultado, "5 + 3 debería ser 8");
    }

    @Test
    @DisplayName("Restar dos números positivos debe retornar el resultado correcto")
    void testResta_numerosPositivos() {
        int resultado = calculadora.restar(10, 4);
        assertEquals(6, resultado, "10 - 4 debería ser 6");
    }
}