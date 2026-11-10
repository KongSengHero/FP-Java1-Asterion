@echo off
rem Compiles the game into out\ and starts it. Options are passed on, e.g.  run.bat --plain
cd /d "%~dp0"
if not exist out mkdir out
javac -d out -sourcepath src src\Main.java && java -cp out Main %*
pause
