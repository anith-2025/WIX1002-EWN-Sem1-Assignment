@echo off
setlocal enabledelayedexpansion

echo [SYSTEM] Scanning IntelliJ for JDK 25...

:: 1. Search for ANY folder in the IntelliJ JDK directory that starts with '25'
set "JAVA_BIN="
for /d %%i in ("%USERPROFILE%\.jdks\*25*") do (
    if exist "%%i\bin\java.exe" (
        set "JAVA_BIN=%%i\bin"
    )
)

:: 2. If not found in User folder, check Program Files
if "%JAVA_BIN%"=="" (
    for /d %%i in ("C:\Program Files\Java\*25*") do (
        if exist "%%i\bin\java.exe" (
            set "JAVA_BIN=%%i\bin"
        )
    )
)

:: 3. Verification
if "%JAVA_BIN%"=="" (
    echo [ERROR] Could not find JDK 25!
    echo Please ensure the Download in IntelliJ has finished.
    pause
    exit /b
)

echo [SYSTEM] Found JDK 25 at: %JAVA_BIN%
set "PATH=%JAVA_BIN%;%PATH%"

:: 4. Run the Game
echo [1/2] Running Game Logic...
"%JAVA_BIN%\javac" -cp ".;EWN_GUI.jar" *.java
"%JAVA_BIN%\java" -cp ".;EWN_GUI.jar" GameMain

:: 5. Launch the Viewer
echo.
echo [2/2] Launching Visual Viewer...
"%JAVA_BIN%\java" -jar EWN_GUI.jar

pause