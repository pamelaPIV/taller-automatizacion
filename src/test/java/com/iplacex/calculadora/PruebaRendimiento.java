package com.iplacex.calculadora;

/**
 * Prueba de rendimiento (no funcional) para el metodo dividir().
 *
 * A diferencia de los tests unitarios o BDD (que validan CORRECTITUD),
 * esta prueba mide CAPACIDAD: cuantas operaciones soporta el sistema
 * por segundo, y cuanto tarda cada una en promedio.
 *
 * Se ejecuta manualmente (no es parte de la suite automatica de mvn test),
 * ya que su proposito es analisis, no validacion de aprobado/reprobado.
 */
public class PruebaRendimiento {

    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        int totalOperaciones = 100_000;
        int errores = 0;

        System.out.println("Iniciando prueba de rendimiento...");
        System.out.println("Total de operaciones a ejecutar: " + totalOperaciones);

        long inicio = System.nanoTime();

        for (int i = 0; i < totalOperaciones; i++) {
            try {
                // Division normal para medir el caso comun de uso
                calculadora.dividir(100.0, 4.0);
            } catch (ArithmeticException e) {
                errores++;
            }
        }

        long fin = System.nanoTime();
        long duracionTotalNs = fin - inicio;
        double duracionTotalSegundos = duracionTotalNs / 1_000_000_000.0;

        double tps = totalOperaciones / duracionTotalSegundos;
        double latenciaPromedioMs = (duracionTotalNs / 1_000_000.0) / totalOperaciones;
        double tasaErrores = (errores * 100.0) / totalOperaciones;

        System.out.println("\n===== RESULTADOS DE LA PRUEBA DE RENDIMIENTO =====");
        System.out.printf("Tiempo total:              %.4f segundos%n", duracionTotalSegundos);
        System.out.printf("TPS (operaciones/segundo): %.2f%n", tps);
        System.out.printf("Latencia promedio:         %.6f ms por operacion%n", latenciaPromedioMs);
        System.out.printf("Errores encontrados:       %d (%.2f%%)%n", errores, tasaErrores);
        System.out.println("===================================================");
    }
}