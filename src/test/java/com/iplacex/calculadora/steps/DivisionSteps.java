package com.iplacex.calculadora.steps;

import com.iplacex.calculadora.Calculadora;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Step Definitions para el archivo division.feature.
 * Conecta el lenguaje Gherkin (en español) con la lógica real de la Calculadora.
 */
public class DivisionSteps {

    private Calculadora calculadora;
    private double resultado;
    private boolean ocurrioError;

    @Dado("que tengo una calculadora")
    public void queTengoUnaCalculadora() {
        calculadora = new Calculadora();
        ocurrioError = false;
    }

    @Cuando("divido {double} entre {double}")
    public void dividoEntre(double dividendo, double divisor) {
        try {
            resultado = calculadora.dividir(dividendo, divisor);
        } catch (ArithmeticException e) {
            ocurrioError = true;
        }
    }

    @Entonces("el resultado debería ser {double}")
    public void elResultadoDeberiaSer(double resultadoEsperado) {
        assertEquals(resultadoEsperado, resultado, 0.001,
                "El resultado de la división no coincide con lo esperado");
    }

    @Entonces("el resultado debería ser error")
    public void elResultadoDeberiaSerError() {
        assertEquals(true, ocurrioError,
                "Se esperaba un error de división por cero, pero no ocurrió");
    }
}