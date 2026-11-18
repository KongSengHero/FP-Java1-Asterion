package model;

import java.util.Random;

// Small helpers for luck: yes/no rolls and "a bit more or less" damage.
public class Dice {
    private static final Random RANDOM = new Random();

    // Returns true with the given percent chance (for example 20 means 20% of the time).
    public static boolean chance(double percent) {
        return RANDOM.nextDouble() * 100 < percent;
    }

    // Changes a number up or down by up to the given fraction (0.15 means +/-15%), never below 1.
    public static int vary(int base, double fraction) {
        int spread = (int) Math.round(base * fraction);
        int result = base - spread + RANDOM.nextInt(2 * spread + 1);
        return Math.max(1, result);
    }
}
