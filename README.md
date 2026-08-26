# Taller 1 — Actividad 1: Flujo Básico de Integración Continua

## Objetivo

Profesionalizar el proceso de pruebas automatizadas de un proyecto Java aplicando
gestión de versiones con Git, configuración de dependencias con Maven, pruebas
unitarias atómicas con JUnit 5, y un pipeline de Integración Continua (CI) que
compile y ejecute los tests automáticamente ante cada cambio en el código.

Este proyecto aplica los conceptos revisados en las Unidades I y II de la
asignatura: control de versiones (Git), administración de dependencias (Maven),
principios de Integración Continua y diseño de suites de pruebas automatizadas.

---

## Estructura del proyecto
---

## Comandos utilizados

### Instalación del entorno

Antes de comenzar, se instalaron y configuraron:
- **Java (JDK 25 - Eclipse Temurin)**
- **Apache Maven 3.9.16**
- **Git 2.55.0**

### Control de versiones (Git)

```bash
# Inicializar el repositorio
git init
git branch -m main

# Configurar identidad
git config --global user.name "Pamela"
git config --global user.email "pamela.iturrieta@gmail.com"

# Primer commit
git add .gitignore
git commit -m "chore: inicializa repositorio y agrega .gitignore"

# Crear rama de trabajo (convención feature/)
git checkout -b feature/configuracion-maven

# Commit de la configuración Maven
git add pom.xml
git commit -m "feat: configura Maven con dependencia JUnit5 y estructura de carpetas src"

# Fusionar a main (simulando aprobación de Pull Request)
git checkout main
git merge feature/configuracion-maven --no-ff -m "merge: integra configuracion Maven (PR #1)"

# Segunda rama: código de negocio y pruebas
git checkout -b feature/calculadora-tests
git add src/
git commit -m "feat: agrega clase Calculadora y pruebas unitarias atomicas de suma y resta"
git checkout main
git merge feature/calculadora-tests --no-ff -m "merge: integra clase Calculadora y pruebas unitarias (PR #2)"

# Tercera rama: pipeline de CI
git checkout -b feature/pipeline-ci
git add -A
git commit -m "feat: agrega pipeline de CI con GitHub Actions para compilar y ejecutar tests"
git checkout main
git merge feature/pipeline-ci --no-ff -m "merge: integra pipeline de CI (PR #3)"

# Conectar con GitHub y subir
git remote add origin https://github.com/pamelaPIV/taller-automatizacion.git
git push -u origin main

# Ver historial completo de commits y ramas
git log --oneline --graph --all
```

### Maven (compilación y pruebas)

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar las pruebas unitarias
mvn clean test
```

Al ejecutar `mvn clean test`, Maven descarga automáticamente las dependencias
declaradas en `pom.xml` (JUnit 5), compila el código y ejecuta la clase
`CalculadoraTest`, generando el reporte en `target/surefire-reports/`.

**Resultado obtenido:**
---

## Archivos clave

| Archivo | Propósito |
|---|---|
| `pom.xml` | Define el proyecto Maven: `groupId`, `artifactId`, `version`, dependencia JUnit 5 y plugins (`maven-compiler-plugin`, `maven-surefire-plugin`). |
| `Calculadora.java` | Clase de negocio simple con los métodos `sumar()` y `restar()`, usada como sujeto de prueba. |
| `CalculadoraTest.java` | Suite de pruebas unitarias con JUnit 5. Cada test es **atómico** (valida un único comportamiento) e **independiente** (usa `@BeforeEach` para crear una instancia nueva de `Calculadora` antes de cada prueba, evitando dependencias de estado entre tests). |
| `.gitignore` | Excluye archivos generados (`target/`), configuraciones de IDE (`.idea/`) y logs del control de versiones. |
| `.github/workflows/ci.yml` | Pipeline de CI en GitHub Actions. |

---

## Explicación del pipeline de CI

El pipeline (`.github/workflows/ci.yml`) se dispara automáticamente ante:
- Cada `push` a la rama `main`
- Cada `pull request` dirigido a `main`

### Etapas del pipeline

1. **Checkout del código**: descarga el código del repositorio.
2. **Configurar JDK 17**: instala la versión de Java definida en `pom.xml`, y
   activa el **cacheo de dependencias Maven** para acelerar builds sucesivos.
3. **Compilar proyecto**: `mvn -B clean compile` — compila el código fuente.
4. **Ejecutar pruebas unitarias**: `mvn -B test` — ejecuta la suite de pruebas.
5. **Subir reporte de pruebas**: adjunta el reporte de resultados
   (`target/surefire-reports/`) como artifact descargable y navegable desde
   la pestaña *Actions* de GitHub.

Este diseño sigue el principio de **Pipeline as Code**: la configuración del
pipeline vive versionada en el mismo repositorio que el código, lo que permite
trazabilidad de cambios y facilita su replicación en otros proyectos.

### Evidencia de ejecución exitosa

El pipeline fue ejecutado exitosamente en GitHub Actions, con todos los pasos
completados en verde:
---

## Flujo de trabajo Git aplicado

Se utilizó una estrategia de ramas por tipo (`feature/`), con fusiones
explícitas a `main` mediante `--no-ff` para simular la aprobación de Pull
Requests, dejando un historial de commits trazable:
---

## Cómo ejecutar el proyecto localmente

```bash
# Clonar el repositorio
git clone https://github.com/pamelaPIV/taller-automatizacion.git
cd taller-automatizacion

# Ejecutar las pruebas
mvn clean test
```

---

## Autor

**Pamela Iturrieta**
Trabajo desarrollado para la asignatura **Automatización de Pruebas**,
Escuela de Informática y Telecomunicaciones — IPLACEX.