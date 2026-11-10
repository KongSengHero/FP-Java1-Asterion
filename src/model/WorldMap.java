/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 3 (Labyrinth Environment & World Map)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: WorldMap.java
 *
 * Task Description:
 * Generates the overworld 5x5 labyrinth topology of 13 connected areas.
 * Computes shortest-path graph distances using Breadth-First Search (BFS) and designates the farthest room as the Boss room.
 *
 * What to do:
 * 1. Define dimensions (SIZE = 5, AREA_COUNT = 13) and directional offsets DR = {-1, 0, 1, 0}, DC = {0, 1, 0, -1}.
 * 2. Store FIXED_MAPS (4 verified 13-area layout matrices).
 * 3. Implement useFixedMap(Random rnd) copying a chosen layout and detecting startRow/startCol.
 * 4. Implement generateRandom(Random rnd) growing a connected 13-cell graph.
 * 5. Implement boundary checks: isInside(r, c), isArea(r, c), and hasDoor(r, c, d).
 * 6. Implement findBoss() using BFS from (startRow, startCol) to calculate steps in dist[][] and locate the farthest room.
 * 7. Run the main method to verify map connectivity and BFS calculations.
 *
 * Expected Output:
 * === Testing WorldMap.java (Member 3) ===
 * [PASS] Fixed maps contain exactly 13 connected areas
 * [PASS] BFS computes non-negative distance for every reachable room
 * [PASS] Farthest room assigned as Asterion's Boss room
 * [PASS] Procedural random map generation produces connected 13-area topology
 * [SUCCESS] WorldMap self-test passed successfully!
 *
 * Hints:
 * - dist[startRow][startCol] is initialized to 0.
 * - BFS queue explores unvisited valid areas (dist[nr][nc] == -1).
 * - The area with the maximum BFS distance becomes bossRow and bossCol.
 */

package model;

import java.util.ArrayDeque; 
import java.util.Arrays; 
import java.util.Random; 

public class WorldMap { 
    public static final int SIZE = 5; 
    public static final int AREA_COUNT = 13; 
    
    public static final int[] DR = {-1, 0, 1, 0}; 
    public static final int[] DC = {0, 1, 0, -1}; 
    
    public int[][] map = new int[SIZE][SIZE]; 
    public int startRow, startCol; 
    public int bossRow, bossCol; 
    public int[][] dist = new int[SIZE][SIZE]; 
    
    public static final int[][][] FIXED_MAPS = {
        {
            {0, 1, 0, 0, 0}, 
            {2, 1, 1, 1, 0}, 
            {1, 0, 1, 0, 0}, 
            {0, 1, 1, 1, 1}, 
            {0, 1, 0, 1, 0}
        }, 
        {
            {2, 1, 1, 0, 0}, 
            {0, 0, 1, 0, 0}, 
            {0, 1, 1, 1, 0}, 
            {0, 1, 0, 1, 1}, 
            {1, 1, 0, 0, 1}
        }, 
        {
            {2, 1, 0, 0, 0}, 
            {0, 1, 1, 1, 1}, 
            {0, 0, 1, 0, 0}, 
            {0, 1, 1, 1, 1}, 
            {0, 1, 0, 0, 1}
        }, 
        {
            {0, 1, 1, 1, 0}, 
            {0, 1, 0, 1, 0}, 
            {1, 1, 0, 1, 1}, 
            {0, 1, 1, 1, 0}, 
            {0, 0, 2, 0, 0}
        }
    }; 
    
    public void useFixedMap(Random rnd) { 
        // TODO: Member 3
        // 1. Pick a random fixed map from FIXED_MAPS using rnd.nextInt(FIXED_MAPS.length).
        // 2. Initialize map = new int[SIZE][SIZE] and copy cells across.
        // 3. If cell value is 2, record startRow = r, startCol = c.
        // 4. Call findBoss().
    }
    
    public void generateRandom(Random rnd) { 
        // TODO: Member 3
        // 1. Initialize map = new int[SIZE][SIZE].
        // 2. Pick startRow = rnd.nextInt(SIZE), startCol = rnd.nextInt(SIZE), set map[startRow][startCol] = 2.
        // 3. Loop until count reaches AREA_COUNT:
        //    - Pick random existing area cell (map[r][c] != 0).
        //    - Pick random direction d in 0..3: nr = r + DR[d], nc = c + DC[d].
        //    - If isInside(nr, nc) and map[nr][nc] == 0: map[nr][nc] = 1, increment count.
        // 4. Call findBoss().
    }
    
    public boolean isInside(int r, int c) { 
        // TODO: Member 3
        // Check 0 <= r < SIZE and 0 <= c < SIZE.
        return false; 
    }
    
    public boolean isArea(int r, int c) { 
        // TODO: Member 3
        // Return true if isInside(r, c) and map[r][c] != 0.
        return false; 
    }
    
    public boolean hasDoor(int r, int c, int d) { 
        // TODO: Member 3
        // Return true if neighbor in direction d is also an area: isArea(r + DR[d], c + DC[d]).
        return false; 
    }
    
    private void findBoss() { 
        // TODO: Member 3
        // 1. Initialize dist = new int[SIZE][SIZE] filled with -1.
        // 2. Set dist[startRow][startCol] = 0.
        // 3. Use an ArrayDeque<int[]> queue seeded with {startRow, startCol}.
        // 4. While queue is not empty:
        //    - Poll cur = queue.poll().
        //    - For each direction d: nr = cur[0] + DR[d], nc = cur[1] + DC[d].
        //    - If isArea(nr, nc) and dist[nr][nc] == -1:
        //        dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
        //        queue.add(new int[]{nr, nc});
        //        Track best distance, bossRow = nr, bossCol = nc.
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing WorldMap.java (Member 3) ==="); 
        
        WorldMap wm = new WorldMap(); 
        Random rnd = new Random(7); 
        wm.useFixedMap(rnd); 
        
        int areaCount = 0; 
        for (int r = 0; r < SIZE; r++) { 
            for (int c = 0; c < SIZE; c++) { 
                if (wm.isArea(r, c)) areaCount++; 
            }
        }
        if (areaCount != 13) throw new AssertionError("Expected 13 areas in fixed map, found: " + areaCount); 
        System.out.println("[PASS] Fixed maps contain exactly 13 connected areas"); 
        
        for (int r = 0; r < SIZE; r++) { 
            for (int c = 0; c < SIZE; c++) { 
                if (wm.isArea(r, c) && wm.dist[r][c] < 0) { 
                    throw new AssertionError("Unreachable area found at (" + r + ", " + c + ")"); 
                }
            }
        }
        System.out.println("[PASS] BFS computes non-negative distance for every reachable room"); 
        
        if (wm.dist[wm.bossRow][wm.bossCol] <= 0) { 
            throw new AssertionError("Boss area distance should be greater than 0"); 
        }
        System.out.println("[PASS] Farthest room assigned as Asterion's Boss room"); 
        
        WorldMap randomWm = new WorldMap(); 
        randomWm.generateRandom(new Random(99)); 
        int randAreas = 0; 
        for (int r = 0; r < SIZE; r++) { 
            for (int c = 0; c < SIZE; c++) { 
                if (randomWm.isArea(r, c)) randAreas++; 
            }
        }
        if (randAreas != 13 || randomWm.dist[randomWm.bossRow][randomWm.bossCol] <= 0) { 
            throw new AssertionError("Random map failed 13-area or connectivity constraint"); 
        }
        System.out.println("[PASS] Procedural random map generation produces connected 13-area topology"); 
        
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
 * import java.util.*;
 *
 * public class WorldMap {
 *     public static final int SIZE = 5;
 *     public static final int AREA_COUNT = 13;
 *
 *     public static final int[] DR = {-1, 0, 1, 0};
 *     public static final int[] DC = {0, 1, 0, -1};
 *
 *     public int[][] map = new int[SIZE][SIZE];
 *     public int startRow, startCol;
 *     public int bossRow, bossCol;
 *     public int[][] dist = new int[SIZE][SIZE];
 *
 *     public static final int[][][] FIXED_MAPS = {
 *         {
 *             {0, 1, 0, 0, 0},
 *             {2, 1, 1, 1, 0},
 *             {1, 0, 1, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 1, 0, 1, 0}
 *         },
 *         {
 *             {2, 1, 1, 0, 0},
 *             {0, 0, 1, 0, 0},
 *             {0, 1, 1, 1, 0},
 *             {0, 1, 0, 1, 1},
 *             {1, 1, 0, 0, 1}
 *         },
 *         {
 *             {2, 1, 0, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 0, 1, 0, 0},
 *             {0, 1, 1, 1, 1},
 *             {0, 1, 0, 0, 1}
 *         },
 *         {
 *             {0, 1, 1, 1, 0},
 *             {0, 1, 0, 1, 0},
 *             {1, 1, 0, 1, 1},
 *             {0, 1, 1, 1, 0},
 *             {0, 0, 2, 0, 0}
 *         }
 *     };
 *
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
 *     public void generateRandom(Random rnd) {
 *         map = new int[SIZE][SIZE];
 *         startRow = rnd.nextInt(SIZE);
 *         startCol = rnd.nextInt(SIZE);
 *         map[startRow][startCol] = 2;
 *         int count = 1;
 *
 *         while (count < AREA_COUNT) {
 *             int r, c;
 *             do {
 *                 r = rnd.nextInt(SIZE);
 *                 c = rnd.nextInt(SIZE);
 *             } while (map[r][c] == 0);
 *
 *             int d = rnd.nextInt(4);
 *             int nr = r + DR[d];
 *             int nc = c + DC[d];
 *             if (isInside(nr, nc) && map[nr][nc] == 0) {
 *                 map[nr][nc] = 1;
 *                 count++;
 *             }
 *         }
 *         findBoss();
 *     }
 *
 *     public boolean isInside(int r, int c) {
 *         return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
 *     }
 *
 *     public boolean isArea(int r, int c) {
 *         return isInside(r, c) && map[r][c] != 0;
 *     }
 *
 *     public boolean hasDoor(int r, int c, int d) {
 *         return isArea(r + DR[d], c + DC[d]);
 *     }
 *
 *     private void findBoss() {
 *         dist = new int[SIZE][SIZE];
 *         for (int[] row : dist) Arrays.fill(row, -1);
 *
 *         ArrayDeque<int[]> queue = new ArrayDeque<int[]>();
 *         dist[startRow][startCol] = 0;
 *         queue.add(new int[]{startRow, startCol});
 *         int best = 0;
 *         bossRow = startRow;
 *         bossCol = startCol;
 *
 *         while (!queue.isEmpty()) {
 *             int[] cur = queue.poll();
 *             for (int d = 0; d < 4; d++) {
 *                 int nr = cur[0] + DR[d];
 *                 int nc = cur[1] + DC[d];
 *                 if (isArea(nr, nc) && dist[nr][nc] == -1) {
 *                     dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
 *                     queue.add(new int[]{nr, nc});
 *                     if (dist[nr][nc] > best) {
 *                         best = dist[nr][nc];
 *                         bossRow = nr;
 *                         bossCol = nc;
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 */
