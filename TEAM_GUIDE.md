# Void-Dungeon — Team Exercise & Division Guide

Welcome! Our game is **Void-Dungeon**: walk through a dungeon of 12–13 areas, fight monsters, shop at the
**Sacred Exchange**, grow stronger in the **Lost Realm**, and defeat the **Void Dragon**.

Every file in `src/model/` is a **starter exercise**: the structure is there, the logic is missing.
You fill in the methods marked `// TODO: Member X`. At the bottom of every file the complete
**reference solution** is written inside a comment (`SOLUTION / REFERENCE IMPLEMENTATION`).
Every file has a **self-test**: run it and it tells you what is right and what is wrong.

> The `src/ui/` screens and `Main.java` are already finished. You do not write them, but you **own** them:
> read them, run them, and be able to explain how they use your model classes.

---

## 👥 4-Member Team Division

| Member | Files to complete (`src/model/`) | Screens you own (`src/ui/`, `src/Main.java`) | What you are responsible for |
| :--- | :--- | :--- | :--- |
| **Member 1** | `WorldMap.java`<br>`Area.java`<br>`Game.java` | `Main.java`<br>`Term.java`<br>`Menu.java`<br>`RoomScreen.java` | The dungeon itself: the 5×5 world map and boss distance, the 11×17 rooms with walls and doors, building the whole game, walking, chests, shop and Lost Realm, the combat round, and the screens you see while walking around. |
| **Member 2** | `Dice.java`<br>`Fighter.java`<br>`Player.java` | `CombatScreen.java` | Luck and damage rules, defense and guarding, the player's stats, equipment, potions, mana, XP and levels, and the fight screen. |
| **Member 3** | `Skill.java`<br>`Hero.java`<br>`Enemy.java`<br>`Boss.java` | *(shares `Menu.java` with Member 1: the hero selection screen)* | Skill damage formulas, the Warrior / Mage / Rogue, the four monster kinds (Skeleton → Cyclops → Blaze → Void Guardian), and the Void Dragon with its Flame Breath. Also keeps the **test list** and the **README / report** for the team. |
| **Member 4** | `Item.java`<br>`Weapon.java`<br>`Armor.java`<br>`Potion.java`<br>`Loot.java` | `InventoryScreen.java` | Items and their stats, the four potions, the four gear tiers, chest loot and shop gear, and the inventory, potion picker and shop screens. (`Treasure.java` is a tiny finished data class.) |

Number of `TODO`s: Member 1 has 25, Member 2 has 22, Member 3 has 15 (plus the paperwork), Member 4 has 25.
The shares are close to equal; swap a small file between two members if you prefer a different split.

---

## 🧭 What to do first: order of work

Some files need other files to work. Start with the ones that need nothing, so nobody waits.

| Step | Files (owner) | Needs |
| :--- | :--- | :--- |
| 1 | `Dice` (M2), `WorldMap` (M1), `Item` + `Weapon` + `Armor` + `Potion` (M4), `Skill` constructor (M3) | nothing — **everyone can start today** |
| 2 | `Fighter` (M2) | `Dice` |
| 3 | `Hero` (M3), `Enemy` (M3), `Loot` (M4), `Skill` damage (M3) | `Weapon`, `Armor`, `Potion`, `Fighter`, `Dice` |
| 4 | `Player` (M2), `Boss` (M3) | `Fighter`, `Hero`, `Potion`, `Enemy`, `Skill` |
| 5 | `Area` (M1) | `WorldMap`, `Enemy`, `Boss`, `Loot`, `Potion` |
| 6 | `Game` (M1) | everything |

If you are **blocked** because a teammate's file is not done yet, open that file, scroll to the bottom,
and copy their reference solution into the top part **on your own computer only**. Do not commit it for them.

---

## 🛠️ How to complete a file

1. Open your file in `src/model/` (for example `Dice.java`).
2. Read the big comment at the top: *Task Description*, *What to do*, *Expected Output* and *Hints*.
3. Find each `// TODO: Member X`. Read the steps in the comments, then replace the placeholder
   (`return 0;`, `return null;`, an empty body…) with your real code.
4. Run the file's self-test (see below). Fix what it complains about. Repeat.
5. You are done when the run ends with:
   ```text
   [SUCCESS] <ClassName> self-test passed successfully!
   ```
6. Above **every method you write**, keep the one-line comment and add who wrote it, for example:
   `// Written by: Sokha`. If you cannot explain a method you wrote, ask a teammate before the demo.
7. Stuck for more than 20 minutes? Read the reference solution at the bottom of the file, close it,
   and write the code again **without looking**. Copy-pasting teaches nothing.

### Running the self-tests

**With the batch runner** (Windows) or the shell runner (Mac / Linux), from the `exercise` folder:

```powershell
.\test_member.bat 1
```
```sh
./test_member.sh 1
```
Use `1`, `2`, `3` or `4` for your own files, or `all` for everything (it ends with `FeatureTest`, which needs
every file finished).

**One file only, in VS Code:** click **Run** above `public static void main(String[] args)`.

**One file only, in the terminal:**

```powershell
javac -d out -sourcepath src src/model/Dice.java
java -cp out model.Dice
```

### Reading a failed test

- `AssertionError: ...` — the message says what was expected. Read it carefully.
- `NullPointerException ... is null` — you have not stored that value yet (usually the constructor), or a
  method still returns the placeholder `null`.
- The self-test of a file can also fail because a **teammate's** file is not finished (see the order of
  work above). Check the stack trace: if it points into another member's file, it is not your bug.

---

## 🎮 Playing the finished game

When all four members have passed their tests:

- **Windows:** double-click `run.bat`  (or `.\run.bat --plain` if the colors look wrong)
- **Mac / Linux:** `./run.sh`
- **Regression test:** `test_member.bat all` — runs all 15 self-tests and `FeatureTest`.
- **Balance check (optional):** `javac -d out -sourcepath src test/BalanceTest.java` then `java -cp out BalanceTest 500`.

---

## 🧩 What each member should be able to explain in the demo

- **Member 1:** how `Game` builds the dungeon and what happens when the player walks into a wall, a door,
  a chest and an enemy; how `WorldMap` finds the boss area (breadth-first search); what `RoomScreen` shows.
- **Member 2:** the damage formula in `takeDamage` (and what Guard changes); how `gainXp` can level up twice;
  how `Dice.vary` works.
- **Member 3:** how a `Skill` computes damage; how `Enemy.create` picks a kind by distance; how the Void
  Dragon announces and uses Flame Breath, and how it enrages.
- **Member 4:** the tiers in `Loot`, how `isUpgradeFor` compares gear, why potions stay in the shop while gear
  is sold once, and how `InventoryScreen` pages through the inventory.

Everybody should also understand, in one sentence each, what the **other three** parts of the game do.

---

## 📁 Folder layout

```text
exercise/
  TEAM_GUIDE.md      this file
  test_member.bat    self-test runner for Windows   (test_member.sh for Mac / Linux)
  run.bat / run.sh   start the finished game
  src/
    Main.java        finished: starts the game
    model/           THE RULES: your exercise files (stubs + solution at the bottom)
    ui/              finished screens: only draw and read keys
  test/
    FeatureTest.java checks everything together after all four are done
    BalanceTest.java bots play full games to check the difficulty
```

The rule: `ui` may use `model`, never the other way around. That is why every model class can be tested
without a screen.

## ✅ Final checklist

- [ ] My files all end with `[SUCCESS]` in `test_member`.
- [ ] `test_member all` passes (needs all four members).
- [ ] The game starts with `run.bat` / `./run.sh` and I can finish a fight, buy an item, use the Lost Realm.
- [ ] Every method I wrote has a comment with what it does and who wrote it.
- [ ] I can explain every method that has my name on it, without looking at the solution.
