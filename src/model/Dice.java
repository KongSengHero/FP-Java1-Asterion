/*
 * Team Project: Void-Dungeon
 * Member: Member 2 (Fighting & the Player)
 *
 * File: Dice.java
 *
 * Task Description:
 * Two small luck helpers used by every fight: a yes/no roll and a "a bit more or less" damage roll.
 *
 * What to do:
 * 1. chance(percent): return true about `percent` out of 100 times.
 * 2. vary(base, fraction): change `base` up or down by up to `fraction` (0.15 = +/-15%), never below 1.
 * 3. Run this file's main method to check your work.
 *
 * Expected Output:
 * === Testing Dice.java (Member 2) ===
 * [PASS] chance(0) is never true and chance(100) is always true
 * [PASS] chance(50) is true about half of the time
 * [PASS] vary(100, 0.15) always stays between 85 and 115
 * [PASS] vary(100, 0.15) gives different results (it is random)
 * [PASS] vary never returns less than 1
 * [SUCCESS] Dice self-test passed successfully!
 *
 * Hints:
 * - RANDOM.nextDouble() gives a number from 0.0 up to (not including) 1.0.
 * - RANDOM.nextInt(n) gives a whole number from 0 to n - 1.
 * - spread = Math.round(base * fraction). Result = base - spread + nextInt(2 * spread + 1).
 */

package model;

import java.util.Random;

public class Dice {
    private static final Random RANDOM = new Random();

    // Returns true with the given percent chance (for example 20 means 20% of the time).
    public static boolean chance(double percent) {
        // TODO: Member 2
        // Return true when RANDOM.nextDouble() * 100 is smaller than percent.
        return false;
    }

    // Changes a number up or down by up to the given fraction (0.15 means +/-15%), never below 1.
    public static int vary(int base, double fraction) {
        // TODO: Member 2
        // 1. int spread = (int) Math.round(base * fraction);
        // 2. int result = base - spread + RANDOM.nextInt(2 * spread + 1);
        // 3. Return Math.max(1, result).
        return base;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Dice.java (Member 2) ===");

        boolean everTrue = false, alwaysTrue = true;
        for (int i = 0; i < 1000; i++) {
            if (Dice.chance(0)) everTrue = true;
            if (!Dice.chance(100)) alwaysTrue = false;
        }
        if (everTrue || !alwaysTrue) throw new AssertionError("chance(0) must never be true and chance(100) must always be true");
        System.out.println("[PASS] chance(0) is never true and chance(100) is always true");

        int hits = 0;
        for (int i = 0; i < 10000; i++) {
            if (Dice.chance(50)) hits++;
        }
        if (hits < 4500 || hits > 5500) throw new AssertionError("chance(50) should be true about 5000 times out of 10000, got " + hits);
        System.out.println("[PASS] chance(50) is true about half of the time");

        int min = 1000, max = 0;
        for (int i = 0; i < 2000; i++) {
            int v = Dice.vary(100, 0.15);
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        if (min < 85 || max > 115) throw new AssertionError("vary(100, 0.15) must stay between 85 and 115, got " + min + ".." + max);
        System.out.println("[PASS] vary(100, 0.15) always stays between 85 and 115");

        if (min == max) throw new AssertionError("vary should give different results, but always gave " + min);
        System.out.println("[PASS] vary(100, 0.15) gives different results (it is random)");

        for (int i = 0; i < 500; i++) {
            if (Dice.vary(1, 0.5) < 1 || Dice.vary(0, 0.5) < 1) throw new AssertionError("vary must never return less than 1");
        }
        System.out.println("[PASS] vary never returns less than 1");

        System.out.println("[SUCCESS] Dice self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * import java.util.Random;
 *
 * // Small helpers for luck: yes/no rolls and "a bit more or less" damage.
 * public class Dice {
 *     private static final Random RANDOM = new Random();
 *
 *     // Returns true with the given percent chance (for example 20 means 20% of the time).
 *     public static boolean chance(double percent) {
 *         return RANDOM.nextDouble() * 100 < percent;
 *     }
 *
 *     // Changes a number up or down by up to the given fraction (0.15 means +/-15%), never below 1.
 *     public static int vary(int base, double fraction) {
 *         int spread = (int) Math.round(base * fraction);
 *         int result = base - spread + RANDOM.nextInt(2 * spread + 1);
 *         return Math.max(1, result);
 *     }
 * }
 */
