/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 4 (Adversaries & Game Engine Orchestration)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Game.java
 *
 * Task Description:
 * Master game engine coordinator connecting all subsystems.
 * Orchestrates procedural dungeon initialization, player tile navigation, room transitions,
 * chest loot retrieval, shop transactions, Athena's blessing, and turn-based combat rounds.
 *
 * What to do:
 * 1. Implement constructor Game(boolean randomWorld, Random rnd, Hero hero):
 *    - Builds WorldMap and initializes 2D Area[][] array.
 *    - Places START, BOSS, SHOP, and STATUE special rooms.
 *    - Populates normal areas with monsters and chests scaled by BFS distance.
 * 2. Implement movement logic in move(int d):
 *    - Wall collision detection.
 *    - Room transitions when stepping onto DOOR tiles.
 *    - Triggering interact(...) upon walking into chests, enemies, Hermes, or Athena.
 * 3. Implement turn-based combat mechanics:
 *    - combatAttack(): Player basic attack -> enemy response turn.
 *    - combatSkill(int idx): Validates mana -> executes skill -> enemy response turn.
 *    - combatDefend(): Activates guard stance and class recovery -> absorbs incoming enemy hit.
 *    - combatPotion(int idx): Consumes potion -> enemy response turn.
 * 4. Implement victory and defeat state flags (won, gameOver).
 * 5. Run the main method to verify game engine flow.
 *
 * Expected Output:
 * === Testing Game.java (Member 4) ===
 * [PASS] Game initialized with player placed in start room
 * [PASS] Wall collisions prevent walking out of bounds
 * [PASS] Player movement updates coordinates inside room
 * [PASS] Combat attack round damages enemy and triggers enemy retaliation
 * [PASS] Defend action activates guard stance and applies class recovery
 * [PASS] Victory flag set upon defeating boss Asterion
 * [SUCCESS] Game self-test passed successfully!
 *
 * Hints:
 * - Movement direction d follows {0: North, 1: East, 2: South, 3: West}.
 * - Entering a door transitions playerRow and playerCol to Area.ENTRY_ROW[targetDoor].
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
    
    public int areaRow, areaCol; 
    public int playerRow, playerCol; 
    
    public boolean inShop = false; 
    public boolean gameOver = false; 
    public String lastMessage = ""; 
    
    public Enemy fighting = null; 
    public int dealt, taken, healed, manaGained, reward, xpGain; 
    public boolean crit, blocked; 
    public String used = ""; 
    private int fightRow, fightCol; 
    public boolean won = false; 
    public int kills = 0; 
    public boolean[][] visited = new boolean[WorldMap.SIZE][WorldMap.SIZE]; 
    
    public Game(boolean randomWorld, Random rnd, Hero hero) { 
        player = new Player(hero); 
        
        if (randomWorld) world.generateRandom(rnd); 
        else world.useFixedMap(rnd); 
        
        List<Area> others = new ArrayList<Area>(); 
        for (int r = 0; r < WorldMap.SIZE; r++) { 
            for (int c = 0; c < WorldMap.SIZE; c++) { 
                if (world.isArea(r, c)) { 
                    areas[r][c] = new Area(world, r, c); 
                    boolean isStart = (r == world.startRow && c == world.startCol); 
                    boolean isBoss = (r == world.bossRow && c == world.bossCol); 
                    if (isStart) { 
                        areas[r][c].type = Area.START; 
                    } else if (isBoss) { 
                        areas[r][c].type = Area.BOSS; 
                        areas[r][c].placeCenterObject(Area.OBJ_BOSS); 
                    } else { 
                        others.add(areas[r][c]); 
                    }
                }
            }
        } 
        
        Collections.shuffle(others, rnd); 
        others.get(0).type = Area.SHOP; 
        others.get(0).placeCenterObject(Area.OBJ_SHOP); 
        others.get(1).type = Area.STATUE; 
        others.get(1).placeCenterObject(Area.OBJ_STATUE); 
        
        List<Area> normals = others.subList(2, others.size()); 
        boolean anyEmpty = false; 
        for (Area a : normals) { 
            a.placeRandomObjects(rnd); 
            if (a.countObjects() == 0) anyEmpty = true; 
        }
        if (!anyEmpty) { 
            normals.get(0).objects = new int[Area.ROWS][Area.COLS]; 
        }
        
        for (int r = 0; r < WorldMap.SIZE; r++) { 
            for (int c = 0; c < WorldMap.SIZE; c++) { 
                if (areas[r][c] != null) areas[r][c].populate(world.dist[r][c], rnd); 
            }
        }
        
        areaRow = world.startRow; 
        areaCol = world.startCol; 
        visited[areaRow][areaCol] = true; 
        Area start = areas[areaRow][areaCol]; 
        for (int d = 0; d < 4; d++) { 
            if (start.doors[d]) { 
                playerRow = Area.ENTRY_ROW[d]; 
                playerCol = Area.ENTRY_COL[d]; 
                break; 
            }
        }
    }
    
    public Area currentArea() { 
        return areas[areaRow][areaCol]; 
    }
    
    public void move(int d) { 
        // TODO: Member 4
        // 1. If gameOver || won || fighting != null: return.
        // 2. Area area = currentArea(). Calculate target (nr, nc) using WorldMap.DR[d] and DC[d].
        // 3. Boundary check: return if (nr, nc) is outside Area grid.
        // 4. Wall collision: return if area.tiles[nr][nc] == Area.WALL.
        // 5. Door transition: if tile == Area.DOOR:
        //    - nextAreaRow = areaRow + DR[d], nextAreaCol = areaCol + DC[d].
        //    - If valid, update areaRow, areaCol, visited, and place player at opposite doorway Area.ENTRY_ROW[(d + 2) % 4].
        // 6. Object interaction: if area.objects[nr][nc] != Area.NONE, call interact(area, nr, nc) and return.
        // 7. Otherwise, step player: playerRow = nr, playerCol = nc.
    }
    
    private void interact(Area area, int r, int c) { 
        int obj = area.objects[r][c]; 
        if (obj == Area.ENEMY || obj == Area.OBJ_BOSS) { 
            fighting = (obj == Area.ENEMY) ? area.enemies[r][c] : area.boss; 
            fightRow = r; 
            fightCol = c; 
        } else if (obj == Area.CHEST) { 
            Treasure t = area.chests[r][c]; 
            player.gold += t.gold; 
            if (t.item != null) { 
                player.pickUp(t.item); 
                lastMessage = "Found " + t.item.name + " (" + t.item.stats() + "). Added to inventory; equip or use it with i."; 
            } else { 
                lastMessage = "The chest contained gold only."; 
            }
            area.objects[r][c] = Area.NONE; 
            area.chests[r][c] = null; 
        } else if (obj == Area.OBJ_SHOP) { 
            inShop = true; 
        } else if (obj == Area.OBJ_STATUE && !area.statueUsed) { 
            area.statueUsed = true; 
            player.bless(); 
        }
    }
    
    public void combatAttack()       { combatRound(0); } 
    public void combatSkill(int i)   { combatRound(1 + i); } 
    public void combatDefend()       { combatRound(3); } 
    public void combatPotion()       { combatPotion(player.firstPotionIndex()); } 
    public void combatPotion(int inventoryIndex) { combatRound(4, inventoryIndex); } 
    
    private void combatRound(int action) { 
        combatRound(action, -1); 
    }
    
    private void combatRound(int action, int potionIndex) { 
        // TODO: Member 4
        // 1. If fighting == null: return.
        // 2. Perform player turn based on action (0: attack, 1-2: skill, 3: defend, 4: potion).
        // 3. If enemy dies: call win(e) and return.
        // 4. Perform enemy turn: taken = e.act(p).
        // 5. If player dies: set gameOver = true, fighting = null, return.
        // 6. Call e.startRound().
    }
    
    private void win(Enemy e) { 
        Area area = currentArea(); 
        fighting = null; 
        reward = e.goldReward; 
        xpGain = e.xp; 
        player.gold += reward; 
        player.gainXp(xpGain); 
        if (area.type == Area.BOSS) { 
            for (int rr = Area.CENTER_ROW - 1; rr <= Area.CENTER_ROW + 1; rr++) 
                for (int cc = Area.CENTER_COL - 1; cc <= Area.CENTER_COL + 1; cc++) 
                    area.objects[rr][cc] = Area.NONE; 
            won = true; 
        } else { 
            area.objects[fightRow][fightCol] = Area.NONE; 
            area.enemies[fightRow][fightCol] = null; 
            kills++; 
        }
    }
    
    public void buy(int i) { 
        Area area = currentArea(); 
        if (i < 0 || i >= area.shopStock.size()) return; 
        Item it = area.shopStock.get(i); 
        if (player.gold < it.price) return; 
        player.gold -= it.price; 
        player.pickUp(it); 
        if (it.type != Item.POTION) area.shopStock.remove(i); 
    }
    
    public static char symbol(Area a) { 
        switch (a.type) { 
            case Area.START:  return 'S'; 
            case Area.BOSS:   return 'B'; 
            case Area.SHOP:   return 'H'; 
            case Area.STATUE: return 'T'; 
            default:          return (char) ('0' + a.countObjects()); 
        }
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Game.java (Member 4) ==="); 
        
        Game game = new Game(false, new Random(42), Hero.WARRIOR); 
        if (game.player == null || game.currentArea() == null) { 
            throw new AssertionError("Game initialization failed"); 
        }
        System.out.println("[PASS] Game initialized with player placed in start room"); 
        
        game.playerRow = 1; 
        game.playerCol = 1; 
        game.move(0); 
        if (game.playerRow != 1 || game.playerCol != 1) { 
            throw new AssertionError("Player should not walk through top border wall"); 
        }
        System.out.println("[PASS] Wall collisions prevent walking out of bounds"); 
        
        game.move(2); 
        if (game.playerRow != 2 || game.playerCol != 1) { 
            throw new AssertionError("Player did not move down to empty floor tile"); 
        }
        System.out.println("[PASS] Player movement updates coordinates inside room"); 
        
        game.fighting = new Enemy("Test Dummy", 50, 10, 5, 20, 15); 
        game.combatAttack(); 
        if (game.dealt <= 0 || game.fighting.hp >= 50) { 
            throw new AssertionError("combatAttack failed to damage enemy"); 
        }
        System.out.println("[PASS] Combat attack round damages enemy and triggers enemy retaliation"); 
        
        game.player.hp -= 20; 
        game.combatDefend(); 
        if (game.healed != Hero.WARRIOR.defendHp) { 
            throw new AssertionError("combatDefend failed to apply Warrior HP recovery"); 
        }
        System.out.println("[PASS] Defend action activates guard stance and applies class recovery"); 
        
        game.areaRow = game.world.bossRow; 
        game.areaCol = game.world.bossCol; 
        game.fighting = game.currentArea().boss; 
        game.fighting.hp = 1; 
        game.combatAttack(); 
        if (!game.won) throw new AssertionError("Victory condition not triggered after boss defeat"); 
        System.out.println("[PASS] Victory flag set upon defeating boss Asterion"); 
        
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
 * import java.util.*;
 *
 * public class Game {
 *     public WorldMap world = new WorldMap();
 *     public Area[][] areas = new Area[WorldMap.SIZE][WorldMap.SIZE];
 *     public Player player;
 *     public int areaRow, areaCol;
 *     public int playerRow, playerCol;
 *     public boolean inShop = false;
 *     public boolean gameOver = false;
 *     public String lastMessage = "";
 *     public Enemy fighting = null;
 *     public int dealt, taken, healed, manaGained, reward, xpGain;
 *     public boolean crit, blocked;
 *     public String used = "";
 *     private int fightRow, fightCol;
 *     public boolean won = false;
 *     public int kills = 0;
 *     public boolean[][] visited = new boolean[WorldMap.SIZE][WorldMap.SIZE];
 *
 *     public Game(boolean randomWorld, Random rnd, Hero hero) {
 *         player = new Player(hero);
 *         if (randomWorld) world.generateRandom(rnd);
 *         else world.useFixedMap(rnd);
 *
 *         List<Area> others = new ArrayList<Area>();
 *         for (int r = 0; r < WorldMap.SIZE; r++) {
 *             for (int c = 0; c < WorldMap.SIZE; c++) {
 *                 if (world.isArea(r, c)) {
 *                     areas[r][c] = new Area(world, r, c);
 *                     boolean isStart = (r == world.startRow && c == world.startCol);
 *                     boolean isBoss = (r == world.bossRow && c == world.bossCol);
 *                     if (isStart) {
 *                         areas[r][c].type = Area.START;
 *                     } else if (isBoss) {
 *                         areas[r][c].type = Area.BOSS;
 *                         areas[r][c].placeCenterObject(Area.OBJ_BOSS);
 *                     } else {
 *                         others.add(areas[r][c]);
 *                     }
 *                 }
 *             }
 *         }
 *
 *         Collections.shuffle(others, rnd);
 *         others.get(0).type = Area.SHOP;
 *         others.get(0).placeCenterObject(Area.OBJ_SHOP);
 *         others.get(1).type = Area.STATUE;
 *         others.get(1).placeCenterObject(Area.OBJ_STATUE);
 *
 *         List<Area> normals = others.subList(2, others.size());
 *         boolean anyEmpty = false;
 *         for (Area a : normals) {
 *             a.placeRandomObjects(rnd);
 *             if (a.countObjects() == 0) anyEmpty = true;
 *         }
 *         if (!anyEmpty) {
 *             normals.get(0).objects = new int[Area.ROWS][Area.COLS];
 *         }
 *
 *         for (int r = 0; r < WorldMap.SIZE; r++) {
 *             for (int c = 0; c < WorldMap.SIZE; c++) {
 *                 if (areas[r][c] != null) areas[r][c].populate(world.dist[r][c], rnd);
 *             }
 *         }
 *
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
 *     public void move(int d) {
 *         if (gameOver || won || fighting != null) return;
 *         Area area = currentArea();
 *         int nr = playerRow + WorldMap.DR[d];
 *         int nc = playerCol + WorldMap.DC[d];
 *         if (nr < 0 || nr >= Area.ROWS || nc < 0 || nc >= Area.COLS) return;
 *         int tile = area.tiles[nr][nc];
 *         if (tile == Area.WALL) return;
 *         if (tile == Area.DOOR) {
 *             int nextAreaRow = areaRow + WorldMap.DR[d];
 *             int nextAreaCol = areaCol + WorldMap.DC[d];
 *             if (world.isArea(nextAreaRow, nextAreaCol)) {
 *                 areaRow = nextAreaRow;
 *                 areaCol = nextAreaCol;
 *                 visited[areaRow][areaCol] = true;
 *                 int entrySide = (d + 2) % 4;
 *                 playerRow = Area.ENTRY_ROW[entrySide];
 *                 playerCol = Area.ENTRY_COL[entrySide];
 *             }
 *             return;
 *         }
 *         if (area.objects[nr][nc] != Area.NONE) {
 *             interact(area, nr, nc);
 *             return;
 *         }
 *         playerRow = nr;
 *         playerCol = nc;
 *     }
 *
 *     private void interact(Area area, int r, int c) {
 *         int obj = area.objects[r][c];
 *         if (obj == Area.ENEMY || obj == Area.OBJ_BOSS) {
 *             fighting = (obj == Area.ENEMY) ? area.enemies[r][c] : area.boss;
 *             fightRow = r;
 *             fightCol = c;
 *         } else if (obj == Area.CHEST) {
 *             Treasure t = area.chests[r][c];
 *             player.gold += t.gold;
 *             if (t.item != null) {
 *                 player.pickUp(t.item);
 *                 lastMessage = "Found " + t.item.name + " (" + t.item.stats() + "). Added to inventory; equip or use it with i.";
 *             } else {
 *                 lastMessage = "The chest contained gold only.";
 *             }
 *             area.objects[r][c] = Area.NONE;
 *             area.chests[r][c] = null;
 *         } else if (obj == Area.OBJ_SHOP) {
 *             inShop = true;
 *         } else if (obj == Area.OBJ_STATUE && !area.statueUsed) {
 *             area.statueUsed = true;
 *             player.bless();
 *         }
 *     }
 *
 *     public void combatAttack()       { combatRound(0); }
 *     public void combatSkill(int i)   { combatRound(1 + i); }
 *     public void combatDefend()       { combatRound(3); }
 *     public void combatPotion()       { combatPotion(player.firstPotionIndex()); }
 *     public void combatPotion(int inventoryIndex) { combatRound(4, inventoryIndex); }
 *
 *     private void combatRound(int action) { combatRound(action, -1); }
 *
 *     private void combatRound(int action, int potionIndex) {
 *         if (fighting == null) return;
 *         Enemy e = fighting;
 *         Player p = player;
 *         int hp0 = p.hp, mana0 = p.mana;
 *         dealt = taken = healed = manaGained = reward = xpGain = 0;
 *         crit = blocked = false;
 *         used = "";
 *         e.move = "";
 *         if (action == 0) {
 *             dealt = e.takeDamage(p.attackDamage());
 *             crit = p.crit;
 *         } else if (action <= 2) {
 *             Skill s = p.hero.skills[action - 1];
 *             if (!p.spendMana(s.mana)) return;
 *             dealt = e.takeDamage(s.damage(p));
 *             crit = p.crit;
 *             used = s.label;
 *         } else if (action == 3) {
 *             p.guarding = true;
 *             p.heal(p.hero.defendHp);
 *             p.restoreMana(p.hero.defendMana);
 *         } else if (!p.usePotion(potionIndex)) {
 *             return;
 *         }
 *         healed = p.hp - hp0;
 *         manaGained = Math.max(0, p.mana - mana0);
 *         if (e.isDead()) { win(e); return; }
 *         blocked = p.guarding;
 *         taken = e.act(p);
 *         blocked = blocked && taken > 0;
 *         if (p.isDead()) {
 *             gameOver = true;
 *             fighting = null;
 *             return;
 *         }
 *         e.startRound();
 *     }
 *
 *     private void win(Enemy e) {
 *         Area area = currentArea();
 *         fighting = null;
 *         reward = e.goldReward;
 *         xpGain = e.xp;
 *         player.gold += reward;
 *         player.gainXp(xpGain);
 *         if (area.type == Area.BOSS) {
 *             for (int rr = Area.CENTER_ROW - 1; rr <= Area.CENTER_ROW + 1; rr++)
 *                 for (int cc = Area.CENTER_COL - 1; cc <= Area.CENTER_COL + 1; cc++)
 *                     area.objects[rr][cc] = Area.NONE;
 *             won = true;
 *         } else {
 *             area.objects[fightRow][fightCol] = Area.NONE;
 *             area.enemies[fightRow][fightCol] = null;
 *             kills++;
 *         }
 *     }
 *
 *     public void buy(int i) {
 *         Area area = currentArea();
 *         if (i < 0 || i >= area.shopStock.size()) return;
 *         Item it = area.shopStock.get(i);
 *         if (player.gold < it.price) return;
 *         player.gold -= it.price;
 *         player.pickUp(it);
 *         if (it.type != Item.POTION) area.shopStock.remove(i);
 *     }
 *
 *     public static char symbol(Area a) {
 *         switch (a.type) {
 *             case Area.START:  return 'S';
 *             case Area.BOSS:   return 'B';
 *             case Area.SHOP:   return 'H';
 *             case Area.STATUE: return 'T';
 *             default:          return (char) ('0' + a.countObjects());
 *         }
 *     }
 * }
 */
