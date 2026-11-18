/*
 * Team Project: Void-Dungeon
 * Member: Member 1 (Game Engine, Map & Screens)
 *
 * File: WorldMap.java
 *
 * Task Description:
 * The big map: which cells of the 5 x 5 grid are areas, where the start is and where the boss is.
 * The four fixed layouts are already typed in. You make the map usable.
 *
 * What to do:
 * 1. useFixedMap(rnd): pick one of the FIXED_MAPS at random, copy it into `map`, remember where the
 *    start area (the cell with value 2) is, then call findBoss().
 * 2. isArea(r, c): true if the cell is inside the 5 x 5 grid and is not 0.
 * 3. findBoss(): walk out from the start area one step at a time (a "breadth-first search").
 *    Fill `dist` with the number of steps from the start (-1 = not reached yet).
 *    The area that is farthest from the start becomes the boss area (bossRow, bossCol).
 *
 * Expected Output:
 * === Testing WorldMap.java (Member 1) ===
 * [PASS] isArea answers correctly, also outside the grid
 * [PASS] useFixedMap finds the start area
 * [PASS] Distances: start is 0, neighbors are 1, every area is reachable
 * [PASS] The boss area is the farthest one and is not the start
 * [PASS] All four fixed maps work
 * [SUCCESS] WorldMap self-test passed successfully!
 *
 * Hints:
 * - Directions: DR[d] and DC[d] give the row and column change for d = 0 north, 1 east, 2 south, 3 west.
 * - Breadth-first search: put the start in an ArrayDeque, then repeat: take one out (queue.poll()),
 *   look at its 4 neighbors, and for each area not reached yet set its dist and add it to the queue.
 * - Remember the biggest distance seen so far and which cell had it.
 */

package model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Random;

public class WorldMap {
    public static final int SIZE = 5;

    // Directions: 0 = north, 1 = east, 2 = south, 3 = west (row and column change for each).
    public static final int[] DR = {-1, 0, 1, 0};
    public static final int[] DC = {0, 1, 0, -1};

    public int[][] map = new int[SIZE][SIZE];       // 0 = nothing, 1 = area, 2 = start area
    public int startRow, startCol;
    public int bossRow, bossCol;
    public int[][] dist = new int[SIZE][SIZE];      // number of steps from the start area

    public static final int[][][] FIXED_MAPS = {
        {
            {0, 1, 0, 0, 0},
            {1, 1, 2, 1, 0},
            {1, 0, 1, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 1, 0, 1, 0}
        },
        {
            {1, 1, 1, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 1, 2, 0},
            {0, 1, 0, 1, 1},
            {1, 1, 0, 0, 0}
        },
        {
            {1, 1, 0, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 0, 2, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 1, 0, 0, 1}
        },
        {
            {0, 1, 0, 0, 0},
            {1, 1, 0, 1, 0},
            {0, 1, 0, 1, 1},
            {1, 1, 1, 2, 0},
            {0, 0, 1, 0, 0}
        }
    };

    // Picks one of the fixed maps at random and finds the start and the boss area.
    public void useFixedMap(Random rnd) {
        // TODO: Member 1
        // 1. int[][] chosen = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
        // 2. Copy every cell into map. When a cell is 2, set startRow and startCol.
        // 3. Call findBoss().
    }

    // True if this grid cell exists and is an area.
    public boolean isArea(int r, int c) {
        // TODO: Member 1
        return false;
    }

    // Walks out from the start area step by step; the area farthest away becomes the boss area.
    private void findBoss() {
        // TODO: Member 1
        // 1. dist = new int[SIZE][SIZE]; fill every row with -1 (Arrays.fill).
        // 2. dist[startRow][startCol] = 0 and put the start cell in the queue.
        // 3. While the queue is not empty, visit the 4 neighbors of the cell you took out (see Hints).
        // 4. Keep the farthest cell in bossRow / bossCol.
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void need(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    private static int countAreas(WorldMap w) {
        int n = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (w.isArea(r, c)) n++;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println("=== Testing WorldMap.java (Member 1) ===");

        Random rnd = new Random(11);  // one shared generator, so the four maps all get picked
        WorldMap w = new WorldMap();
        w.map = new int[][]{
            {1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2}
        };
        need(w.isArea(0, 0) && w.isArea(4, 4) && !w.isArea(1, 1), "isArea should be true for 1 and 2 and false for 0");
        need(!w.isArea(-1, 0) && !w.isArea(0, -1) && !w.isArea(5, 0) && !w.isArea(0, 5), "isArea must be false outside the grid (and must not crash)");
        System.out.println("[PASS] isArea answers correctly, also outside the grid");

        for (int seed = 0; seed < 20; seed++) {
            WorldMap m = new WorldMap();
            m.useFixedMap(rnd);
            need(m.map[m.startRow][m.startCol] == 2, "startRow/startCol must point at the cell with value 2");
        }
        System.out.println("[PASS] useFixedMap finds the start area");

        for (int seed = 0; seed < 20; seed++) {
            WorldMap m = new WorldMap();
            m.useFixedMap(rnd);
            need(m.dist[m.startRow][m.startCol] == 0, "the start must be at distance 0");
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (m.isArea(r, c)) {
                        need(m.dist[r][c] >= 0, "every area must be reachable from the start");
                        if (m.dist[r][c] == 1) {
                            int step = Math.abs(r - m.startRow) + Math.abs(c - m.startCol);
                            need(step == 1, "an area at distance 1 must touch the start");
                        }
                    }
                }
            }
        }
        System.out.println("[PASS] Distances: start is 0, neighbors are 1, every area is reachable");

        for (int seed = 0; seed < 20; seed++) {
            WorldMap m = new WorldMap();
            m.useFixedMap(rnd);
            need(m.bossRow != m.startRow || m.bossCol != m.startCol, "the boss must not be in the start area");
            int boss = m.dist[m.bossRow][m.bossCol];
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) need(m.dist[r][c] <= boss, "no area may be farther than the boss area");
            }
        }
        System.out.println("[PASS] The boss area is the farthest one and is not the start");

        boolean[] seen = new boolean[FIXED_MAPS.length];
        for (int seed = 0; seed < 200; seed++) {
            WorldMap m = new WorldMap();
            m.useFixedMap(rnd);
            for (int i = 0; i < FIXED_MAPS.length; i++) {
                if (Arrays.deepEquals(m.map, FIXED_MAPS[i])) seen[i] = true;
            }
            need(countAreas(m) >= 12, "every fixed map has 12 or 13 areas");
        }
        for (boolean s : seen) need(s, "all four fixed maps should be picked sometimes");
        System.out.println("[PASS] All four fixed maps work");

        System.out.println("[SUCCESS] WorldMap self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * import java.util.ArrayDeque;
 * import java.util.Arrays;
 * import java.util.Random;
 *
 * // The big map: which of the 5x5 grid cells are areas, where the start is and where the boss is.
 * public class WorldMap {
 *     public static final int SIZE = 5;
 *
 *     // Directions: 0 = north, 1 = east, 2 = south, 3 = west (row and column change for each).
 *     public static final int[] DR = {-1, 0, 1, 0};
 *     public static final int[] DC = {0, 1, 0, -1};
 *
 *     public int[][] map = new int[SIZE][SIZE];       // 0 = nothing, 1 = area, 2 = start area
 *     public int startRow, startCol;
 *     public int bossRow, bossCol;
 *     public int[][] dist = new int[SIZE][SIZE];      // number of steps from the start area
 *
 *     public static final int[][][] FIXED_MAPS = {
 *         {
 *             {0, 1, 0, 0, 0},
 *             {1, 1, 2, 1, 0},
 *             {1, 0, 1, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 1, 0, 1, 0}
 *         },
 *         {
 *             {1, 1, 1, 1, 0},
 *             {0, 0, 1, 0, 0},
 *             {0, 1, 1, 2, 0},
 *             {0, 1, 0, 1, 1},
 *             {1, 1, 0, 0, 0}
 *         },
 *         {
 *             {1, 1, 0, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 0, 2, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 1, 0, 0, 1}
 *         },
 *         {
 *             {0, 1, 0, 0, 0},
 *             {1, 1, 0, 1, 0},
 *             {0, 1, 0, 1, 1},
 *             {1, 1, 1, 2, 0},
 *             {0, 0, 1, 0, 0}
 *         }
 *     };
 *
 *     // Picks one of the fixed maps at random and finds the start and the boss area.
 *     public void useFixedMap(Random rnd) {
 *         int[][] chosen = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
 *         map = new int[SIZE][SIZE];
 *         for (int r = 0; r < SIZE; r++) {
 *             for (int c = 0; c < SIZE; c++) {
 *                 map[r][c] = chosen[r][c];
 *                 if (map[r][c] == 2) {
 *                     startRow = r;
 *                     startCol = c;
 *                 }
 *             }
 *         }
 *         findBoss();
 *     }
 *
 *     // True if this grid cell exists and is an area.
 *     public boolean isArea(int r, int c) {
 *         return r >= 0 && r < SIZE && c >= 0 && c < SIZE && map[r][c] != 0;
 *     }
 *
 *     // Walks out from the start area step by step; the area farthest away becomes the boss area.
 *     private void findBoss() {
 *         dist = new int[SIZE][SIZE];
 *         for (int[] row : dist) {
 *             Arrays.fill(row, -1);
 *         }
 *
 *         ArrayDeque<int[]> queue = new ArrayDeque<int[]>();
 *         dist[startRow][startCol] = 0;
 *         queue.add(new int[]{startRow, startCol});
 *         int farthest = 0;
 *         bossRow = startRow;
 *         bossCol = startCol;
 *
 *         while (!queue.isEmpty()) {
 *             int[] current = queue.poll();
 *             for (int d = 0; d < 4; d++) {
 *                 int nr = current[0] + DR[d];
 *                 int nc = current[1] + DC[d];
 *                 if (isArea(nr, nc) && dist[nr][nc] == -1) {
 *                     dist[nr][nc] = dist[current[0]][current[1]] + 1;
 *                     queue.add(new int[]{nr, nc});
 *                     if (dist[nr][nc] > farthest) {
 *                         farthest = dist[nr][nc];
 *                         bossRow = nr;
 *                         bossCol = nc;
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 */
