/*
 * Team Project: Void-Dungeon
 * Member: Member 1 (Game Engine, Map & Screens)
 *
 * File: Game.java
 *
 * Task Description:
 * The heart of the game: it builds the whole dungeon and holds all the rules for walking, chests,
 * the shop (Sacred Exchange), the Lost Realm and combat. The screens in `ui/` only draw what is here.
 * This is the biggest file: do it last, after the others, or use their solutions to get going.
 *
 * What to do (in this order):
 * 1. createAreas(): make an Area for every cell of the map. Mark the start and the boss area (the boss area
 *    gets the Void Dragon object in the centre). Return the list of all the OTHER areas.
 * 2. placeSpecialAreas(others, rnd): shuffle the list; the 1st becomes the SHOP (OBJ_SHOP in the centre),
 *    the 2nd becomes the REALM (OBJ_REALM in the centre). Remove those two from the list.
 * 3. fillAreas(normalAreas, rnd): placeRandomObjects in each normal area; make sure at least one normal area
 *    stays empty (if none is, clear the objects of the first one); then populate() every area with its distance.
 * 4. placePlayerAtStart(): put the player in the start area next to its first door, and mark it visited.
 * 5. move(d): walk one step. Walls block, objects are used (interact), doors take you to the next area.
 * 6. interact / openChest: use the object you walked into (see the comments inside).
 * 7. The four combat actions, startRound, endRound and win.
 * 8. buy(index) and symbol(area).
 *
 * Expected Output:
 * === Testing Game.java (Member 1) ===
 * [PASS] The dungeon is built: start, boss, shop and Lost Realm exist once
 * [PASS] Normal areas are filled and at least one is empty
 * [PASS] The player starts in the start area next to a door
 * [PASS] Walls block, free tiles let you walk, doors lead to the next area
 * [PASS] Walking into a chest, the Lost Realm, the shop, an enemy and the boss
 * [PASS] Combat: attack, skill, defend and potion
 * [PASS] Winning a fight, losing a fight and beating the boss
 * [PASS] Buying: gear is sold once, potions are not, no gold means no sale
 * [PASS] Map letters for each kind of area
 * [SUCCESS] Game self-test passed successfully!
 *
 * Hints:
 * - Direction d: 0 north, 1 east, 2 south, 3 west. The new position is playerRow + WorldMap.DR[d], playerCol + WorldMap.DC[d].
 * - Going through a door: the player appears at the OPPOSITE door of the next area: opposite = (d + 2) % 4,
 *   position Area.ENTRY_ROW[opposite], Area.ENTRY_COL[opposite].
 * - A combat round: the player acts, then if the enemy is still alive it answers with fighting.act(player).
 * - Use `instanceof Potion` to find out if an item is a potion.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    public WorldMap world = new WorldMap();
    public Area[][] areas = new Area[WorldMap.SIZE][WorldMap.SIZE];
    public Player player;

    public int areaRow, areaCol;      // which area the player is in
    public int playerRow, playerCol;  // where the player stands inside that area

    public boolean inShop = false;
    public boolean gameOver = false;
    public boolean won = false;
    public String lastMessage = "";   // one-time message the screen shows after an action

    public Enemy fighting = null;     // the enemy in the current fight, or null
    private int fightRow, fightCol;   // where that enemy stands
    public int kills = 0;
    public boolean[][] visited = new boolean[WorldMap.SIZE][WorldMap.SIZE];

    // Results of the last combat round, read by the combat screen.
    public int dealt, taken, healed, manaGained, reward, xpGain;
    public boolean crit, blocked;
    public String used = "";          // name of the skill used, or "" for a normal attack
    private int hpBefore, manaBefore;

    // Builds a new dungeon for the chosen hero. (Finished: it calls the methods you write below.)
    public Game(Random rnd, Hero hero) {
        player = new Player(hero);
        world.useFixedMap(rnd);

        List<Area> normalAreas = createAreas();
        placeSpecialAreas(normalAreas, rnd);
        fillAreas(normalAreas, rnd);
        placePlayerAtStart();
    }

    // Creates an Area for every cell of the map. Marks the start and boss areas and returns all the others.
    private List<Area> createAreas() {
        // TODO: Member 1
        // 1. List<Area> others = new ArrayList<Area>();
        // 2. For every r and c where world.isArea(r, c): area = new Area(world, r, c); areas[r][c] = area;
        //    - the start cell (world.startRow, world.startCol): area.type = Area.START
        //    - the boss cell (world.bossRow, world.bossCol): area.type = Area.BOSS and area.placeCenterObject(Area.OBJ_BOSS)
        //    - every other area: others.add(area)
        // 3. Return others.
        return new ArrayList<Area>();
    }

    // Randomly turns two of the other areas into the shop (Sacred Exchange) and the Lost Realm.
    // The areas left over stay normal and are removed from the list.
    private void placeSpecialAreas(List<Area> others, Random rnd) {
        // TODO: Member 1
        // 1. Collections.shuffle(others, rnd);
        // 2. others.get(0): type SHOP, placeCenterObject(Area.OBJ_SHOP). others.get(1): type REALM, placeCenterObject(Area.OBJ_REALM).
        // 3. others.remove(0) twice, so only the normal areas are left in the list.
    }

    // Puts enemies and chests in the normal areas and creates their real content.
    private void fillAreas(List<Area> normalAreas, Random rnd) {
        // TODO: Member 1
        // 1. For each normal area: area.placeRandomObjects(rnd). Remember if any area has countObjects() == 0.
        // 2. If no area is empty: normalAreas.get(0).objects = new int[Area.ROWS][Area.COLS];
        // 3. For every non-null area in areas: area.populate(world.dist[r][c], rnd);
    }

    // Puts the player in the start area, next to its first door.
    private void placePlayerAtStart() {
        // TODO: Member 1
        // 1. areaRow = world.startRow; areaCol = world.startCol; visited[areaRow][areaCol] = true;
        // 2. For d = 0..3: the first d where the start area has a door (start.doors[d]):
        //    playerRow = Area.ENTRY_ROW[d]; playerCol = Area.ENTRY_COL[d]; then stop (break).
    }

    public Area currentArea() {
        return areas[areaRow][areaCol];
    }

    // Tries to walk one step. d = 0 north, 1 east, 2 south, 3 west. Walking into an object uses it.
    public void move(int d) {
        // TODO: Member 1
        // 1. Do nothing if gameOver, won, or fighting != null.
        // 2. newRow / newCol = the position one step in direction d.
        // 3. Do nothing if that is outside the area (0..ROWS-1, 0..COLS-1) or a WALL tile.
        // 4. If objects[newRow][newCol] != Area.NONE: interact(area, newRow, newCol) and stop.
        // 5. If the tile is a DOOR: go to the next area (areaRow/areaCol change by DR/DC, mark it visited) and
        //    set newRow / newCol to the opposite door's entry (see Hints).
        // 6. playerRow = newRow; playerCol = newCol;
    }

    // The player walked into the object at (r, c): start a fight, open a chest, enter the shop or the Lost Realm.
    private void interact(Area area, int r, int c) {
        // TODO: Member 1
        // - ENEMY: startFight(area.enemies[r][c], r, c)
        // - OBJ_BOSS: startFight(area.boss, r, c)
        // - CHEST: openChest(area, r, c)
        // - OBJ_SHOP: inShop = true
        // - OBJ_REALM, only if !area.realmUsed: area.realmUsed = true; player.enterLostRealm();
        //   lastMessage = "The Lost Realm grants +30 max HP and +10 max MP, and restores you fully."
    }

    private void startFight(Enemy enemy, int r, int c) {
        fighting = enemy;
        fightRow = r;
        fightCol = c;
    }

    // Gives the player the gold and item of the chest and removes the chest.
    private void openChest(Area area, int r, int c) {
        // TODO: Member 1
        // 1. Treasure treasure = area.chests[r][c]; player.gold += treasure.gold;
        // 2. If treasure.item != null: add it to player.inventory and set lastMessage to
        //    "Found " + name + " (" + stats + "). Added to inventory; equip or use it with i."
        //    else lastMessage = "The chest contained gold only."
        // 3. area.objects[r][c] = Area.NONE; area.chests[r][c] = null;
    }

    // The player's four fight actions. Each one plays a full round: the player acts, then the enemy answers.

    public void combatAttack() {
        // TODO: Member 1
        // 1. If fighting == null, return.   2. startRound();
        // 3. dealt = fighting.takeDamage(player.attackDamage()); crit = player.crit;   4. endRound();
    }

    public void combatSkill(int skillNumber) {
        // TODO: Member 1
        // 1. If fighting == null, return.  2. Skill skill = player.hero.skills[skillNumber];
        // 3. If !player.spendMana(skill.mana), return (nothing happens).  4. startRound();
        // 5. dealt = fighting.takeDamage(skill.damage(player)); crit = player.crit; used = skill.name;  6. endRound();
    }

    public void combatDefend() {
        // TODO: Member 1
        // startRound(); player.guarding = true; player.heal(player.hero.defendHp);
        // player.restoreMana(player.hero.defendMana); endRound();
    }

    public void combatPotion(int inventoryIndex) {
        // TODO: Member 1
        // If fighting == null or !player.canUsePotion(index), return. Otherwise startRound(); usePotion; endRound();
    }

    // Clears the results of the last round and remembers HP and mana so we can show what changed.
    private void startRound() {
        // TODO: Member 1
        // dealt = taken = healed = manaGained = reward = xpGain = 0; crit = blocked = false; used = "";
        // fighting.move = ""; hpBefore = player.hp; manaBefore = player.mana;
    }

    // Finishes the round: the enemy dies (player wins) or answers with its own turn.
    private void endRound() {
        // TODO: Member 1
        // 1. healed = player.hp - hpBefore; manaGained = Math.max(0, player.mana - manaBefore);
        // 2. If fighting.isDead(): win(fighting) and stop.
        // 3. blocked = player.guarding; taken = fighting.act(player); blocked = blocked && taken > 0;
        // 4. If player.isDead(): gameOver = true; fighting = null.
    }

    // The enemy died: give gold and XP and remove it. Killing the boss wins the game.
    private void win(Enemy enemy) {
        // TODO: Member 1
        // 1. Area area = currentArea(); fighting = null; reward = enemy.goldReward; xpGain = enemy.xp;
        // 2. player.gold += reward; player.gainXp(xpGain);
        // 3. If area.type == Area.BOSS: clear the 3 x 3 centre block (objects = NONE) and won = true.
        //    Otherwise: area.objects[fightRow][fightCol] = NONE; area.enemies[fightRow][fightCol] = null; kills++.
    }

    // Buys the shop item at this position if the player has enough gold. Gear is sold only once; potions are not.
    public void buy(int index) {
        // TODO: Member 1
        // 1. Do nothing for a bad index or when player.gold < item.price.
        // 2. player.gold -= price; add the item to the inventory.
        // 3. Remove the item from area.shopStock unless it is a Potion.
    }

    // The letter shown for this area on the small map.
    public static char symbol(Area area) {
        // TODO: Member 1
        // START 'S', BOSS 'B', SHOP 'H', REALM 'L'; otherwise the number of enemies and chests as a digit:
        // (char) ('0' + area.countObjects())
        return '?';
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void need(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    private static Game fresh() {
        Game g = new Game(new Random(3), Hero.WARRIOR);
        g.playerRow = 5;
        g.playerCol = 5;
        return g;
    }

    private static Area find(Game g, int type) {
        for (Area[] row : g.areas) {
            for (Area a : row) {
                if (a != null && a.type == type) return a;
            }
        }
        return null;
    }

    private static Enemy dummy(int hp, int attack, int xp, int gold) {
        return new Enemy("Dummy", hp, attack, 0, xp, gold);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Game.java (Member 1) ===");

        for (int seed = 0; seed < 30; seed++) {
            Game g = new Game(new Random(seed), Hero.MAGE);
            int start = 0, boss = 0, shop = 0, realm = 0, areaCount = 0, mapCount = 0;
            for (int r = 0; r < WorldMap.SIZE; r++) {
                for (int c = 0; c < WorldMap.SIZE; c++) {
                    if (g.world.isArea(r, c)) mapCount++;
                    Area a = g.areas[r][c];
                    if (a == null) continue;
                    areaCount++;
                    if (a.type == Area.START) start++;
                    if (a.type == Area.BOSS) boss++;
                    if (a.type == Area.SHOP) shop++;
                    if (a.type == Area.REALM) realm++;
                }
            }
            need(areaCount == mapCount && start == 1 && boss == 1 && shop == 1 && realm == 1,
                    "seed " + seed + ": one Area per map cell, and exactly one START, BOSS, SHOP and REALM");
            Area bossArea = find(g, Area.BOSS);
            need(bossArea.row == g.world.bossRow && bossArea.col == g.world.bossCol && bossArea.boss != null
                    && bossArea.objects[Area.CENTER_ROW][Area.CENTER_COL] == Area.OBJ_BOSS, "the boss area needs the boss object in the centre");
            need(find(g, Area.SHOP).objects[Area.CENTER_ROW][Area.CENTER_COL] == Area.OBJ_SHOP, "the shop needs OBJ_SHOP in the centre");
            need(find(g, Area.REALM).objects[Area.CENTER_ROW][Area.CENTER_COL] == Area.OBJ_REALM, "the Lost Realm needs OBJ_REALM in the centre");
        }
        System.out.println("[PASS] The dungeon is built: start, boss, shop and Lost Realm exist once");

        for (int seed = 0; seed < 30; seed++) {
            Game g = new Game(new Random(seed), Hero.ROGUE);
            boolean anyEmpty = false;
            for (Area[] row : g.areas) {
                for (Area a : row) {
                    if (a == null || a.type != Area.NORMAL) continue;
                    if (a.countObjects() == 0) anyEmpty = true;
                    for (int r = 0; r < Area.ROWS; r++) {
                        for (int c = 0; c < Area.COLS; c++) {
                            if (a.objects[r][c] == Area.ENEMY) need(a.enemies[r][c] != null, "an ENEMY object needs a real Enemy");
                            if (a.objects[r][c] == Area.CHEST) need(a.chests[r][c] != null, "a CHEST object needs real Treasure");
                        }
                    }
                }
            }
            need(anyEmpty, "seed " + seed + ": at least one normal area must be empty");
        }
        System.out.println("[PASS] Normal areas are filled and at least one is empty");

        Game s = new Game(new Random(3), Hero.WARRIOR);
        need(s.areaRow == s.world.startRow && s.areaCol == s.world.startCol && s.visited[s.areaRow][s.areaCol], "the player starts in the start area, marked visited");
        int firstDoor = -1;
        for (int d = 3; d >= 0; d--) {
            if (s.currentArea().doors[d]) firstDoor = d;
        }
        need(firstDoor >= 0 && s.playerRow == Area.ENTRY_ROW[firstDoor] && s.playerCol == Area.ENTRY_COL[firstDoor],
                "the player must stand at the entry tile of the first door of the start area");
        System.out.println("[PASS] The player starts in the start area next to a door");

        Game g = fresh();
        g.playerRow = 1;
        g.playerCol = 1;
        g.move(0);
        g.move(3);
        need(g.playerRow == 1 && g.playerCol == 1, "walls must block you");
        g.playerRow = 5;
        g.playerCol = 5;
        g.move(1);
        need(g.playerRow == 5 && g.playerCol == 6, "a free tile lets you walk east");
        g.move(0);
        g.move(2);
        g.move(3);
        need(g.playerRow == 5 && g.playerCol == 5, "north, south, west steps should return to the start");
        g.fighting = dummy(10, 0, 0, 0);
        g.move(1);
        need(g.playerCol == 5, "you cannot walk during a fight");
        g.fighting = null;
        int door = -1;
        for (int d = 0; d < 4; d++) {
            if (g.currentArea().doors[d]) door = d;
        }
        int oldRow = g.areaRow, oldCol = g.areaCol, opposite = (door + 2) % 4;
        g.playerRow = Area.ENTRY_ROW[door];
        g.playerCol = Area.ENTRY_COL[door];
        g.move(door);
        need(g.areaRow == oldRow + WorldMap.DR[door] && g.areaCol == oldCol + WorldMap.DC[door], "walking into a door must change the area");
        need(g.playerRow == Area.ENTRY_ROW[opposite] && g.playerCol == Area.ENTRY_COL[opposite], "you must appear at the opposite door of the next area");
        need(g.visited[g.areaRow][g.areaCol], "the new area must be marked visited");
        System.out.println("[PASS] Walls block, free tiles let you walk, doors lead to the next area");

        g = fresh();
        Area area = g.currentArea();
        Treasure t = new Treasure();
        t.gold = 5;
        t.item = Potion.mixed();
        area.objects[5][6] = Area.CHEST;
        area.chests[5][6] = t;
        int gold = g.player.gold, items = g.player.inventory.size();
        g.move(1);
        need(g.player.gold == gold + 5 && g.player.inventory.size() == items + 1 && area.objects[5][6] == Area.NONE
                && area.chests[5][6] == null && g.lastMessage.length() > 0 && g.playerCol == 5, "opening a chest gives gold and the item, removes the chest, sets a message");
        Treasure goldOnly = new Treasure();
        goldOnly.gold = 3;
        area.objects[5][6] = Area.CHEST;
        area.chests[5][6] = goldOnly;
        g.move(1);
        need(g.player.gold == gold + 8 && g.player.inventory.size() == items + 1, "a gold-only chest adds no item");
        area.objects[5][6] = Area.OBJ_REALM;
        int maxHp = g.player.maxHp, maxMana = g.player.maxMana;
        g.move(1);
        need(area.realmUsed && g.player.maxHp == maxHp + 30 && g.player.maxMana == maxMana + 10 && g.lastMessage.length() > 0, "the Lost Realm gives +30 max HP and +10 max MP");
        g.move(1);
        need(g.player.maxHp == maxHp + 30, "the Lost Realm works only once");
        area.objects[5][6] = Area.OBJ_SHOP;
        g.move(1);
        need(g.inShop, "walking into the shop sets inShop");
        g.inShop = false;
        Enemy foe = dummy(50, 1, 5, 5);
        area.objects[5][6] = Area.ENEMY;
        area.enemies[5][6] = foe;
        g.move(1);
        need(g.fighting == foe && g.playerCol == 5, "walking into an enemy starts a fight");
        g = fresh();
        g.currentArea().objects[5][6] = Area.OBJ_BOSS;
        g.currentArea().boss = foe;
        g.move(1);
        need(g.fighting == foe, "walking into the boss object starts a fight with the boss");
        System.out.println("[PASS] Walking into a chest, the Lost Realm, the shop, an enemy and the boss");

        g = fresh();
        Enemy big = dummy(10000, 0, 0, 0);
        g.fighting = big;
        g.combatAttack();
        need(g.dealt > 0 && big.hp == 10000 - g.dealt && g.used.equals("") && g.taken > 0 && g.player.hp == g.player.maxHp - g.taken,
                "an attack hurts the enemy, then the enemy hits back");
        int mana = g.player.mana;
        g.combatSkill(0);
        need(g.used.equals("Shield Slam") && g.player.mana == mana - 15 && g.dealt > 0, "a skill costs mana, deals damage and is named in `used`");
        g.player.mana = 0;
        int enemyHp = big.hp, myHp = g.player.hp;
        g.combatSkill(0);
        need(big.hp == enemyHp && g.player.hp == myHp && g.player.mana == 0, "a skill without enough mana does nothing at all");
        g.player.hp = g.player.maxHp - 25;
        g.combatDefend();
        need(g.healed >= 12 && g.blocked && !g.player.guarding, "Defend heals 12 (Warrior), blocks the next hit and the guard ends after it");
        int potions = g.player.potionCount();
        g.player.hp = 20;
        g.combatPotion(2);
        need(g.player.potionCount() == potions - 1 && g.healed > 0, "a potion in combat is used up and heals");
        g.player.hp = g.player.maxHp;
        g.player.mana = g.player.maxMana;
        enemyHp = big.hp;
        potions = g.player.potionCount();
        g.combatPotion(2);
        need(g.player.potionCount() == potions && big.hp == enemyHp, "a useless potion is refused and the round does not happen");
        System.out.println("[PASS] Combat: attack, skill, defend and potion");

        g = fresh();
        area = g.currentArea();
        Enemy weak = dummy(1, 0, 5, 7);
        area.objects[5][6] = Area.ENEMY;
        area.enemies[5][6] = weak;
        g.move(1);
        gold = g.player.gold;
        g.combatAttack();
        need(g.fighting == null && g.reward == 7 && g.xpGain == 5 && g.player.gold == gold + 7 && g.player.xp == 5 && g.kills == 1
                && area.objects[5][6] == Area.NONE && area.enemies[5][6] == null && g.taken == 0, "a won fight: reward, XP, kill counted, enemy removed, no counter-attack");
        Enemy killer = dummy(1000, 9999, 0, 0);
        g.fighting = killer;
        g.player.hp = 1;
        g.combatAttack();
        need(g.gameOver && g.fighting == null && g.player.isDead(), "when the player dies the game is over");
        g = fresh();
        Area bossArea = find(g, Area.BOSS);
        g.areaRow = bossArea.row;
        g.areaCol = bossArea.col;
        g.fighting = dummy(1, 0, 0, 0);
        g.combatAttack();
        need(g.won && bossArea.objects[Area.CENTER_ROW][Area.CENTER_COL] == Area.NONE && g.kills == 0, "beating the boss wins the game and clears the boss block");
        System.out.println("[PASS] Winning a fight, losing a fight and beating the boss");

        g = fresh();
        Area shop = find(g, Area.SHOP);
        g.areaRow = shop.row;
        g.areaCol = shop.col;
        g.player.gold = 2000;
        int stock = shop.shopStock.size();
        g.buy(0);
        need(g.player.gold == 2000 - 75 && g.player.inventory.get(g.player.inventory.size() - 1).name.equals("Healing Potion") && shop.shopStock.size() == stock,
                "buying a potion costs its price and the potion stays in the shop");
        Item gear = shop.shopStock.get(4);
        g.buy(4);
        need(g.player.inventory.contains(gear) && shop.shopStock.size() == stock - 1 && !shop.shopStock.contains(gear), "gear leaves the shop after it is bought");
        g.player.gold = 0;
        int count = g.player.inventory.size();
        g.buy(0);
        g.buy(-1);
        g.buy(99);
        need(g.player.inventory.size() == count && g.player.gold == 0, "no gold or a bad index means nothing is bought (and nothing crashes)");
        System.out.println("[PASS] Buying: gear is sold once, potions are not, no gold means no sale");

        Area letters = new Area(g.world, 0, 0);
        letters.type = Area.START;
        need(Game.symbol(letters) == 'S', "START is S");
        letters.type = Area.BOSS;
        need(Game.symbol(letters) == 'B', "BOSS is B");
        letters.type = Area.SHOP;
        need(Game.symbol(letters) == 'H', "SHOP is H");
        letters.type = Area.REALM;
        need(Game.symbol(letters) == 'L', "REALM is L");
        letters.type = Area.NORMAL;
        letters.objects[2][2] = Area.ENEMY;
        letters.objects[3][3] = Area.CHEST;
        letters.objects[4][4] = Area.ENEMY;
        need(Game.symbol(letters) == '3', "a normal area shows its enemy and chest count");
        System.out.println("[PASS] Map letters for each kind of area");

        System.out.println("[SUCCESS] Game self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * import java.util.ArrayList;
 * import java.util.Collections;
 * import java.util.List;
 * import java.util.Random;
 *
 * // The whole game state and rules: the dungeon, walking, chests, the shop, the Lost Realm and combat.
 * public class Game {
 *     public WorldMap world = new WorldMap();
 *     public Area[][] areas = new Area[WorldMap.SIZE][WorldMap.SIZE];
 *     public Player player;
 *
 *     public int areaRow, areaCol;      // which area the player is in
 *     public int playerRow, playerCol;  // where the player stands inside that area
 *
 *     public boolean inShop = false;
 *     public boolean gameOver = false;
 *     public boolean won = false;
 *     public String lastMessage = "";   // one-time message the screen shows after an action
 *
 *     public Enemy fighting = null;     // the enemy in the current fight, or null
 *     private int fightRow, fightCol;   // where that enemy stands
 *     public int kills = 0;
 *     public boolean[][] visited = new boolean[WorldMap.SIZE][WorldMap.SIZE];
 *
 *     // Results of the last combat round, read by the combat screen.
 *     public int dealt, taken, healed, manaGained, reward, xpGain;
 *     public boolean crit, blocked;
 *     public String used = "";          // name of the skill used, or "" for a normal attack
 *     private int hpBefore, manaBefore;
 *
 *     // Builds a new dungeon for the chosen hero.
 *     public Game(Random rnd, Hero hero) {
 *         player = new Player(hero);
 *         world.useFixedMap(rnd);
 *
 *         List<Area> normalAreas = createAreas();
 *         placeSpecialAreas(normalAreas, rnd);
 *         fillAreas(normalAreas, rnd);
 *         placePlayerAtStart();
 *     }
 *
 *     // Creates an Area for every cell of the map. Marks the start and boss areas and returns all the others.
 *     private List<Area> createAreas() {
 *         List<Area> others = new ArrayList<Area>();
 *         for (int r = 0; r < WorldMap.SIZE; r++) {
 *             for (int c = 0; c < WorldMap.SIZE; c++) {
 *                 if (!world.isArea(r, c)) {
 *                     continue;
 *                 }
 *                 Area area = new Area(world, r, c);
 *                 areas[r][c] = area;
 *                 if (r == world.startRow && c == world.startCol) {
 *                     area.type = Area.START;
 *                 } else if (r == world.bossRow && c == world.bossCol) {
 *                     area.type = Area.BOSS;
 *                     area.placeCenterObject(Area.OBJ_BOSS);
 *                 } else {
 *                     others.add(area);
 *                 }
 *             }
 *         }
 *         return others;
 *     }
 *
 *     // Randomly turns two of the other areas into the shop (Sacred Exchange) and the Lost Realm.
 *     // The areas left over stay normal and are removed from the list.
 *     private void placeSpecialAreas(List<Area> others, Random rnd) {
 *         Collections.shuffle(others, rnd);
 *         Area shop = others.get(0);
 *         shop.type = Area.SHOP;
 *         shop.placeCenterObject(Area.OBJ_SHOP);
 *         Area realm = others.get(1);
 *         realm.type = Area.REALM;
 *         realm.placeCenterObject(Area.OBJ_REALM);
 *         others.remove(0);
 *         others.remove(0);
 *     }
 *
 *     // Puts enemies and chests in the normal areas and creates their real content.
 *     private void fillAreas(List<Area> normalAreas, Random rnd) {
 *         boolean anyEmpty = false;
 *         for (Area area : normalAreas) {
 *             area.placeRandomObjects(rnd);
 *             if (area.countObjects() == 0) {
 *                 anyEmpty = true;
 *             }
 *         }
 *         // Always keep at least one empty normal area so the player has somewhere safe.
 *         if (!anyEmpty) {
 *             normalAreas.get(0).objects = new int[Area.ROWS][Area.COLS];
 *         }
 *
 *         for (int r = 0; r < WorldMap.SIZE; r++) {
 *             for (int c = 0; c < WorldMap.SIZE; c++) {
 *                 if (areas[r][c] != null) {
 *                     areas[r][c].populate(world.dist[r][c], rnd);
 *                 }
 *             }
 *         }
 *     }
 *
 *     // Puts the player in the start area, next to its first door.
 *     private void placePlayerAtStart() {
 *         areaRow = world.startRow;
 *         areaCol = world.startCol;
 *         visited[areaRow][areaCol] = true;
 *         Area start = areas[areaRow][areaCol];
 *         for (int d = 0; d < 4; d++) {
 *             if (start.doors[d]) {
 *                 playerRow = Area.ENTRY_ROW[d];
 *                 playerCol = Area.ENTRY_COL[d];
 *                 break;
 *             }
 *         }
 *     }
 *
 *     public Area currentArea() {
 *         return areas[areaRow][areaCol];
 *     }
 *
 *     // Tries to walk one step. d = 0 north, 1 east, 2 south, 3 west. Walking into an object uses it.
 *     public void move(int d) {
 *         if (gameOver || won || fighting != null) {
 *             return;
 *         }
 *         Area area = currentArea();
 *         int newRow = playerRow + WorldMap.DR[d];
 *         int newCol = playerCol + WorldMap.DC[d];
 *
 *         boolean outside = newRow < 0 || newRow >= Area.ROWS || newCol < 0 || newCol >= Area.COLS;
 *         if (outside || area.tiles[newRow][newCol] == Area.WALL) {
 *             return;
 *         }
 *         if (area.objects[newRow][newCol] != Area.NONE) {
 *             interact(area, newRow, newCol);
 *             return;
 *         }
 *         if (area.tiles[newRow][newCol] == Area.DOOR) {
 *             // Go through the door into the next area and appear at the opposite door.
 *             areaRow += WorldMap.DR[d];
 *             areaCol += WorldMap.DC[d];
 *             visited[areaRow][areaCol] = true;
 *             int oppositeDoor = (d + 2) % 4;
 *             newRow = Area.ENTRY_ROW[oppositeDoor];
 *             newCol = Area.ENTRY_COL[oppositeDoor];
 *         }
 *         playerRow = newRow;
 *         playerCol = newCol;
 *     }
 *
 *     // The player walked into the object at (r, c): start a fight, open a chest, enter the shop or the Lost Realm.
 *     private void interact(Area area, int r, int c) {
 *         int object = area.objects[r][c];
 *         if (object == Area.ENEMY) {
 *             startFight(area.enemies[r][c], r, c);
 *         } else if (object == Area.OBJ_BOSS) {
 *             startFight(area.boss, r, c);
 *         } else if (object == Area.CHEST) {
 *             openChest(area, r, c);
 *         } else if (object == Area.OBJ_SHOP) {
 *             inShop = true;
 *         } else if (object == Area.OBJ_REALM && !area.realmUsed) {
 *             area.realmUsed = true;
 *             player.enterLostRealm();
 *             lastMessage = "The Lost Realm grants +30 max HP and +10 max MP, and restores you fully.";
 *         }
 *     }
 *
 *     private void startFight(Enemy enemy, int r, int c) {
 *         fighting = enemy;
 *         fightRow = r;
 *         fightCol = c;
 *     }
 *
 *     // Gives the player the gold and item of the chest and removes the chest.
 *     private void openChest(Area area, int r, int c) {
 *         Treasure treasure = area.chests[r][c];
 *         player.gold += treasure.gold;
 *         if (treasure.item != null) {
 *             player.inventory.add(treasure.item);
 *             lastMessage = "Found " + treasure.item.name + " (" + treasure.item.stats() + "). Added to inventory; equip or use it with i.";
 *         } else {
 *             lastMessage = "The chest contained gold only.";
 *         }
 *         area.objects[r][c] = Area.NONE;
 *         area.chests[r][c] = null;
 *     }
 *
 *     // The player's four fight actions. Each one plays a full round: the player acts, then the enemy answers.
 *
 *     public void combatAttack() {
 *         if (fighting == null) return;
 *         startRound();
 *         dealt = fighting.takeDamage(player.attackDamage());
 *         crit = player.crit;
 *         endRound();
 *     }
 *
 *     public void combatSkill(int skillNumber) {
 *         if (fighting == null) return;
 *         Skill skill = player.hero.skills[skillNumber];
 *         if (!player.spendMana(skill.mana)) return;
 *         startRound();
 *         dealt = fighting.takeDamage(skill.damage(player));
 *         crit = player.crit;
 *         used = skill.name;
 *         endRound();
 *     }
 *
 *     public void combatDefend() {
 *         if (fighting == null) return;
 *         startRound();
 *         player.guarding = true;
 *         player.heal(player.hero.defendHp);
 *         player.restoreMana(player.hero.defendMana);
 *         endRound();
 *     }
 *
 *     public void combatPotion(int inventoryIndex) {
 *         if (fighting == null) return;
 *         if (!player.canUsePotion(inventoryIndex)) return;
 *         startRound();
 *         player.usePotion(inventoryIndex);
 *         endRound();
 *     }
 *
 *     // Clears the results of the last round and remembers HP and mana so we can show what changed.
 *     private void startRound() {
 *         dealt = taken = healed = manaGained = reward = xpGain = 0;
 *         crit = blocked = false;
 *         used = "";
 *         fighting.move = "";
 *         hpBefore = player.hp;
 *         manaBefore = player.mana;
 *     }
 *
 *     // Finishes the round: the enemy dies (player wins) or answers with its own turn.
 *     private void endRound() {
 *         healed = player.hp - hpBefore;
 *         manaGained = Math.max(0, player.mana - manaBefore);
 *         if (fighting.isDead()) {
 *             win(fighting);
 *             return;
 *         }
 *
 *         blocked = player.guarding;
 *         taken = fighting.act(player);
 *         blocked = blocked && taken > 0;
 *         if (player.isDead()) {
 *             gameOver = true;
 *             fighting = null;
 *         }
 *     }
 *
 *     // The enemy died: give gold and XP and remove it. Killing the boss wins the game.
 *     private void win(Enemy enemy) {
 *         Area area = currentArea();
 *         fighting = null;
 *         reward = enemy.goldReward;
 *         xpGain = enemy.xp;
 *         player.gold += reward;
 *         player.gainXp(xpGain);
 *         if (area.type == Area.BOSS) {
 *             for (int r = Area.CENTER_ROW - 1; r <= Area.CENTER_ROW + 1; r++) {
 *                 for (int c = Area.CENTER_COL - 1; c <= Area.CENTER_COL + 1; c++) {
 *                     area.objects[r][c] = Area.NONE;
 *                 }
 *             }
 *             won = true;
 *         } else {
 *             area.objects[fightRow][fightCol] = Area.NONE;
 *             area.enemies[fightRow][fightCol] = null;
 *             kills++;
 *         }
 *     }
 *
 *     // Buys the shop item at this position if the player has enough gold. Gear is sold only once; potions are not.
 *     public void buy(int index) {
 *         Area area = currentArea();
 *         if (index < 0 || index >= area.shopStock.size()) {
 *             return;
 *         }
 *         Item item = area.shopStock.get(index);
 *         if (player.gold < item.price) {
 *             return;
 *         }
 *         player.gold -= item.price;
 *         player.inventory.add(item);
 *         if (!(item instanceof Potion)) {
 *             area.shopStock.remove(index);
 *         }
 *     }
 *
 *     // The letter shown for this area on the small map.
 *     public static char symbol(Area area) {
 *         if (area.type == Area.START) return 'S';
 *         if (area.type == Area.BOSS) return 'B';
 *         if (area.type == Area.SHOP) return 'H';
 *         if (area.type == Area.REALM) return 'L';
 *         return (char) ('0' + area.countObjects());
 *     }
 * }
 */
