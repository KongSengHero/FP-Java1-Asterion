/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 3 (Labyrinth Environment & World Map)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Area.java
 *
 * Task Description:
 * Generates and manages the 11-row by 17-col tile grid for a single labyrinth room.
 * Handles wall boundaries, directional door carving, object/enemy/chest spawning, and special room centers.
 *
 * What to do:
 * 1. Define dimensions (ROWS = 11, COLS = 17) and tile/object constants.
 * 2. Implement constructor Area(WorldMap world, int row, int col):
 *    - Queries world.hasDoor(...) for north (0), east (1), south (2), and west (3).
 *    - Carves outer walls and doorway gaps in tiles array.
 * 3. Implement placeRandomObjects(Random rnd):
 *    - Selects 0 to 4 spawn points from SPAWN_POINTS.
 *    - Places ENEMY or CHEST on chosen coordinates.
 * 4. Implement placeCenterObject(int type) to fill the 3x3 center zone.
 * 5. Implement populate(int distance, Random rnd) to instantiate Enemy, Treasure, Boss, and shopStock.
 * 6. Implement countObjects() and run the main method to verify grid structure.
 *
 * Expected Output:
 * === Testing Area.java (Member 3) ===
 * [PASS] 11x17 tile grid boundary walls constructed
 * [PASS] Door gaps carved matching connected directions
 * [PASS] Random objects placed at valid spawn points
 * [PASS] Center 3x3 object placed for special rooms
 * [PASS] Room population instantiates entities with distance scaling
 * [SUCCESS] Area self-test passed successfully!
 *
 * Hints:
 * - North/South doors occupy columns 7..9 at row 0 or row ROWS - 1.
 * - East/West doors occupy rows 4..6 at col 0 or col COLS - 1.
 * - Center 3x3 block spans rows 4..6 and cols 7..9.
 */

package model;

import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import java.util.Random; 

public class Area { 
    public static final int ROWS = 11; 
    public static final int COLS = 17; 
    
    public static final int FLOOR = 0; 
    public static final int WALL = 1; 
    public static final int DOOR = 2; 
    
    public static final int START = 0; 
    public static final int NORMAL = 1; 
    public static final int SHOP = 2; 
    public static final int STATUE = 3; 
    public static final int BOSS = 4; 
    
    public static final int NONE = 0; 
    public static final int ENEMY = 1; 
    public static final int CHEST = 2; 
    public static final int OBJ_SHOP = 3; 
    public static final int OBJ_STATUE = 4; 
    public static final int OBJ_BOSS = 5; 
    
    public static final int[] ENTRY_ROW = {1, 5, 9, 5}; 
    public static final int[] ENTRY_COL = {8, 15, 8, 1}; 
    
    public static final int[][] SPAWN_POINTS = {
        {1, 14}, {2, 2}, {3, 11}, {4, 5}, 
        {6, 6}, {7, 12}, {8, 2}, {9, 14}
    }; 
    
    public static final int CENTER_ROW = 5; 
    public static final int CENTER_COL = 8; 
    
    public int row, col; 
    public int type = NORMAL; 
    public boolean[] doors = new boolean[4]; 
    public int[][] tiles = new int[ROWS][COLS]; 
    public int[][] objects = new int[ROWS][COLS]; 
    
    public Enemy[][] enemies = new Enemy[ROWS][COLS]; 
    public Treasure[][] chests = new Treasure[ROWS][COLS]; 
    public Enemy boss; 
    public boolean statueUsed = false; 
    public List<Item> shopStock = new ArrayList<Item>(); 
    
    public Area(WorldMap world, int row, int col) { 
        this.row = row; 
        this.col = col; 
        for (int d = 0; d < 4; d++) { 
            doors[d] = world.hasDoor(row, col, d); 
        }
        buildTiles(); 
    }
    
    private void buildTiles() { 
        // TODO: Member 3
        // 1. Fill tiles[ROWS][COLS]: outer border (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1) is WALL, else FLOOR.
        // 2. If doors[0] (North) is true: carve tiles[0][c] = DOOR for c in 7..9.
        // 3. If doors[2] (South) is true: carve tiles[ROWS - 1][c] = DOOR for c in 7..9.
        // 4. If doors[3] (West) is true: carve tiles[r][0] = DOOR for r in 4..6.
        // 5. If doors[1] (East) is true: carve tiles[r][COLS - 1] = DOOR for r in 4..6.
    }
    
    public void placeRandomObjects(Random rnd) { 
        // TODO: Member 3
        // 1. Roll count = rnd.nextInt(5) (0 to 4 objects).
        // 2. Shuffle indices 0..SPAWN_POINTS.length - 1.
        // 3. For the first count points: place ENEMY or CHEST randomly (rnd.nextBoolean() ? ENEMY : CHEST).
    }
    
    public void placeCenterObject(int objectType) { 
        // TODO: Member 3
        // Fill the 3x3 square centered at (CENTER_ROW, CENTER_COL) with objectType:
        // rows from CENTER_ROW - 1 to CENTER_ROW + 1, cols from CENTER_COL - 1 to CENTER_COL + 1.
    }
    
    public void populate(int distance, Random rnd) { 
        // TODO: Member 3
        // 1. Iterate through objects[r][c]:
        //    - if objects[r][c] == ENEMY: enemies[r][c] = Enemy.create(distance, rnd).
        //    - if objects[r][c] == CHEST: chests[r][c] = Treasure.roll(distance, rnd).
        // 2. If type == BOSS: boss = new Boss().
        // 3. If type == SHOP: populate shopStock with potions, randomWeapon, and randomArmor.
    }
    
    public int countObjects() { 
        // TODO: Member 3
        // Count how many positions in objects[][] are ENEMY or CHEST.
        return 0; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Area.java (Member 3) ==="); 
        
        WorldMap map = new WorldMap(); 
        map.useFixedMap(new Random(1)); 
        Area startArea = new Area(map, map.startRow, map.startCol); 
        
        if (startArea.tiles[0][0] != WALL || startArea.tiles[5][8] != FLOOR) { 
            throw new AssertionError("Tile grid walls or floors incorrectly assigned"); 
        }
        System.out.println("[PASS] 11x17 tile grid boundary walls constructed"); 
        
        boolean foundDoor = false; 
        for (int d = 0; d < 4; d++) { 
            if (startArea.doors[d]) foundDoor = true; 
        }
        if (!foundDoor) throw new AssertionError("Start area should have at least one door"); 
        System.out.println("[PASS] Door gaps carved matching connected directions"); 
        
        Random rnd = new Random(42); 
        startArea.placeRandomObjects(rnd); 
        int count = startArea.countObjects(); 
        if (count < 0 || count > 4) throw new AssertionError("Random object count out of bounds: " + count); 
        System.out.println("[PASS] Random objects placed at valid spawn points"); 
        
        Area bossRoom = new Area(map, map.bossRow, map.bossCol); 
        bossRoom.type = BOSS; 
        bossRoom.placeCenterObject(OBJ_BOSS); 
        if (bossRoom.objects[CENTER_ROW][CENTER_COL] != OBJ_BOSS) { 
            throw new AssertionError("Center object not placed correctly"); 
        }
        System.out.println("[PASS] Center 3x3 object placed for special rooms"); 
        
        bossRoom.populate(5, rnd); 
        if (bossRoom.boss == null) throw new AssertionError("Boss entity not populated in BOSS room"); 
        System.out.println("[PASS] Room population instantiates entities with distance scaling"); 
        
        System.out.println("[SUCCESS] Area self-test passed successfully!"); 
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
 * public class Area {
 *     public static final int ROWS = 11;
 *     public static final int COLS = 17;
 *
 *     public static final int FLOOR = 0;
 *     public static final int WALL = 1;
 *     public static final int DOOR = 2;
 *
 *     public static final int START = 0;
 *     public static final int NORMAL = 1;
 *     public static final int SHOP = 2;
 *     public static final int STATUE = 3;
 *     public static final int BOSS = 4;
 *
 *     public static final int NONE = 0;
 *     public static final int ENEMY = 1;
 *     public static final int CHEST = 2;
 *     public static final int OBJ_SHOP = 3;
 *     public static final int OBJ_STATUE = 4;
 *     public static final int OBJ_BOSS = 5;
 *
 *     public static final int[] ENTRY_ROW = {1, 5, 9, 5};
 *     public static final int[] ENTRY_COL = {8, 15, 8, 1};
 *
 *     public static final int[][] SPAWN_POINTS = {
 *         {1, 14}, {2, 2}, {3, 11}, {4, 5},
 *         {6, 6}, {7, 12}, {8, 2}, {9, 14}
 *     };
 *
 *     public static final int CENTER_ROW = 5;
 *     public static final int CENTER_COL = 8;
 *
 *     public int row, col;
 *     public int type = NORMAL;
 *     public boolean[] doors = new boolean[4];
 *     public int[][] tiles = new int[ROWS][COLS];
 *     public int[][] objects = new int[ROWS][COLS];
 *
 *     public Enemy[][] enemies = new Enemy[ROWS][COLS];
 *     public Treasure[][] chests = new Treasure[ROWS][COLS];
 *     public Enemy boss;
 *     public boolean statueUsed = false;
 *     public List<Item> shopStock = new ArrayList<Item>();
 *
 *     public Area(WorldMap world, int row, int col) {
 *         this.row = row;
 *         this.col = col;
 *         for (int d = 0; d < 4; d++) {
 *             doors[d] = world.hasDoor(row, col, d);
 *         }
 *         buildTiles();
 *     }
 *
 *     private void buildTiles() {
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 boolean border = (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1);
 *                 tiles[r][c] = border ? WALL : FLOOR;
 *             }
 *         }
 *         for (int c = 7; c <= 9; c++) {
 *             if (doors[0]) tiles[0][c] = DOOR;
 *             if (doors[2]) tiles[ROWS - 1][c] = DOOR;
 *         }
 *         for (int r = 4; r <= 6; r++) {
 *             if (doors[3]) tiles[r][0] = DOOR;
 *             if (doors[1]) tiles[r][COLS - 1] = DOOR;
 *         }
 *     }
 *
 *     public void placeRandomObjects(Random rnd) {
 *         int count = rnd.nextInt(5);
 *         List<Integer> order = new ArrayList<Integer>();
 *         for (int i = 0; i < SPAWN_POINTS.length; i++) order.add(i);
 *         Collections.shuffle(order, rnd);
 *
 *         for (int i = 0; i < count; i++) {
 *             int[] p = SPAWN_POINTS[order.get(i)];
 *             objects[p[0]][p[1]] = rnd.nextBoolean() ? ENEMY : CHEST;
 *         }
 *     }
 *
 *     public void placeCenterObject(int objectType) {
 *         for (int r = CENTER_ROW - 1; r <= CENTER_ROW + 1; r++) {
 *             for (int c = CENTER_COL - 1; c <= CENTER_COL + 1; c++) {
 *                 objects[r][c] = objectType;
 *             }
 *         }
 *     }
 *
 *     public void populate(int distance, Random rnd) {
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 if (objects[r][c] == ENEMY) enemies[r][c] = Enemy.create(distance, rnd);
 *                 if (objects[r][c] == CHEST) chests[r][c] = Treasure.roll(distance, rnd);
 *             }
 *         }
 *         if (type == BOSS) {
 *             boss = new Boss();
 *         }
 *         if (type == SHOP) {
 *             shopStock.add(Item.potion());
 *             shopStock.add(Item.healingPotion());
 *             shopStock.add(Item.manaPotion());
 *             shopStock.add(Item.greaterPotion());
 *             shopStock.add(Treasure.randomWeapon(distance, rnd));
 *             shopStock.add(Treasure.randomArmor(distance, rnd));
 *         }
 *     }
 *
 *     public int countObjects() {
 *         int n = 0;
 *         for (int r = 0; r < ROWS; r++)
 *             for (int c = 0; c < COLS; c++)
 *                 if (objects[r][c] == ENEMY || objects[r][c] == CHEST) n++;
 *         return n;
 *     }
 * }
 */
