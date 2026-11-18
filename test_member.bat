@echo off
cd /d "%~dp0"
if not exist out mkdir out

echo =======================================================
echo   Void-Dungeon - Team Exercise Test Runner
echo =======================================================

javac -d out -sourcepath src src\model\*.java test\FeatureTest.java
if errorlevel 1 (
    echo [ERROR] Compilation failed! Fix the error above, then run this again.
    pause
    exit /b 1
)

if "%1"=="1" goto member1
if "%1"=="2" goto member2
if "%1"=="3" goto member3
if "%1"=="4" goto member4
if "%1"=="all" goto all

echo Usage:
echo   test_member.bat 1    (Member 1: WorldMap, Area, Game)
echo   test_member.bat 2    (Member 2: Dice, Fighter, Player)
echo   test_member.bat 3    (Member 3: Skill, Hero, Enemy, Boss)
echo   test_member.bat 4    (Member 4: Item, Weapon, Armor, Potion, Loot)
echo   test_member.bat all  (all 15 files + FeatureTest)
echo.
pause
exit /b 0

:member1
echo [Testing Member 1: WorldMap, Area, Game]
java -cp out model.WorldMap
java -cp out model.Area
java -cp out model.Game
goto finish

:member2
echo [Testing Member 2: Dice, Fighter, Player]
java -cp out model.Dice
java -cp out model.Fighter
java -cp out model.Player
goto finish

:member3
echo [Testing Member 3: Skill, Hero, Enemy, Boss]
java -cp out model.Skill
java -cp out model.Hero
java -cp out model.Enemy
java -cp out model.Boss
goto finish

:member4
echo [Testing Member 4: Item, Weapon, Armor, Potion, Loot]
java -cp out model.Item
java -cp out model.Weapon
java -cp out model.Armor
java -cp out model.Potion
java -cp out model.Loot
goto finish

:all
echo [Testing all members]
java -cp out model.WorldMap
java -cp out model.Area
java -cp out model.Game
java -cp out model.Dice
java -cp out model.Fighter
java -cp out model.Player
java -cp out model.Skill
java -cp out model.Hero
java -cp out model.Enemy
java -cp out model.Boss
java -cp out model.Item
java -cp out model.Weapon
java -cp out model.Armor
java -cp out model.Potion
java -cp out model.Loot
echo.
echo [Running the integrated FeatureTest - needs every file finished]
java -cp out FeatureTest
goto finish

:finish
echo.
echo =======================================================
echo   Done. Every file you are responsible for must end with [SUCCESS].
echo =======================================================
pause
