package model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Random;

// The big map: which of the 5x5 grid cells are areas, where the start is and where the boss is.
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
        int[][] chosen = FIXED_MAPS[rnd.nextInt(FIXED_MAPS.length)];
        map = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                map[r][c] = chosen[r][c];
                if (map[r][c] == 2) {
                    startRow = r;
                    startCol = c;
                }
            }
        }
        findBoss();
    }

    // True if this grid cell exists and is an area.
    public boolean isArea(int r, int c) {
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE && map[r][c] != 0;
    }

    // Walks out from the start area step by step; the area farthest away becomes the boss area.
    private void findBoss() {
        dist = new int[SIZE][SIZE];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        ArrayDeque<int[]> queue = new ArrayDeque<int[]>();
        dist[startRow][startCol] = 0;
        queue.add(new int[]{startRow, startCol});
        int farthest = 0;
        bossRow = startRow;
        bossCol = startCol;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nr = current[0] + DR[d];
                int nc = current[1] + DC[d];
                if (isArea(nr, nc) && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[current[0]][current[1]] + 1;
                    queue.add(new int[]{nr, nc});
                    if (dist[nr][nc] > farthest) {
                        farthest = dist[nr][nc];
                        bossRow = nr;
                        bossCol = nc;
                    }
                }
            }
        }
    }
}
