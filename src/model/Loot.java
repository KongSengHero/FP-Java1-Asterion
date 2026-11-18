package model;

import java.util.Random;

// Creates the weapons, armor and potions that appear in chests and in the shop.
public class Loot {

    // Weapon of the given tier (1 = weakest, 4 = strongest).
    public static Weapon weapon(int tier) {
        if (tier == 1) return new Weapon("Stone Sword", 6, 5, 40);
        if (tier == 2) return new Weapon("Iron Sword", 14, 8, 110);
        if (tier == 3) return new Weapon("Obsidian Sword", 24, 12, 260);
        return new Weapon("Dark Blade", 38, 20, 500);
    }

    // Armor of the given tier (1 = weakest, 4 = strongest).
    public static Armor armor(int tier) {
        if (tier == 1) return new Armor("Stone Armor", 4, 20, 40);
        if (tier == 2) return new Armor("Iron Armor", 9, 45, 110);
        if (tier == 3) return new Armor("Obsidian Armor", 16, 80, 260);
        return new Armor("Void Protection", 26, 140, 500);
    }

    // Picks a random potion: Mixed 40%, Healing 27%, Mana 25%, Great 8%.
    public static Potion randomPotion(Random rnd) {
        int roll = rnd.nextInt(100);
        if (roll < 40) return Potion.mixed();
        if (roll < 67) return Potion.healing();
        if (roll < 92) return Potion.mana();
        return Potion.great();
    }

    // Rolls the content of a chest. Farther areas give more gold and better gear.
    public static Treasure rollChest(int distance, Random rnd) {
        Treasure t = new Treasure();
        t.gold = 3 + distance + rnd.nextInt(4);
        int roll = rnd.nextInt(100);
        if (roll < 28) {
            t.item = randomPotion(rnd);
        } else if (roll < 36) {
            t.item = weapon(chestTier(distance));
        } else if (roll < 44) {
            t.item = armor(chestTier(distance));
        }
        return t;
    }

    // Gear tier found in chests: tier 1 near the start, at most tier 3.
    private static int chestTier(int distance) {
        int tier = 1;
        if (distance > 1) {
            tier = 1 + (distance - 1) / 3;
        }
        return Math.min(tier, 3);
    }

    // Gear tier sold in the shop: grows with distance, 30% chance of one extra tier, at most tier 4.
    private static int shopTier(int distance, Random rnd) {
        int tier = (distance + 1) / 2;
        if (rnd.nextInt(100) < 30) {
            tier = tier + 1;
        }
        return Math.min(tier, 4);
    }

    public static Weapon shopWeapon(int distance, Random rnd) {
        return weapon(shopTier(distance, rnd));
    }

    public static Armor shopArmor(int distance, Random rnd) {
        return armor(shopTier(distance, rnd));
    }
}
