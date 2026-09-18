#!/usr/bin/env bash
# Script de despliegue a ambiente de prueba (staging)
# Unidad III - Deployment Pipeline

set -e  # Detener el script si cualquier comando falla

VERSION=$(date +%Y%m%d%H%M%S)
ENTORNO="staging"

echo "===================================================="
echo " DESPLIEGUE A AMBIENTE: $ENTORNO"
echo " Version del artefacto: build-$VERSION"
echo " Fecha: $(date)"
echo "===================================================="

echo "[1/4] Empaquetando artefacto..."
mvn -B package -DskipTests

echo "[2/4] Simulando copia del artefacto a servidor de $ENTORNO..."
mkdir -p deploy-history
echo "build-$VERSION desplegado en $ENTORNO el $(date)" >> deploy-history/log.txt

echo "[3/4] Simulando reinicio del servicio en $ENTORNO..."
sleep 1

echo "[4/4] Verificando salud del servicio (smoke test)..."
if [ -f target/*.jar ]; then
  echo "Artefacto generado correctamente. Servicio saludable."
else
  echo "ERROR: no se encontro el artefacto generado."
  exit 1
fi

echo "===================================================="
echo " DESPLIEGUE COMPLETADO CON EXITO EN $ENTORNO"
echo " Version activa: build-$VERSION"
echo "===================================================="
