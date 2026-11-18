/*
 * Team Project: Void-Dungeon
 * Member: Member 4 (Items, Inventory & Paperwork)
 *
 * File: Item.java
 *
 * Task Description:
 * The base of everything you can carry: Weapon, Armor and Potion extend it.
 * It is "abstract": you never write `new Item(...)`, only `new Weapon(...)` and so on.
 *
 * What to do:
 * 1. Constructor: store the name and the price.
 * 2. isUpgradeFor(player): by default an item is never an upgrade (Weapon and Armor change this).
 * 3. The two abstract methods (stats and typeName) are already declared; the children fill them in.
 *
 * Expected Output:
 * === Testing Item.java (Member 4) ===
 * [PASS] Constructor stores name and price
 * [PASS] isUpgradeFor is false by default
 * [SUCCESS] Item self-test passed successfully!
 *
 * Hints:
 * - Use this.name = name; this.price = price;
 */

package model;

public abstract class Item {
    public String name;
    public int price;

    public Item(String name, int price) {
        // TODO: Member 4
        // Store the name and the price.
    }

    // Short text with the numbers of this item, shown in lists.
    public abstract String stats();

    // Label shown in the inventory ("WEAPON", "ARMOR ", "POTION").
    public abstract String typeName();

    // True if this item is better than what the player wears now. Only gear can be an upgrade.
    public boolean isUpgradeFor(Player player) {
        // TODO: Member 4
        // Items are not upgrades by default. (Weapon and Armor override this.)
        return true;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Item.java (Member 4) ===");

        Item test = new Item("Test Item", 42) {
            public String stats() { return "stats"; }
            public String typeName() { return "TEST"; }
        };
        if (!test.name.equals("Test Item") || test.price != 42) throw new AssertionError("Constructor should store name and price");
        System.out.println("[PASS] Constructor stores name and price");

        if (test.isUpgradeFor(new Player(Hero.WARRIOR))) throw new AssertionError("isUpgradeFor should be false by default");
        System.out.println("[PASS] isUpgradeFor is false by default");

        System.out.println("[SUCCESS] Item self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // Anything you can carry in the inventory. Weapon, Armor and Potion extend it.
 * public abstract class Item {
 *     public String name;
 *     public int price;
 *
 *     public Item(String name, int price) {
 *         this.name = name;
 *         this.price = price;
 *     }
 *
 *     // Short text with the numbers of this item, shown in lists.
 *     public abstract String stats();
 *
 *     // Label shown in the inventory ("WEAPON", "ARMOR ", "POTION").
 *     public abstract String typeName();
 *
 *     // True if this item is better than what the player wears now. Only gear can be an upgrade.
 *     public boolean isUpgradeFor(Player player) {
 *         return false;
 *     }
 * }
 */
