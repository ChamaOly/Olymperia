@echo off
echo Creando .gitignore...

echo *.iml > .gitignore
echo .gradle >> .gitignore
echo /local.properties >> .gitignore
echo /.idea >> .gitignore
echo .DS_Store >> .gitignore
echo /build >> .gitignore
echo /captures >> .gitignore
echo .externalNativeBuild >> .gitignore
echo .cxx >> .gitignore
echo *.apk >> .gitignore
echo *.ap_ >> .gitignore
echo *.db >> .gitignore
echo *.keystore >> .gitignore
echo *.jks >> .gitignore
echo .env >> .gitignore
echo output.json >> .gitignore

echo .gitignore creado.

echo --------------------------
echo Creando README.md...

(
echo # Olymperia
echo.
echo Olymperia es una app de gamificación deportiva vinculada con Strava. Permite a los usuarios ganar puntos completando puertos, avanzar de nivel, conquistar provincias y desbloquear logros visuales en un mapa interactivo.
echo.
echo ## Características destacadas
echo.
echo - Sistema de niveles y divisiones
echo - Logros de "Conquistador" y "Rey" por provincia
echo - Mapa interactivo con visualización de progresos
echo - Perfil de usuario con logros destacados
echo - Cacheo de esfuerzos para optimizar llamadas a la API de Strava
echo.
echo Desarrollada en Android con Kotlin.
echo.
echo ## Estado del proyecto
echo.
echo En desarrollo - Fase 6 pendiente de implementación.
) > README.md

echo README.md creado.

pause
