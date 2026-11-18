/*
 * Team Project: Void-Dungeon
 * Member: Member 4 (Items, Inventory & Paperwork)
 *
 * File: Loot.java
 *
 * Task Description:
 * Creates the weapons, armor and potions that appear in chests and in the shop (the Sacred Exchange).
 * Gear comes in 4 tiers. Chests only give tiers 1 to 3, so the shop stays worth visiting.
 *
 * What to do:
 * 1. weapon(tier) and armor(tier): return the right item for tier 1 to 4:
 *      Weapons: 1 Stone Sword (6 ATK, 5% crit, 40 gold)    2 Iron Sword (14, 8, 110)
 *               3 Obsidian Sword (24, 12, 260)             4 Dark Blade (38, 20, 500)
 *      Armor:   1 Stone Armor (4 DEF, 20 HP, 40 gold)      2 Iron Armor (9, 45, 110)
 *               3 Obsidian Armor (16, 80, 260)             4 Void Protection (26, 140, 500)
 * 2. randomPotion(rnd): roll 0-99. Under 40 Mixed, under 67 Healing, under 92 Mana, otherwise Great.
 * 3. rollChest(distance, rnd): gold = 3 + distance + rnd.nextInt(4). Then roll 0-99:
 *      under 28 a random potion, under 36 a weapon of chestTier, under 44 an armor of chestTier, else no item.
 * 4. chestTier(distance): 1 near the start, grows by 1 every 3 steps, never more than 3.
 * 5. shopTier(distance, rnd): (distance + 1) / 2, plus 1 with a 30% chance, never more than 4.
 * 6. shopWeapon(distance, rnd) and shopArmor(distance, rnd): the weapon / armor of shopTier.
 *
 * Expected Output:
 * === Testing Loot.java (Member 4) ===
 * [PASS] All 4 weapon tiers and all 4 armor tiers are correct
 * [PASS] Potion odds: all four kinds appear with the right rough share
 * [PASS] Chests: gold grows with distance and loot rates are ~28% potion, ~8% weapon, ~8% armor
 * [PASS] Chest gear stops at tier 3 (even very far away)
 * [PASS] The shop can sell tier 4 gear, and tier 1 only near the start
 * [SUCCESS] Loot self-test passed successfully!
 *
 * Hints:
 * - chestTier: int tier = 1; if (distance > 1) tier = 1 + (distance - 1) / 3; return Math.min(tier, 3);
 * - Treasure.java is only a small data holder (gold + item) and is already finished.
 */

package model;

import java.util.Random;

public class Loot {

    // Weapon of the given tier (1 = weakest, 4 = strongest).
    public static Weapon weapon(int tier) {
        // TODO: Member 4
        // One if per tier, see the table above. Example: if (tier == 1) return new Weapon("Stone Sword", 6, 5, 40);
        return null;
    }

    // Armor of the given tier (1 = weakest, 4 = strongest).
    public static Armor armor(int tier) {
        // TODO: Member 4
        return null;
    }

    // Picks a random potion: Mixed 40%, Healing 27%, Mana 25%, Great 8%.
    public static Potion randomPotion(Random rnd) {
        // TODO: Member 4
        return null;
    }

    // Rolls the content of a chest. Farther areas give more gold and better gear.
    public static Treasure rollChest(int distance, Random rnd) {
        // TODO: Member 4
        // 1. Treasure t = new Treasure(); 2. t.gold = ...; 3. roll and set t.item (see above); 4. return t.
        return null;
    }

    // Gear tier found in chests: tier 1 near the start, at most tier 3.
    private static int chestTier(int distance) {
        // TODO: Member 4
        return 1;
    }

    // Gear tier sold in the shop: grows with distance, 30% chance of one extra tier, at most tier 4.
    private static int shopTier(int distance, Random rnd) {
        // TODO: Member 4
        return 1;
    }

    public static Weapon shopWeapon(int distance, Random rnd) {
        // TODO: Member 4
        return null;
    }

    public static Armor shopArmor(int distance, Random rnd) {
        // TODO: Member 4
        return null;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void need(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Loot.java (Member 4) ===");

        String[] weaponNames = {"Stone Sword", "Iron Sword", "Obsidian Sword", "Dark Blade"};
        int[][] weaponStats = {{6, 5, 40}, {14, 8, 110}, {24, 12, 260}, {38, 20, 500}};
        String[] armorNames = {"Stone Armor", "Iron Armor", "Obsidian Armor", "Void Protection"};
        int[][] armorStats = {{4, 20, 40}, {9, 45, 110}, {16, 80, 260}, {26, 140, 500}};
        for (int t = 1; t <= 4; t++) {
            Weapon w = Loot.weapon(t);
            Armor a = Loot.armor(t);
            need(w != null && w.name.equals(weaponNames[t - 1]) && w.attack == weaponStats[t - 1][0]
                    && w.crit == weaponStats[t - 1][1] && w.price == weaponStats[t - 1][2], "weapon tier " + t + " is wrong");
            need(a != null && a.name.equals(armorNames[t - 1]) && a.defense == armorStats[t - 1][0]
                    && a.maxHp == armorStats[t - 1][1] && a.price == armorStats[t - 1][2], "armor tier " + t + " is wrong");
        }
        System.out.println("[PASS] All 4 weapon tiers and all 4 armor tiers are correct");

        Random rnd = new Random(7);
        int mixed = 0, healing = 0, mana = 0, great = 0;
        for (int i = 0; i < 10000; i++) {
            String n = Loot.randomPotion(rnd).name;
            if (n.equals("Mixed Potion")) mixed++;
            else if (n.equals("Healing Potion")) healing++;
            else if (n.equals("Mana Potion")) mana++;
            else if (n.equals("Great Potion")) great++;
        }
        need(Math.abs(mixed - 4000) < 300 && Math.abs(healing - 2700) < 300 && Math.abs(mana - 2500) < 300 && Math.abs(great - 800) < 200,
                "potion odds should be about 40% Mixed, 27% Healing, 25% Mana, 8% Great, got " + mixed + "/" + healing + "/" + mana + "/" + great);
        System.out.println("[PASS] Potion odds: all four kinds appear with the right rough share");

        int potions = 0, weapons = 0, armors = 0, rolls = 10000;
        for (int i = 0; i < rolls; i++) {
            Treasure t = Loot.rollChest(3, rnd);
            need(t.gold >= 6 && t.gold <= 9, "chest gold at distance 3 should be 6 to 9, got " + t.gold);
            if (t.item instanceof Potion) potions++;
            else if (t.item instanceof Weapon) weapons++;
            else if (t.item instanceof Armor) armors++;
        }
        need(Math.abs(potions * 100.0 / rolls - 28) < 2, "about 28% of chests should hold a potion, got " + potions * 100.0 / rolls);
        need(Math.abs(weapons * 100.0 / rolls - 8) < 1.5, "about 8% of chests should hold a weapon");
        need(Math.abs(armors * 100.0 / rolls - 8) < 1.5, "about 8% of chests should hold armor");
        need(Loot.rollChest(20, rnd).gold > Loot.rollChest(1, rnd).gold, "far chests should give more gold");
        System.out.println("[PASS] Chests: gold grows with distance and loot rates are ~28% potion, ~8% weapon, ~8% armor");

        for (int i = 0; i < 3000; i++) {
            Treasure t = Loot.rollChest(100, rnd);
            if (t.item instanceof Weapon) need(((Weapon) t.item).attack <= 24, "chest weapons must stop at tier 3");
            if (t.item instanceof Armor) need(((Armor) t.item).defense <= 16, "chest armor must stop at tier 3");
        }
        System.out.println("[PASS] Chest gear stops at tier 3 (even very far away)");

        boolean sawTier1 = false;
        for (int i = 0; i < 200; i++) {
            need(Loot.shopWeapon(100, rnd).name.equals("Dark Blade"), "far away the shop should sell Dark Blade");
            need(Loot.shopArmor(100, rnd).name.equals("Void Protection"), "far away the shop should sell Void Protection");
            need(!Loot.shopWeapon(7, rnd).name.equals("Stone Sword"), "distance 7 should not sell tier 1");
            if (Loot.shopWeapon(1, rnd).name.equals("Stone Sword")) sawTier1 = true;
            need(Loot.shopArmor(1, rnd).defense <= 9, "distance 1 sells tier 1 or 2 only");
        }
        need(sawTier1, "distance 1 should usually sell tier 1");
        System.out.println("[PASS] The shop can sell tier 4 gear, and tier 1 only near the start");

        System.out.println("[SUCCESS] Loot self-test passed successfully!");
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
 * // Creates the weapons, armor and potions that appear in chests and in the shop.
 * public class Loot {
 *
 *     // Weapon of the given tier (1 = weakest, 4 = strongest).
 *     public static Weapon weapon(int tier) {
 *         if (tier == 1) return new Weapon("Stone Sword", 6, 5, 40);
 *         if (tier == 2) return new Weapon("Iron Sword", 14, 8, 110);
 *         if (tier == 3) return new Weapon("Obsidian Sword", 24, 12, 260);
 *         return new Weapon("Dark Blade", 38, 20, 500);
 *     }
 *
 *     // Armor of the given tier (1 = weakest, 4 = strongest).
 *     public static Armor armor(int tier) {
 *         if (tier == 1) return new Armor("Stone Armor", 4, 20, 40);
 *         if (tier == 2) return new Armor("Iron Armor", 9, 45, 110);
 *         if (tier == 3) return new Armor("Obsidian Armor", 16, 80, 260);
 *         return new Armor("Void Protection", 26, 140, 500);
 *     }
 *
 *     // Picks a random potion: Mixed 40%, Healing 27%, Mana 25%, Great 8%.
 *     public static Potion randomPotion(Random rnd) {
 *         int roll = rnd.nextInt(100);
 *         if (roll < 40) return Potion.mixed();
 *         if (roll < 67) return Potion.healing();
 *         if (roll < 92) return Potion.mana();
 *         return Potion.great();
 *     }
 *
 *     // Rolls the content of a chest. Farther areas give more gold and better gear.
 *     public static Treasure rollChest(int distance, Random rnd) {
 *         Treasure t = new Treasure();
 *         t.gold = 3 + distance + rnd.nextInt(4);
 *         int roll = rnd.nextInt(100);
 *         if (roll < 28) {
 *             t.item = randomPotion(rnd);
 *         } else if (roll < 36) {
 *             t.item = weapon(chestTier(distance));
 *         } else if (roll < 44) {
 *             t.item = armor(chestTier(distance));
 *         }
 *         return t;
 *     }
 *
 *     // Gear tier found in chests: tier 1 near the start, at most tier 3.
 *     private static int chestTier(int distance) {
 *         int tier = 1;
 *         if (distance > 1) {
 *             tier = 1 + (distance - 1) / 3;
 *         }
 *         return Math.min(tier, 3);
 *     }
 *
 *     // Gear tier sold in the shop: grows with distance, 30% chance of one extra tier, at most tier 4.
 *     private static int shopTier(int distance, Random rnd) {
 *         int tier = (distance + 1) / 2;
 *         if (rnd.nextInt(100) < 30) {
 *             tier = tier + 1;
 *         }
 *         return Math.min(tier, 4);
 *     }
 *
 *     public static Weapon shopWeapon(int distance, Random rnd) {
 *         return weapon(shopTier(distance, rnd));
 *     }
 *
 *     public static Armor shopArmor(int distance, Random rnd) {
 *         return armor(shopTier(distance, rnd));
 *     }
 * }
 */
