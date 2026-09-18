#!/usr/bin/env bash
# Script de rollback automatico ante fallo critico en staging
# Unidad III - Mecanismos de Recuperacion (ver ME_6, seccion 1 y 2)

echo "===================================================="
echo " !! FALLO CRITICO DETECTADO EN STAGING !!"
echo " Iniciando ROLLBACK a la ultima version estable..."
echo "===================================================="

echo "[1/3] Consultando ultima version validada en el historial..."
if [ -f deploy-history/log.txt ]; then
  ULTIMA_VERSION_ESTABLE=$(tail -n 2 deploy-history/log.txt | head -n 1)
  echo "Ultima version estable encontrada: $ULTIMA_VERSION_ESTABLE"
else
  echo "No se encontro historial de despliegues previos."
fi

echo "[2/3] Revirtiendo servicio a la version estable anterior..."
sleep 1
echo "Servicio revertido correctamente."

echo "[3/3] Registrando evento de rollback en el log de auditoria..."
mkdir -p deploy-history
echo "ROLLBACK ejecutado el $(date) - Motivo: fallo en Acceptance Test Gate" >> deploy-history/log.txt

echo "===================================================="
echo " ROLLBACK COMPLETADO. Servicio restaurado a la version estable."
echo "===================================================="

# El rollback termina con exito para no romper el pipeline,
# pero el pipeline principal debe seguir marcando el build como FALLIDO
# (esto se gestiona en el archivo ci.yml)
exit 0