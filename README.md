# Void-Dungeon (terminal version)

Walk through a dungeon of 13 areas, fight monsters, shop at the Sacred Exchange, grow stronger in the Lost Realm,
and defeat the Void Dragon. Needs Java (JDK 17 or newer).

## Run
- Mac / Linux: `./run.sh`
- Windows: double-click `run.bat`

Options (add them after the command, e.g. `./run.sh --plain`):
- `--plain`  no colors and plain characters (the game also switches to plain characters by itself if your
  console can not show the blocks)
- `--lines`  type keys and press Enter, instead of instant keys
- `--seed 5`  same number = same dungeon (handy when testing)

## How to play
Every key works instantly, no Enter needed.
- In the dungeon: `w a s d` or the arrow keys walk, `i` opens your inventory, `?` help, `q` quit.
- In a fight: `1` attack, `2` and `3` your two skills, `4` defend, `i` (or `5`) choose a potion from inventory. Gear cannot be changed mid-fight.
- In the inventory: select a numbered item to equip a weapon/armor or use a potion. Use `n` / `b` to change pages and `q` to close.
- In the shop: the number of the item to buy, Enter to leave. Purchased equipment is added to the inventory; it is not equipped automatically.

Walk into things to use them: `M` enemy, `C` chest, `SS` Sacred Exchange (shop), `RR` Lost Realm, `DD` Void Dragon.
The `░░` gaps in the wall are doors. The small map on the right shows the 13 areas.

If instant keys are not possible (an IDE console, or no PowerShell on Windows) the game notices and falls back to
"type the key, then press Enter" (you can type several: `wwdd`). `--lines` forces that.

## Folder structure
Note: the folder is still called `VoidDungeon`; rename it whenever you like, nothing in the code depends on the name.
```
src/
  Main.java        starts the game (reads the options, runs the menus and the game)
  model/           THE RULES. Nothing in here prints or reads input.
    Game.java        builds the dungeon; walking, chests, shop, Lost Realm, the combat round
    WorldMap.java    the layout of the 13 areas (the 4 fixed maps, distance from the start)
    Area.java        one area: walls, doors, the fixed room layouts (FIXED_MAPS), what stands where
    Hero.java        the 3 hero classes: stats, starting gear, and their two skills
    Skill.java       one skill: mana cost and damage formula (also the Void Dragon's Flame Breath)
    Fighter.java     base of every fighter: the one place for attack damage, defense, healing, plus random helpers (chance, vary)
    Player.java      the hero: inventory, equipped gear, mana, XP and levels, potions
    Enemy.java       the 4 enemy kinds (Skeleton, Cyclops, Blaze, Void Guardian) and their act()
    Boss.java        the Void Dragon, with its Flame Breath
    Dice.java        luck helpers: chance() and vary()
    Item.java        base of Weapon.java, Armor.java and Potion.java
    Loot.java        the 4 gear tiers, potion odds, chest contents (Treasure.java) and shop gear
  ui/              WHAT THE PLAYER SEES. Only prints and reads input.
    Term.java        colors, bars, drawing a screen, reading single key presses
    Menu.java       title, hero selection, ending
    InventoryScreen.java inventory browsing, equipping, potion selection, and Sacred Exchange
    RoomScreen.java  the main screen: status bar, room, minimap, rewards
    CombatScreen.java the fight
 test/
   FeatureTest.java verifies inventory, potion, and chest-drop behavior
   BalanceTest.java bots play games to check the balance (./test.sh)
```
The rule: `ui` may use `model`, never the other way around. That is why the balance test can play the
whole game without a screen. A new feature usually means: change `model`, then show it in one screen in `ui`.

## The rules in short
- **Heroes:** Warrior (150 base HP, tanky), Mage (100 base HP, 120 MP, stronger/cheaper spells, improved robes), Rogue (+10% critical hit chance). Starting armor adds its own HP and defense bonuses (Mage robes now provide +3 DEF and +20 HP).
- **Inventory:** starting gear and every found/bought item remain in the inventory. Choose a weapon or armor to equip; gear is never auto-equipped. Every potion is its own inventory row; choosing one drinks it. Armor changes maximum HP, but equipping armor does not heal you.
- **Fight:** you and the enemy take turns, no running away. Attack, 2 skills (cost mana), Defend, or choose a potion. Defend doubles the defense used against the next incoming hit, without also halving the damage left over. Defend restores Warrior +12 HP, Mage +15 MP, or Rogue +5 HP and +5 MP.
- **Skills:** each hero's two skills show their mana cost, damage multiplier, flat bonus, defense scaling (when relevant), and final damage swing on the hero-selection screen. Mage spells have lower costs and stronger multipliers than before.
- **Potions:** Healing Potion (+110 HP), Mana Potion (+60 MP), Mixed Potion (+75 HP, +30 MP) and Great Potion (+140 HP, +55 MP). Chest potion drops are weighted toward the Mixed and single-stat potions; Great Potions are uncommon. The Sacred Exchange sells all four types.
- **Loot:** a chest has a 28% chance to contain a potion, an 8% chance to contain a weapon, an 8% chance to contain armor, and a 56% chance to contain gold only. Chest gear is capped at tier 3. Sacred Exchange can offer tier 4 gear. Normal enemies currently reward gold and XP; they do not have an equipment-drop system.
- **Lost Realm:** walk into it once to gain +30 max HP and +10 max MP, and a full refill. It can only be used once.
- **Progress:** enemies give XP and gold. A level needs 1.5x more XP than the last and gives +25 HP, +15 MP, +4 ATK, +2 DEF and a full refill. Gear has 4 tiers.
- **Enemy behavior:** enemies get harder with distance: Skeleton (guards when hurt), Cyclops (heals itself), Blaze (hits wildly), Void Guardian (crits often, guards when hurt). The Void Dragon uses a telegraphed Flame Breath (every 3rd turn, every 2nd when enraged below half HP).

## Balance checks
Run `./test.sh 1000` to compare the simple automated strategies. The bot equips the best gear it finds and buys upgrades at the Sacred Exchange; the actual player chooses equipment manually.

The test is a simulation, not proof that every hero/build is equally balanced. Compare all four strategies and watch for large gaps between classes after any change. Skipping monsters should remain a poor strategy because kills are the main source of XP and gold.

Where to change numbers: enemy strength `Enemy` (stats) and its kinds, boss `Boss`, heroes `Hero`, skills `Skill`, levels `Player.gainXp`, chest gold and gear `Loot`, potion variants `Potion`, and blocking `Fighter.takeDamage`.

## Combat balance notes
- Defend doubles the defense used against one incoming hit, without a second damage-halving step. It restores Warrior +12 HP, Mage +15 MP, or Rogue +5 HP and +5 MP. A guard can still carry over if an enemy chooses to guard or heal instead of attacking.
- Skills use a rolled normal attack as their base, so equipped weapon damage and critical hits affect skills too. The listed multiplier/flat/DEF bonuses are applied before the skill's own damage swing.
- Avoid buffing chest gear drop rates to solve a weak build. That makes the Sacred Exchange irrelevant again. Tune enemy XP/gold, shop stock/prices, and boss durability separately from chest gear odds.

## Troubleshooting
- Colors or symbols look wrong: run with `--plain`.
- Colors do not show or the screen does not clear (IDE console): run in a real terminal (Windows Terminal, cmd, macOS Terminal).
- Keys need Enter: you are probably in an IDE console. Use a real terminal, or accept the Enter mode.
