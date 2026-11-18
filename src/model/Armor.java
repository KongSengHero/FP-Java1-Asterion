/*
 * Team Project: Void-Dungeon
 * Member: Member 4 (Items, Inventory & Paperwork)
 *
 * File: Armor.java
 *
 * Task Description:
 * Armor gives extra defense and extra maximum HP while it is equipped.
 *
 * What to do:
 * 1. Constructor: pass name and price to super(...), then store defense and maxHp.
 * 2. stats(): text like "+10 DEF, +50 HP".
 * 3. typeName(): the label "ARMOR " (with one space at the end, so the columns line up with "WEAPON").
 * 4. isUpgradeFor(player): true if it has more defense than the player's current armor,
 *    or the same defense but more HP.
 *
 * Expected Output:
 * === Testing Armor.java (Member 4) ===
 * [PASS] Constructor stores name, defense, HP and price
 * [PASS] stats() and typeName() give the right text
 * [PASS] isUpgradeFor compares defense first, then HP
 * [SUCCESS] Armor self-test passed successfully!
 *
 * Hints:
 * - The player's current armor is player.armor (the test sets it to 5 DEF, 25 HP).
 */

package model;

public class Armor extends Item {
    public int defense;
    public int maxHp;

    public Armor(String name, int defense, int maxHp, int price) {
        super(name, price);
        // TODO: Member 4
        // Store defense and maxHp.
    }

    public String stats() {
        // TODO: Member 4
        // Return "+" + defense + " DEF, +" + maxHp + " HP"
        return "";
    }

    public String typeName() {
        // TODO: Member 4
        return "";
    }

    // Better if it defends more, or defends the same but gives more HP.
    public boolean isUpgradeFor(Player player) {
        // TODO: Member 4
        // Same idea as Weapon: compare defense first, then maxHp.
        return false;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Armor.java (Member 4) ===");

        Armor a = new Armor("Test Plate", 10, 50, 80);
        if (!a.name.equals("Test Plate") || a.defense != 10 || a.maxHp != 50 || a.price != 80) {
            throw new AssertionError("Constructor should store name, defense, HP and price");
        }
        System.out.println("[PASS] Constructor stores name, defense, HP and price");

        if (!a.stats().equals("+10 DEF, +50 HP")) throw new AssertionError("stats() should be \"+10 DEF, +50 HP\", got \"" + a.stats() + "\"");
        if (!a.typeName().equals("ARMOR ")) throw new AssertionError("typeName() should be \"ARMOR \" (with a trailing space)");
        System.out.println("[PASS] stats() and typeName() give the right text");

        Player p = new Player(Hero.WARRIOR);
        p.armor = new Armor("Current", 5, 25, 0);  // the armor the player wears: 5 DEF, 25 HP
        if (!new Armor("A", 6, 0, 1).isUpgradeFor(p)) throw new AssertionError("more defense is an upgrade");
        if (new Armor("B", 4, 999, 1).isUpgradeFor(p)) throw new AssertionError("less defense is not an upgrade, even with more HP");
        if (!new Armor("C", 5, 26, 1).isUpgradeFor(p)) throw new AssertionError("same defense and more HP is an upgrade");
        if (new Armor("D", 5, 25, 1).isUpgradeFor(p)) throw new AssertionError("identical stats are not an upgrade");
        System.out.println("[PASS] isUpgradeFor compares defense first, then HP");

        System.out.println("[SUCCESS] Armor self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // Gives extra defense and extra maximum HP while equipped.
 * public class Armor extends Item {
 *     public int defense;
 *     public int maxHp;
 *
 *     public Armor(String name, int defense, int maxHp, int price) {
 *         super(name, price);
 *         this.defense = defense;
 *         this.maxHp = maxHp;
 *     }
 *
 *     public String stats() {
 *         return "+" + defense + " DEF, +" + maxHp + " HP";
 *     }
 *
 *     public String typeName() {
 *         return "ARMOR ";
 *     }
 *
 *     // Better if it defends more, or defends the same but gives more HP.
 *     public boolean isUpgradeFor(Player player) {
 *         Armor current = player.armor;
 *         if (defense != current.defense) {
 *             return defense > current.defense;
 *         }
 *         return maxHp > current.maxHp;
 *     }
 * }
 */
