# Examen Final - Automatizacion de Pruebas

## Autor
**Pamela Iturrieta**
Escuela de Informatica y Telecomunicaciones - IPLACEX

## Descripcion del proyecto

Este proyecto integra los contenidos de las Unidades I, II y III de la
asignatura Automatizacion de Pruebas: control de versiones con Git,
administracion de dependencias con Maven, Integracion Continua (CI),
Behavior-Driven Development (BDD) con Cucumber, y un Deployment Pipeline
completo con Acceptance Test Gate y mecanismo de rollback automatico.

El proyecto es una calculadora simple (suma, resta, division) usada como
caso de estudio para aplicar cada practica de automatizacion de pruebas
de forma progresiva y realista.

---

## Actividad 1: Control de versiones y configuracion Maven

### Flujo de ramas (Trunk-Based con features cortas)

Se utilizo una estrategia basada en ramas `feature/` de corta duracion,
fusionadas a `main` mediante merges explicitos (`--no-ff`) que simulan
la aprobacion de Pull Requests, dejando un historial trazable:
git checkout -b feature/nombre-de-la-tarea
git add .
git commit -m "feat: descripcion del cambio"
git checkout main
git merge feature/nombre-de-la-tarea --no-ff -m "merge: integra la tarea (PR #N)"
git push origin main

### Dependencias configuradas en pom.xml

- **JUnit 5** (jupiter): pruebas unitarias
- **Cucumber** (cucumber-java, cucumber-junit-platform-engine): pruebas BDD/aceptacion
- **Selenium** (selenium-java): pruebas de UI/aceptacion (declarada segun requerimiento)
- **JUnit Platform Suite**: orquesta la ejecucion de los escenarios Cucumber

### Estructura del proyecto
taller-automatizacion/
|-- .github/workflows/ci.yml # Pipeline de CI/CD completo
|-- scripts/
| |-- deploy.sh # Script de despliegue a staging
| -- rollback.sh # Script de rollback automatico |-- src/ | |-- main/java/com/iplacex/calculadora/ | | -- Calculadora.java # sumar, restar, dividir
| -- test/ | |-- java/com/iplacex/calculadora/ | | |-- CalculadoraTest.java # Pruebas unitarias | | |-- RunCucumberTest.java # Runner de Cucumber | | -- steps/DivisionSteps.java # Step definitions BDD
| -- resources/features/ | -- division.feature # Escenarios Gherkin
|-- pom.xml
`-- README.md

---

## Actividad 2: Pipeline de Integracion Continua

El pipeline (`.github/workflows/ci.yml`) se ejecuta automaticamente ante
cada `push` o `pull request` a la rama `main`, e incluye **dos tipos de
pruebas** distintas, cumpliendo el requisito de la actividad:

### Etapa 1: Build, Compile y Unit Tests
- Compila el proyecto con `mvn -B clean compile`
- Ejecuta las **pruebas unitarias** de `CalculadoraTest` con `mvn -B test`
- Publica el reporte de resultados como artifact descargable

### Etapa 2: Acceptance Test Gate (BDD)
- Ejecuta los **escenarios de aceptacion** (Cucumber/Gherkin) definidos
  en `division.feature`, incluyendo un escenario simple y un Scenario
  Outline parametrizado con 4 casos (incluyendo division por cero)
- Publica el reporte HTML navegable de Cucumber como artifact
- Esta etapa solo se ejecuta si la etapa de Build fue exitosa (`needs:`)

### Sesion "Three Amigos" (simulada)

Antes de implementar el escenario de division, se definio el
comportamiento esperado simulando una sesion conjunta entre negocio,
QA y desarrollo:

- **Negocio:** "Los usuarios necesitan dividir numeros, pero el sistema
  no debe romperse si alguien intenta dividir por cero."
- **QA:** "Hay que probar division exacta, con decimales, y el caso
  limite de division por cero."
- **Desarrollo:** "Se puede lanzar una excepcion controlada
  (ArithmeticException) cuando el divisor sea cero."

**Criterios de aceptacion acordados:**
1. Dados dos numeros validos, la division retorna el resultado correcto.
2. Dado un divisor igual a cero, el sistema lanza un error controlado
   en vez de fallar de forma inesperada.

---

## Actividad 3: Deployment Pipeline con Acceptance Gate y Rollback

### Etapa 3: Deploy a Staging

Tras superar el Acceptance Test Gate, el pipeline ejecuta el script
`scripts/deploy.sh`, que simula el despliegue a un ambiente de staging:

1. Empaqueta el artefacto (`mvn package`)
2. Simula la copia del artefacto al servidor de staging
3. Simula el reinicio del servicio
4. Ejecuta un **smoke test**: verifica que el artefacto se genero
   correctamente antes de dar el despliegue por exitoso

### Mecanismo de Rollback automatico

Si el smoke test detecta un problema (o cualquier paso del despliegue
falla), el pipeline ejecuta automaticamente `scripts/rollback.sh`
(condicion `if: failure()`), que:

1. Consulta la ultima version estable en el historial de despliegues
2. Revierte el servicio a esa version estable
3. Registra el evento de rollback en un log de auditoria

### Evidencia de validacion del rollback

Para comprobar que el mecanismo funciona de verdad (no solo en teoria),
se forzo un fallo critico intencional en `deploy.sh` y se ejecuto el
flujo completo:$ bash scripts/deploy.sh
...
[4/4] Verificando salud del servicio (smoke test)...
(FALLO FORZADO)
$ echo $?
1

$ bash scripts/rollback.sh
!! FALLO CRITICO DETECTADO EN STAGING !!
Iniciando ROLLBACK a la ultima version estable...
[1/3] Consultando ultima version validada en el historial...
Ultima version estable encontrada: build-20260917235529...
[2/3] Revirtiendo servicio a la version estable anterior...
Servicio revertido correctamente.
[3/3] Registrando evento de rollback en el log de auditoria...
ROLLBACK COMPLETADO. Servicio restaurado a la version estable.

Tras la validacion, el fallo forzado fue removido y el pipeline volvio
a ejecutarse exitosamente en sus 3 etapas.

---

## Estrategia de pruebas implementada

| Nivel | Herramienta | Que valida |
|---|---|---|
| Unitario | JUnit 5 | Logica aislada de `sumar()` y `restar()` |
| Aceptacion (BDD) | Cucumber/Gherkin | Comportamiento de negocio de `dividir()`, incluyendo el caso de error |
| Despliegue | Script bash + smoke test | Que el artefacto se genero y el servicio esta saludable |
| Recuperacion | Script de rollback | Que el sistema puede revertirse ante un fallo critico |

---

## Como ejecutar el proyecto localmente

```bash
# Clonar el repositorio
git clone https://github.com/pamelaPIV/taller-automatizacion.git
cd taller-automatizacion

# Ejecutar todas las pruebas (unitarias + BDD)
mvn clean test

# Ejecutar el script de despliegue (requiere bash / Git Bash en Windows)
bash scripts/deploy.sh

# Ejecutar el script de rollback manualmente (simulacion)
bash scripts/rollback.sh
```

**Resultado esperado de `mvn clean test`:**
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS

---

## Evidencias

> Las capturas de pantalla de la ejecucion local, del pipeline en
> GitHub Actions (3 etapas en verde), del fallo simulado y del
> rollback exitoso se adjuntan en el documento Word de entrega
> del Examen Final.
