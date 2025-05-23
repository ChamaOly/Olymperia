@echo off
D:
cd Olymperia

echo Iniciando repositorio Git...
git init

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

echo Creando README.md...
(
echo # Olymperia
echo.
echo Olymperia es una app de gamificación deportiva vinculada con Strava.
echo Permite ganar puntos, subir de nivel y conquistar provincias.
echo.
echo ## Estado
echo En desarrollo - Fase 6 pendiente de implementación.
) > README.md

echo Agregando archivos a Git...
git add .

echo Haciendo primer commit...
git commit -m "Primer commit: agrega .gitignore y README"

echo Conectando con GitHub...
git remote add origin https://github.com/ChamaOly/Olymperia.git
git branch -M main

echo Subiendo a GitHub...
git push -u origin main

echo PROCESO COMPLETADO.
pause
