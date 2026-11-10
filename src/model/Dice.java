/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 1 (Combat & Hero Systems)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Dice.java
 *
 * Task Description:
 * Provide random number generation utilities for combat probability and damage variance.
 *
 * What to do:
 * 1. Implement chance(double percent):
 *    - Returns true if a random roll in [0.0, 100.0) is strictly less than percent.
 * 2. Implement swing(int base, double variance):
 *    - Calculates spread = Math.round(base * variance).
 *    - Returns a random value in [base - spread, base + spread], with a minimum value of 1.
 * 3. Run the built-in main method to verify your implementation.
 *
 * Expected Output:
 * === Testing Dice.java (Member 1) ===
 * [PASS] chance(0) returns false
 * [PASS] chance(100) returns true
 * [PASS] swing(100, 0.15) stays within bounds [85, 115]
 * [PASS] swing(0, 0.15) guarantees minimum result of at least 1
 * [SUCCESS] Dice self-test passed successfully!
 *
 * Hints:
 * - Use java.util.Random.
 * - Math.max(1, value) guarantees damage never drops below 1.
 */

package model;

import java.util.Random; 

public class Dice { 
    private static final Random RNG = new Random(); 
    
    public static boolean chance(double percent) { 
        // TODO: Member 1
        // Roll a random double in [0.0, 100.0) using RNG.nextDouble() * 100.
        // Return true if the roll is strictly less than percent.
        return false; 
    }
    
    public static int swing(int base, double variance) { 
        // TODO: Member 1
        // 1. Calculate spread = (int) Math.round(base * variance).
        // 2. Roll random offset in [-spread, spread] using RNG.nextInt(2 * spread + 1).
        // 3. Return Math.max(1, base - spread + randomOffset).
        return 1; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Dice.java (Member 1) ==="); 
        
        if (chance(0.0)) throw new AssertionError("chance(0) must always return false"); 
        System.out.println("[PASS] chance(0) returns false"); 
        
        if (!chance(100.0)) throw new AssertionError("chance(100) must always return true"); 
        System.out.println("[PASS] chance(100) returns true"); 
        
        for (int i = 0; i < 500; i++) { 
            int val = swing(100, 0.15); 
            if (val < 85 || val > 115) { 
                throw new AssertionError("swing(100, 0.15) out of range: " + val); 
            }
        }
        System.out.println("[PASS] swing(100, 0.15) stays within bounds [85, 115]"); 
        
        int zeroSwing = swing(0, 0.15); 
        if (zeroSwing < 1) throw new AssertionError("swing must guarantee at least 1"); 
        System.out.println("[PASS] swing(0, 0.15) guarantees minimum result of at least 1"); 
        
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
 * public class Dice {
 *     private static final Random RNG = new Random();
 *
 *     public static boolean chance(double percent) {
 *         return RNG.nextDouble() * 100 < percent;
 *     }
 *
 *     public static int swing(int base, double variance) {
 *         int spread = (int) Math.round(base * variance);
 *         return Math.max(1, base - spread + RNG.nextInt(2 * spread + 1));
 *     }
 * }
 */
