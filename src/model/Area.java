package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// One area of the dungeon: its doors, its 11 x 17 tile map, and what is inside.
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
        this.row = row;
        this.col = col;
        for (int d = 0; d < 4; d++) {
            doors[d] = world.isArea(row + WorldMap.DR[d], col + WorldMap.DC[d]);
        }
        buildTiles();
    }

    // Fills the tiles: walls around the edge, floor inside, and doors where a neighbor area exists.
    private void buildTiles() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean border = (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1);
                if (border) {
                    tiles[r][c] = WALL;
                } else {
                    tiles[r][c] = FLOOR;
                }
            }
        }
        for (int c = 7; c <= 9; c++) {
            if (doors[0]) tiles[0][c] = DOOR;
            if (doors[2]) tiles[ROWS - 1][c] = DOOR;
        }
        for (int r = 4; r <= 6; r++) {
            if (doors[3]) tiles[r][0] = DOOR;
            if (doors[1]) tiles[r][COLS - 1] = DOOR;
        }
    }

    // Puts 0 to 4 enemies or chests on random spots of one of the fixed layouts.
    public void placeRandomObjects(Random rnd) {
        String[] layout = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
        List<int[]> spots = new ArrayList<int[]>();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (layout[r].charAt(c) == 'E') {
                    spots.add(new int[]{r, c});
                }
            }
        }

        Collections.shuffle(spots, rnd);
        int count = rnd.nextInt(5);
        for (int i = 0; i < count; i++) {
            int[] spot = spots.get(i);
            if (rnd.nextBoolean()) {
                objects[spot[0]][spot[1]] = ENEMY;
            } else {
                objects[spot[0]][spot[1]] = CHEST;
            }
        }
    }

    // Fills a 3 x 3 block in the middle of the area with one object (shop, Lost Realm or boss).
    public void placeCenterObject(int objectType) {
        for (int r = CENTER_ROW - 1; r <= CENTER_ROW + 1; r++) {
            for (int c = CENTER_COL - 1; c <= CENTER_COL + 1; c++) {
                objects[r][c] = objectType;
            }
        }
    }

    // Creates the real enemies, chest contents, boss and shop stock. distance = steps from the start area.
    public void populate(int distance, Random rnd) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (objects[r][c] == ENEMY) {
                    enemies[r][c] = Enemy.create(distance, rnd);
                }
                if (objects[r][c] == CHEST) {
                    chests[r][c] = Loot.rollChest(distance, rnd);
                }
            }
        }
        if (type == BOSS) {
            boss = new Boss();
        }
        if (type == SHOP) {
            shopStock.add(Potion.healing());
            shopStock.add(Potion.mana());
            shopStock.add(Potion.mixed());
            shopStock.add(Potion.great());
            shopStock.add(Loot.shopWeapon(distance, rnd));
            shopStock.add(Loot.shopArmor(distance, rnd));
        }
    }

    // Number of enemies and chests still in this area.
    public int countObjects() {
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (objects[r][c] == ENEMY || objects[r][c] == CHEST) {
                    count++;
                }
            }
        }
        return count;
    }
}
