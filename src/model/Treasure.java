/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 2 (Items, Skills & Economy)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Treasure.java
 *
 * Task Description:
 * Manages chest loot drops, tier progression (Tiers 1-4), and shop equipment generation.
 * Enforces chest gear tier limits (capped at Tier 3) so Hermes' shop remains valuable.
 *
 * What to do:
 * 1. Implement roll(int distance, Random rnd):
 *    - Base gold: 3 + distance + rnd.nextInt(4).
 *    - Roll 0..99:
 *      * < 28: Potion (Item.randomPotion(rnd))
 *      * < 36: Weapon (randomChestWeapon(distance))
 *      * < 44: Armor (randomChestArmor(distance))
 *      * Remaining 56%: gold only (item is null).
 * 2. Implement tier(int d, Random rnd) for shop progression (Tiers 1-4).
 * 3. Implement chestTier(int distance) capped between Tier 1 and 3.
 * 4. Implement randomWeapon(...) and randomArmor(...) for shop inventory.
 * 5. Run the main method to verify loot probabilities and tier ceilings.
 *
 * Expected Output:
 * === Testing Treasure.java (Member 2) ===
 * [PASS] Chest rolls contain positive gold scaling with distance
 * [PASS] Chest loot rates match targets (~28% potion, ~8% weapon, ~8% armor, ~56% gold)
 * [PASS] Chest weapons and armors capped at Tier 3 (<= +24 ATK, <= +16 DEF)
 * [PASS] Shop equipment generation reaches top Tier 4
 * [SUCCESS] Treasure self-test passed successfully!
 *
 * Hints:
 * - Distance from start area dictates available gear tier quality.
 * - Chest tier formula: Math.max(1, Math.min(3, 1 + Math.max(0, distance - 1) / 3)).
 */

package model;

import java.util.Random; 

public class Treasure { 
    public int gold; 
    public Item item; 
    
    public static Treasure roll(int distance, Random rnd) { 
        // TODO: Member 2
        // 1. Create Treasure t = new Treasure().
        // 2. Set t.gold = 3 + distance + rnd.nextInt(4).
        // 3. Roll int r = rnd.nextInt(100):
        //    - r < 28: t.item = Item.randomPotion(rnd)
        //    - r < 36: t.item = randomChestWeapon(distance)
        //    - r < 44: t.item = randomChestArmor(distance)
        //    - else: t.item remains null (gold-only chest).
        // 4. Return t.
        return null; 
    }
    
    static final int[][] WEAPON = {{6, 5, 40}, {14, 8, 110}, {24, 12, 260}, {38, 20, 500}}; 
    static final int[][] ARMOR = {{4, 20, 40}, {9, 45, 110}, {16, 80, 260}, {26, 140, 500}}; 
    static final String[] WEAPON_NAMES = {"Bronze Sword", "Iron Spear", "Runic Blade", "Hero's Blade"}; 
    static final String[] ARMOR_NAMES = {"Leather Vest", "Bronze Armor", "Dwarven Plate", "Aegis of Athena"}; 
    
    static int tier(int d, Random rnd) { 
        // TODO: Member 2
        // Shop tier progression: return Math.min(4, (d + 1) / 2 + (rnd.nextInt(100) < 30 ? 1 : 0)).
        return 1; 
    }
    
    private static int chestTier(int distance) { 
        // TODO: Member 2
        // Chest gear tier capped between 1 and 3:
        return Math.max(1, Math.min(3, 1 + Math.max(0, distance - 1) / 3)); 
    }
    
    private static Item randomChestWeapon(int distance) { 
        int t = chestTier(distance); 
        return new Item(WEAPON_NAMES[t - 1], Item.WEAPON, WEAPON[t - 1][0], WEAPON[t - 1][1], WEAPON[t - 1][2]); 
    }
    
    private static Item randomChestArmor(int distance) { 
        int t = chestTier(distance); 
        return new Item(ARMOR_NAMES[t - 1], Item.ARMOR, ARMOR[t - 1][0], ARMOR[t - 1][1], ARMOR[t - 1][2]); 
    }
    
    public static Item randomWeapon(int d, Random rnd) { 
        // TODO: Member 2
        // Instantiate Weapon corresponding to tier(d, rnd).
        int t = tier(d, rnd); 
        return new Item(WEAPON_NAMES[t - 1], Item.WEAPON, WEAPON[t - 1][0], WEAPON[t - 1][1], WEAPON[t - 1][2]); 
    }
    
    public static Item randomArmor(int d, Random rnd) { 
        // TODO: Member 2
        // Instantiate Armor corresponding to tier(d, rnd).
        int t = tier(d, rnd); 
        return new Item(ARMOR_NAMES[t - 1], Item.ARMOR, ARMOR[t - 1][0], ARMOR[t - 1][1], ARMOR[t - 1][2]); 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Treasure.java (Member 2) ==="); 
        
        Random rnd = new Random(12345); 
        Treasure t1 = roll(1, rnd); 
        if (t1 == null || t1.gold < 4 || t1.gold > 8) { 
            throw new AssertionError("Gold generation out of expected range for distance 1: " + (t1 == null ? "null" : t1.gold)); 
        }
        System.out.println("[PASS] Chest rolls contain positive gold scaling with distance"); 
        
        int potions = 0, weapons = 0, armors = 0, goldOnly = 0; 
        int totalRolls = 10000; 
        for (int i = 0; i < totalRolls; i++) { 
            Treasure t = roll(10, rnd); 
            if (t.item == null) { 
                goldOnly++; 
            } else if (t.item.type == Item.POTION) { 
                potions++; 
            } else if (t.item.type == Item.WEAPON) { 
                weapons++; 
                if (t.item.bonus > 24) throw new AssertionError("Chest weapon exceeded tier 3 cap: " + t.item.bonus); 
            } else if (t.item.type == Item.ARMOR) { 
                armors++; 
                if (t.item.bonus > 16) throw new AssertionError("Chest armor exceeded tier 3 cap: " + t.item.bonus); 
            }
        }
        
        double potionPct = (potions * 100.0) / totalRolls; 
        double weaponPct = (weapons * 100.0) / totalRolls; 
        double armorPct = (armors * 100.0) / totalRolls; 
        double goldPct = (goldOnly * 100.0) / totalRolls; 
        
        if (Math.abs(potionPct - 28.0) > 2.0 || Math.abs(weaponPct - 8.0) > 1.5 
                || Math.abs(armorPct - 8.0) > 1.5 || Math.abs(goldPct - 56.0) > 2.0) { 
            throw new AssertionError("Loot distribution deviated from tuning expectations"); 
        }
        System.out.println("[PASS] Chest loot rates match targets (~28% potion, ~8% weapon, ~8% armor, ~56% gold)"); 
        System.out.println("[PASS] Chest weapons and armors capped at Tier 3 (<= +24 ATK, <= +16 DEF)"); 
        
        boolean foundTier4 = false; 
        for (int i = 0; i < 100; i++) { 
            Item shopW = randomWeapon(6, rnd); 
            if (shopW.bonus == 38) { 
                foundTier4 = true; 
                break; 
            }
        }
        if (!foundTier4) throw new AssertionError("Shop tier progression should offer Tier 4 items at high distances"); 
        System.out.println("[PASS] Shop equipment generation reaches top Tier 4"); 
        
        System.out.println("[SUCCESS] Treasure self-test passed successfully!"); 
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
 * public class Treasure {
 *     public int gold;
 *     public Item item;
 *
 *     public static Treasure roll(int distance, Random rnd) {
 *         Treasure t = new Treasure();
 *         t.gold = 3 + distance + rnd.nextInt(4);
 *         int r = rnd.nextInt(100);
 *         if (r < 28)      t.item = Item.randomPotion(rnd);
 *         else if (r < 36) t.item = randomChestWeapon(distance);
 *         else if (r < 44) t.item = randomChestArmor(distance);
 *         return t;
 *     }
 *
 *     static final int[][] WEAPON = {{6, 5, 40}, {14, 8, 110}, {24, 12, 260}, {38, 20, 500}};
 *     static final int[][] ARMOR = {{4, 20, 40}, {9, 45, 110}, {16, 80, 260}, {26, 140, 500}};
 *     static final String[] WEAPON_NAMES = {"Bronze Sword", "Iron Spear", "Runic Blade", "Hero's Blade"};
 *     static final String[] ARMOR_NAMES = {"Leather Vest", "Bronze Armor", "Dwarven Plate", "Aegis of Athena"};
 *
 *     static int tier(int d, Random rnd) {
 *         return Math.min(4, (d + 1) / 2 + (rnd.nextInt(100) < 30 ? 1 : 0));
 *     }
 *
 *     private static int chestTier(int distance) {
 *         return Math.max(1, Math.min(3, 1 + Math.max(0, distance - 1) / 3));
 *     }
 *
 *     private static Item randomChestWeapon(int distance) {
 *         int t = chestTier(distance);
 *         return new Item(WEAPON_NAMES[t - 1], Item.WEAPON, WEAPON[t - 1][0], WEAPON[t - 1][1], WEAPON[t - 1][2]);
 *     }
 *
 *     private static Item randomChestArmor(int distance) {
 *         int t = chestTier(distance);
 *         return new Item(ARMOR_NAMES[t - 1], Item.ARMOR, ARMOR[t - 1][0], ARMOR[t - 1][1], ARMOR[t - 1][2]);
 *     }
 *
 *     public static Item randomWeapon(int d, Random rnd) {
 *         int t = tier(d, rnd);
 *         return new Item(WEAPON_NAMES[t - 1], Item.WEAPON, WEAPON[t - 1][0], WEAPON[t - 1][1], WEAPON[t - 1][2]);
 *     }
 *
 *     public static Item randomArmor(int d, Random rnd) {
 *         int t = tier(d, rnd);
 *         return new Item(ARMOR_NAMES[t - 1], Item.ARMOR, ARMOR[t - 1][0], ARMOR[t - 1][1], ARMOR[t - 1][2]);
 *     }
 * }
 */
