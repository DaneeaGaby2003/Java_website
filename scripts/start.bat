@echo off
setlocal enabledelayedexpansion

if "%SPRING_PROFILES_ACTIVE%"=="" (
  set SPRING_PROFILES_ACTIVE=dev
)

echo Starting Orders API with profile: %SPRING_PROFILES_ACTIVE%

if "%SPRING_PROFILES_ACTIVE%"=="prod" (
  if "%DB_URL%"=="" echo Please set DB_URL && exit /b 1
  if "%DB_USER%"=="" echo Please set DB_USER && exit /b 1
  if "%DB_PASSWORD%"=="" echo Please set DB_PASSWORD && exit /b 1
)

mvn -DskipTests spring-boot:run
