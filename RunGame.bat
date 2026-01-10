@echo off
javac *.java
if %errorlevel% neq 0 pause
java GameMain
pause
