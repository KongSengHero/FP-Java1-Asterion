/*
 * Team Project: Void-Dungeon
 * Member: Member 1 (Game Engine, Map & Screens)
 *
 * File: Area.java
 *
 * Task Description:
 * One area (room) of the dungeon: an 11 x 17 tile map with walls, doors, and what stands inside.
 * The constants and the fixed layouts are already typed in. You build the room.
 *
 * What to do:
 * 1. Constructor: remember row and col, set doors[d] to true when the neighbor in direction d is an area
 *    (use world.isArea(row + WorldMap.DR[d], col + WorldMap.DC[d])), then call buildTiles().
 * 2. buildTiles(): WALL on the border, FLOOR inside. Then carve the doors (DOOR tiles):
 *      north door: tiles[0][7], [0][8], [0][9]            south door: tiles[ROWS - 1][7..9]
 *      west door:  tiles[4][0], [5][0], [6][0]            east door:  tiles[4..6][COLS - 1]
 *    Only for the sides where doors[d] is true (0 north, 1 east, 2 south, 3 west).
 * 3. placeRandomObjects(rnd): pick one fixed layout, find its 'E' spots, shuffle them, and put
 *    0 to 4 objects (count = rnd.nextInt(5)) on the first spots: ENEMY or CHEST (coin flip with rnd.nextBoolean()).
 * 4. placeCenterObject(type): fill the 3 x 3 block around (CENTER_ROW, CENTER_COL) with that object.
 * 5. populate(distance, rnd): create the real contents (see the comments in the method).
 * 6. countObjects(): number of ENEMY and CHEST objects in the area.
 *
 * Expected Output:
 * === Testing Area.java (Member 1) ===
 * [PASS] Walls on the border, floor inside
 * [PASS] Doors are carved only where a neighbor area exists
 * [PASS] placeCenterObject fills the 3 x 3 block in the middle
 * [PASS] placeRandomObjects puts 0 to 4 enemies/chests on the allowed spots
 * [PASS] countObjects counts enemies and chests only
 * [PASS] populate creates enemies, chests, the boss and the shop stock
 * [SUCCESS] Area self-test passed successfully!
 *
 * Hints:
 * - Collections.shuffle(spots, rnd) shuffles a list with your random generator.
 * - Layout rows are Strings: layout[r].charAt(c) == 'E' tells you if (r, c) is a spot.
 * - The shop stock is, in this order: Potion.healing(), Potion.mana(), Potion.mixed(), Potion.great(),
 *   then Loot.shopWeapon(distance, rnd) and Loot.shopArmor(distance, rnd).
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Area {
    public static final int ROWS = 11;
    public static final int COLS = 17;

    // Tile values.
    public static final int FLOOR = 0;
    public static final int WALL = 1;
    public static final int DOOR = 2;

    // Area types.
    public static final int START = 0;
    public static final int NORMAL = 1;
    public static final int SHOP = 2;
    public static final int REALM = 3;
    public static final int BOSS = 4;

    // Objects standing on the floor.
    public static final int NONE = 0;
    public static final int ENEMY = 1;
    public static final int CHEST = 2;
    public static final int OBJ_SHOP = 3;
    public static final int OBJ_REALM = 4;
    public static final int OBJ_BOSS = 5;

    // Where the player appears when entering through a door (index = 0 north, 1 east, 2 south, 3 west).
    public static final int[] ENTRY_ROW = {1, 5, 9, 5};
    public static final int[] ENTRY_COL = {8, 15, 8, 1};

    // Spots ('E') where enemies and chests may stand. One layout is picked per area.
    public static final String[][] FIXED_MAPS = {
        {
            "#################",
            "#.............E.#",
            "#.E.............#",
            "#.........E.....#",
            "#...E...........#",
            "#...............#",
            "#....E..........#",
            "#...........E...#",
            "#.E.............#",
            "#.............E.#",
            "#################"
        },
        {
            "#################",
            "#.E.............#",
            "#.............E.#",
            "#.....E.........#",
            "#...........E...#",
            "#...............#",
            "#..........E....#",
            "#...E...........#",
            "#.............E.#",
            "#.E.............#",
            "#################"
        },
        {
            "#################",
            "#.............E.#",
            "#.E.............#",
            "#...........E...#",
            "#....E..........#",
            "#...............#",
            "#...E...........#",
            "#.........E.....#",
            "#.E.............#",
            "#.............E.#",
            "#################"
        }
    };

    public static final int CENTER_ROW = 5;
    public static final int CENTER_COL = 8;

    public int row, col;                    // position in the big map
    public int type = NORMAL;
    public boolean[] doors = new boolean[4];  // door on the north, east, south, west side
    public int[][] tiles = new int[ROWS][COLS];
    public int[][] objects = new int[ROWS][COLS];

    public Enemy[][] enemies = new Enemy[ROWS][COLS];
    public Treasure[][] chests = new Treasure[ROWS][COLS];
    public Enemy boss;
    public boolean realmUsed = false;
    public List<Item> shopStock = new ArrayList<Item>();

    public Area(WorldMap world, int row, int col) {
        // TODO: Member 1
        // 1. Store row and col.
        // 2. For d = 0..3: doors[d] = world.isArea(row + WorldMap.DR[d], col + WorldMap.DC[d]);
        // 3. Call buildTiles().
    }

    // Fills the tiles: walls around the edge, floor inside, and doors where a neighbor area exists.
    private void buildTiles() {
        // TODO: Member 1
        // 1. Two nested loops over every row r and column c. On the border (r == 0, r == ROWS - 1,
        //    c == 0 or c == COLS - 1) set WALL, otherwise FLOOR.
        // 2. Carve the doors (see "What to do" step 2).
    }

    // Puts 0 to 4 enemies or chests on random spots of one of the fixed layouts.
    public void placeRandomObjects(Random rnd) {
        // TODO: Member 1
        // 1. String[] layout = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
        // 2. Make a List<int[]> spots with {r, c} for every 'E' in the layout.
        // 3. Collections.shuffle(spots, rnd);
        // 4. int count = rnd.nextInt(5); for the first `count` spots: objects[r][c] = ENEMY or CHEST (rnd.nextBoolean()).
    }

    // Fills a 3 x 3 block in the middle of the area with one object (shop, Lost Realm or boss).
    public void placeCenterObject(int objectType) {
        // TODO: Member 1
    }

    // Creates the real enemies, chest contents, boss and shop stock. distance = steps from the start area.
    public void populate(int distance, Random rnd) {
        // TODO: Member 1
        // 1. For every tile: if objects[r][c] == ENEMY then enemies[r][c] = Enemy.create(distance, rnd);
        //    if it is a CHEST then chests[r][c] = Loot.rollChest(distance, rnd).
        // 2. If type == BOSS then boss = new Boss().
        // 3. If type == SHOP then fill shopStock (see Hints).
    }

    // Number of enemies and chests still in this area.
    public int countObjects() {
        // TODO: Member 1
        return 0;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void need(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Area.java (Member 1) ===");

        WorldMap world = new WorldMap();
        world.map = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 2, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };
        Area a = new Area(world, 2, 2);
        need(a.row == 2 && a.col == 2, "the constructor must store row and col");
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean border = r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1;
                boolean doorCell = (r == 0 && c >= 7 && c <= 9) || (c == 0 && r >= 4 && r <= 6);
                if (!border) need(a.tiles[r][c] == FLOOR, "inside tiles must be FLOOR at " + r + "," + c);
                else if (!doorCell) need(a.tiles[r][c] == WALL, "border tiles must be WALL at " + r + "," + c);
            }
        }
        System.out.println("[PASS] Walls on the border, floor inside");

        need(a.doors[0] && a.doors[3] && !a.doors[1] && !a.doors[2], "area (2,2) has neighbors north and west only");
        for (int c = 7; c <= 9; c++) need(a.tiles[0][c] == DOOR && a.tiles[ROWS - 1][c] == WALL, "north door at row 0, cols 7-9 and a wall on the south");
        for (int r = 4; r <= 6; r++) need(a.tiles[r][0] == DOOR && a.tiles[r][COLS - 1] == WALL, "west door at col 0, rows 4-6 and a wall on the east");
        System.out.println("[PASS] Doors are carved only where a neighbor area exists");

        a.placeCenterObject(OBJ_SHOP);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean center = r >= CENTER_ROW - 1 && r <= CENTER_ROW + 1 && c >= CENTER_COL - 1 && c <= CENTER_COL + 1;
                need(a.objects[r][c] == (center ? OBJ_SHOP : NONE), "the 3 x 3 centre block (and only it) should hold the object, wrong at " + r + "," + c);
            }
        }
        need(a.countObjects() == 0, "shop objects are not counted by countObjects");
        System.out.println("[PASS] placeCenterObject fills the 3 x 3 block in the middle");

        boolean sawZero = false, sawFour = false;
        for (int seed = 0; seed < 300; seed++) {
            Area b = new Area(world, 2, 2);
            b.placeRandomObjects(new Random(seed));
            int count = 0;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (b.objects[r][c] != NONE) {
                        count++;
                        need(b.objects[r][c] == ENEMY || b.objects[r][c] == CHEST, "random objects must be ENEMY or CHEST");
                        boolean allowed = false;
                        for (String[] layout : FIXED_MAPS) {
                            if (layout[r].charAt(c) == 'E') allowed = true;
                        }
                        need(allowed, "an object stands on a tile that is not an 'E' spot: " + r + "," + c);
                    }
                }
            }
            need(count <= 4, "at most 4 objects, got " + count);
            if (count == 0) sawZero = true;
            if (count == 4) sawFour = true;
        }
        need(sawZero && sawFour, "over many areas you should see both empty areas and areas with 4 objects");
        System.out.println("[PASS] placeRandomObjects puts 0 to 4 enemies/chests on the allowed spots");

        Area counted = new Area(world, 2, 2);
        counted.objects[2][2] = ENEMY;
        counted.objects[3][3] = CHEST;
        counted.objects[4][4] = OBJ_REALM;
        need(counted.countObjects() == 2, "countObjects should count 1 enemy + 1 chest, got " + counted.countObjects());
        System.out.println("[PASS] countObjects counts enemies and chests only");

        Area p = new Area(world, 2, 2);
        p.objects[2][2] = ENEMY;
        p.objects[3][3] = CHEST;
        p.populate(2, new Random(1));
        need(p.enemies[2][2] != null && p.chests[3][3] != null, "populate must create the enemy and the chest");
        need(p.enemies[3][3] == null && p.chests[2][2] == null, "populate must not create anything on the wrong tiles");
        need(p.boss == null && p.shopStock.isEmpty(), "a normal area has no boss and no shop stock");
        Area bossArea = new Area(world, 2, 2);
        bossArea.type = BOSS;
        bossArea.populate(5, new Random(1));
        need(bossArea.boss != null && bossArea.boss.name.equals("Void Dragon"), "a BOSS area needs the Void Dragon");
        Area shop = new Area(world, 2, 2);
        shop.type = SHOP;
        shop.populate(4, new Random(1));
        need(shop.shopStock.size() == 6, "the shop must stock 6 items, got " + shop.shopStock.size());
        need(shop.shopStock.get(0).name.equals("Healing Potion") && shop.shopStock.get(1).name.equals("Mana Potion")
                && shop.shopStock.get(2).name.equals("Mixed Potion") && shop.shopStock.get(3).name.equals("Great Potion"),
                "the shop starts with the four potions in this order: Healing, Mana, Mixed, Great");
        need(shop.shopStock.get(4) instanceof Weapon && shop.shopStock.get(5) instanceof Armor, "then one weapon and one armor");
        System.out.println("[PASS] populate creates enemies, chests, the boss and the shop stock");

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
 * import java.util.ArrayList;
 * import java.util.Collections;
 * import java.util.List;
 * import java.util.Random;
 *
 * // One area of the dungeon: its doors, its 11 x 17 tile map, and what is inside.
 * public class Area {
 *     public static final int ROWS = 11;
 *     public static final int COLS = 17;
 *
 *     // Tile values.
 *     public static final int FLOOR = 0;
 *     public static final int WALL = 1;
 *     public static final int DOOR = 2;
 *
 *     // Area types.
 *     public static final int START = 0;
 *     public static final int NORMAL = 1;
 *     public static final int SHOP = 2;
 *     public static final int REALM = 3;
 *     public static final int BOSS = 4;
 *
 *     // Objects standing on the floor.
 *     public static final int NONE = 0;
 *     public static final int ENEMY = 1;
 *     public static final int CHEST = 2;
 *     public static final int OBJ_SHOP = 3;
 *     public static final int OBJ_REALM = 4;
 *     public static final int OBJ_BOSS = 5;
 *
 *     // Where the player appears when entering through a door (index = 0 north, 1 east, 2 south, 3 west).
 *     public static final int[] ENTRY_ROW = {1, 5, 9, 5};
 *     public static final int[] ENTRY_COL = {8, 15, 8, 1};
 *
 *     // Spots ('E') where enemies and chests may stand. One layout is picked per area.
 *     public static final String[][] FIXED_MAPS = {
 *         {
 *             "#################",
 *             "#.............E.#",
 *             "#.E.............#",
 *             "#.........E.....#",
 *             "#...E...........#",
 *             "#...............#",
 *             "#....E..........#",
 *             "#...........E...#",
 *             "#.E.............#",
 *             "#.............E.#",
 *             "#################"
 *         },
 *         {
 *             "#################",
 *             "#.E.............#",
 *             "#.............E.#",
 *             "#.....E.........#",
 *             "#...........E...#",
 *             "#...............#",
 *             "#..........E....#",
 *             "#...E...........#",
 *             "#.............E.#",
 *             "#.E.............#",
 *             "#################"
 *         },
 *         {
 *             "#################",
 *             "#.............E.#",
 *             "#.E.............#",
 *             "#...........E...#",
 *             "#....E..........#",
 *             "#...............#",
 *             "#...E...........#",
 *             "#.........E.....#",
 *             "#.E.............#",
 *             "#.............E.#",
 *             "#################"
 *         }
 *     };
 *
 *     public static final int CENTER_ROW = 5;
 *     public static final int CENTER_COL = 8;
 *
 *     public int row, col;                    // position in the big map
 *     public int type = NORMAL;
 *     public boolean[] doors = new boolean[4];  // door on the north, east, south, west side
 *     public int[][] tiles = new int[ROWS][COLS];
 *     public int[][] objects = new int[ROWS][COLS];
 *
 *     public Enemy[][] enemies = new Enemy[ROWS][COLS];
 *     public Treasure[][] chests = new Treasure[ROWS][COLS];
 *     public Enemy boss;
 *     public boolean realmUsed = false;
 *     public List<Item> shopStock = new ArrayList<Item>();
 *
 *     public Area(WorldMap world, int row, int col) {
 *         this.row = row;
 *         this.col = col;
 *         for (int d = 0; d < 4; d++) {
 *             doors[d] = world.isArea(row + WorldMap.DR[d], col + WorldMap.DC[d]);
 *         }
 *         buildTiles();
 *     }
 *
 *     // Fills the tiles: walls around the edge, floor inside, and doors where a neighbor area exists.
 *     private void buildTiles() {
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 boolean border = (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1);
 *                 if (border) {
 *                     tiles[r][c] = WALL;
 *                 } else {
 *                     tiles[r][c] = FLOOR;
 *                 }
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
 *     // Puts 0 to 4 enemies or chests on random spots of one of the fixed layouts.
 *     public void placeRandomObjects(Random rnd) {
 *         String[] layout = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
 *         List<int[]> spots = new ArrayList<int[]>();
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 if (layout[r].charAt(c) == 'E') {
 *                     spots.add(new int[]{r, c});
 *                 }
 *             }
 *         }
 *
 *         Collections.shuffle(spots, rnd);
 *         int count = rnd.nextInt(5);
 *         for (int i = 0; i < count; i++) {
 *             int[] spot = spots.get(i);
 *             if (rnd.nextBoolean()) {
 *                 objects[spot[0]][spot[1]] = ENEMY;
 *             } else {
 *                 objects[spot[0]][spot[1]] = CHEST;
 *             }
 *         }
 *     }
 *
 *     // Fills a 3 x 3 block in the middle of the area with one object (shop, Lost Realm or boss).
 *     public void placeCenterObject(int objectType) {
 *         for (int r = CENTER_ROW - 1; r <= CENTER_ROW + 1; r++) {
 *             for (int c = CENTER_COL - 1; c <= CENTER_COL + 1; c++) {
 *                 objects[r][c] = objectType;
 *             }
 *         }
 *     }
 *
 *     // Creates the real enemies, chest contents, boss and shop stock. distance = steps from the start area.
 *     public void populate(int distance, Random rnd) {
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 if (objects[r][c] == ENEMY) {
 *                     enemies[r][c] = Enemy.create(distance, rnd);
 *                 }
 *                 if (objects[r][c] == CHEST) {
 *                     chests[r][c] = Loot.rollChest(distance, rnd);
 *                 }
 *             }
 *         }
 *         if (type == BOSS) {
 *             boss = new Boss();
 *         }
 *         if (type == SHOP) {
 *             shopStock.add(Potion.healing());
 *             shopStock.add(Potion.mana());
 *             shopStock.add(Potion.mixed());
 *             shopStock.add(Potion.great());
 *             shopStock.add(Loot.shopWeapon(distance, rnd));
 *             shopStock.add(Loot.shopArmor(distance, rnd));
 *         }
 *     }
 *
 *     // Number of enemies and chests still in this area.
 *     public int countObjects() {
 *         int count = 0;
 *         for (int r = 0; r < ROWS; r++) {
 *             for (int c = 0; c < COLS; c++) {
 *                 if (objects[r][c] == ENEMY || objects[r][c] == CHEST) {
 *                     count++;
 *                 }
 *             }
 *         }
 *         return count;
 *     }
 * }
 */
