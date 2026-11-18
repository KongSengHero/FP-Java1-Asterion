#!/bin/sh
# Runs the self-tests.   ./test_member.sh 1   (or 2, 3, 4, all)
cd "$(dirname "$0")"
mkdir -p out
javac -d out -sourcepath src src/model/*.java test/FeatureTest.java || { echo '[ERROR] Compilation failed!'; exit 1; }

case "$1" in
  1)
    java -cp out model.WorldMap
    java -cp out model.Area
    java -cp out model.Game
    ;;
  2)
    java -cp out model.Dice
    java -cp out model.Fighter
    java -cp out model.Player
    ;;
  3)
    java -cp out model.Skill
    java -cp out model.Hero
    java -cp out model.Enemy
    java -cp out model.Boss
    ;;
  4)
    java -cp out model.Item
    java -cp out model.Weapon
    java -cp out model.Armor
    java -cp out model.Potion
    java -cp out model.Loot
    ;;
  all)
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
    java -cp out FeatureTest
    ;;
  *)
    echo "Usage: ./test_member.sh 1|2|3|4|all"
    ;;
esac
