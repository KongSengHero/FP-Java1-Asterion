# Labyrinth of Asterion — Team Exercise & Division Guide

Welcome to the team programming exercise for **Labyrinth of Asterion**!
All files in `src/model/` have been converted into **starter exercise stubs** with step-by-step `// TODO` directions.
At the bottom of each file, the complete reference implementation is provided in commented form (`/* ==================== SOLUTION ==================== */`).

---

## 👥 4-Member Team Division & UI Ownership

| Member | Backend Logic (`src/model/`) | Frontend / UI Component (`src/ui/`) | Responsibilities |
| :--- | :--- | :--- | :--- |
| **Member 1** | • `Dice.java`<br>• `Fighter.java`<br>• `Hero.java`<br>• `Player.java` | • `CombatScreen.java`<br>• `InventoryScreen.java` | Combat calculations, Guard absorption, inventory item stacking, equipment stats, and combat HUD / inventory menu display. |
| **Member 2** | • `Skill.java`<br>• `Item.java`<br>• `Treasure.java` | • `ShopScreen.java` | Skill formulas, equipment & potion models, chest drop chances, and Hermes' shop UI & transaction screens. |
| **Member 3** | • `Area.java`<br>• `WorldMap.java` | • `RoomScreen.java`<br>• `Menu.java` | **Visual heart of the game!** $11 \times 17$ room grid, doorway carving, overworld BFS distance calculation, drawing the room tiles, minimap display, and title/ending menus. |
| **Member 4** | • `Enemy.java`<br>• `Boss.java`<br>• `Game.java` | • `Main.java`<br>• `Term.java` | Monster AI routines, Asterion Phase 2 Enrage & Bull Charge, master game coordination, movement transitions, and terminal input/color engine. |

---

## 🛠️ How Each Member Completes Their File

1. Open your assigned file in `src/model/` (e.g., `Area.java` for Member 3).
2. Locate the `// TODO [Member X]` comments inside the methods.
3. Replace the placeholder return values with your actual implementation.
4. Run the file's self-test:
   - **In VS Code**: Click the **Run** button right above `public static void main(String[] args)`.
   - **In Terminal / PowerShell**:
     ```powershell
     javac -d out -sourcepath src src/model/Area.java
     java -cp out model.Area
     ```
   - **Using the batch runner**: Run `test_member.bat 3` (or `1`, `2`, `4`).
5. Your implementation is complete when all checks pass and you see:
   ```text
   [SUCCESS] <ClassName> self-test passed successfully!
   ```
6. If you get stuck, look at the commented reference solution at the bottom of the file.

---

## 🎮 Launching the Playable Game

Once the 4 members have implemented their model classes:
- **Windows**: Double-click `run.bat`
- **Mac / Linux**: Run `./run.sh`
- **Regression Tests**: Run `test_member.bat all` (runs all 12 unit tests + `FeatureTest`).
