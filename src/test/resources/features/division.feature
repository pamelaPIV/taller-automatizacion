# language: es
Característica: División de números en la Calculadora
  Como usuario de la calculadora
  Quiero dividir dos números
  Para obtener resultados precisos sin que el sistema falle inesperadamente

  Antecedentes:
    Dado que tengo una calculadora

  Escenario: División exacta de dos números positivos
    Cuando divido 10 entre 2
    Entonces el resultado debería ser 5.0

  Esquema del escenario: Validar división con distintos valores, incluyendo división por cero
    Cuando divido <dividendo> entre <divisor>
    Entonces el resultado debería ser <resultado>

    Ejemplos:
      | dividendo | divisor | resultado |
      | 10        | 2       | 5.0       |
      | 9         | 3       | 3.0       |
      | 7         | 2       | 3.5       |
      | 5         | 0       | error     |