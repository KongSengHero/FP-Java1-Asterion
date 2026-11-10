@echo off
cd /d "%~dp0"
if not exist out mkdir out

echo =======================================================
echo   Labyrinth of Asterion - Team Exercise Test Runner
echo =======================================================

javac -d out -sourcepath src src\model\*.java test\FeatureTest.java
if errorlevel 1 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

if "%1"=="1" goto member1
if "%1"=="2" goto member2
if "%1"=="3" goto member3
if "%1"=="4" goto member4
if "%1"=="all" goto all

echo Usage:
echo   test_member.bat 1    (Tests Member 1: Dice, Fighter, Hero, Player)
echo   test_member.bat 2    (Tests Member 2: Skill, Item, Treasure)
echo   test_member.bat 3    (Tests Member 3: Area, WorldMap)
echo   test_member.bat 4    (Tests Member 4: Enemy, Boss, Game)
echo   test_member.bat all  (Tests all 12 classes + FeatureTest)
echo.
pause
exit /b 0

:member1
echo [Testing Member 1: Combat & Hero Systems]
java -cp out model.Dice
java -cp out model.Fighter
java -cp out model.Hero
java -cp out model.Player
goto finish

:member2
echo [Testing Member 2: Items, Skills & Economy]
java -cp out model.Skill
java -cp out model.Item
java -cp out model.Treasure
goto finish

:member3
echo [Testing Member 3: Labyrinth Environment & World Map]
java -cp out model.Area
java -cp out model.WorldMap
goto finish

:member4
echo [Testing Member 4: Adversaries & Game Engine Orchestration]
java -cp out model.Enemy
java -cp out model.Boss
java -cp out model.Game
goto finish

:all
echo [Testing All Members 1 - 4]
java -cp out model.Dice
java -cp out model.Fighter
java -cp out model.Hero
java -cp out model.Player
java -cp out model.Skill
java -cp out model.Item
java -cp out model.Treasure
java -cp out model.Area
java -cp out model.WorldMap
java -cp out model.Enemy
java -cp out model.Boss
java -cp out model.Game
echo.
echo [Running Integrated Feature Regression Test]
java -cp out FeatureTest
goto finish

:finish
echo.
echo =======================================================
echo   Verification Completed!
echo =======================================================
pause

