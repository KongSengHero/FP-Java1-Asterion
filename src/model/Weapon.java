/*
 * Team Project: Void-Dungeon
 * Member: Member 4 (Items, Inventory & Paperwork)
 *
 * File: Weapon.java
 *
 * Task Description:
 * A weapon gives extra attack and extra critical hit chance while it is equipped.
 *
 * What to do:
 * 1. Constructor: pass name and price to super(...), then store attack and crit.
 * 2. stats(): text like "+10 ATK, 5% crit".
 * 3. typeName(): the label "WEAPON".
 * 4. isUpgradeFor(player): true if it has more attack than the player's current weapon,
 *    or the same attack but more crit.
 *
 * Expected Output:
 * === Testing Weapon.java (Member 4) ===
 * [PASS] Constructor stores name, attack, crit and price
 * [PASS] stats() and typeName() give the right text
 * [PASS] isUpgradeFor compares attack first, then crit
 * [SUCCESS] Weapon self-test passed successfully!
 *
 * Hints:
 * - The player's current weapon is player.weapon (the test sets it to 8 ATK, 4% crit).
 * - Compare attack first. Only when the attack is equal, compare crit.
 */

package model;

public class Weapon extends Item {
    public int attack;
    public int crit;  // extra critical hit chance in percent

    public Weapon(String name, int attack, int crit, int price) {
        super(name, price);
        // TODO: Member 4
        // Store attack and crit.
    }

    public String stats() {
        // TODO: Member 4
        // Return "+" + attack + " ATK, " + crit + "% crit"
        return "";
    }

    public String typeName() {
        // TODO: Member 4
        return "";
    }

    // Better if it hits harder, or hits the same but crits more.
    public boolean isUpgradeFor(Player player) {
        // TODO: Member 4
        // 1. Weapon current = player.weapon;
        // 2. If the attack values differ, return attack > current.attack.
        // 3. Otherwise return crit > current.crit.
        return false;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Weapon.java (Member 4) ===");

        Weapon w = new Weapon("Test Sword", 10, 5, 50);
        if (!w.name.equals("Test Sword") || w.attack != 10 || w.crit != 5 || w.price != 50) {
            throw new AssertionError("Constructor should store name, attack, crit and price");
        }
        System.out.println("[PASS] Constructor stores name, attack, crit and price");

        if (!w.stats().equals("+10 ATK, 5% crit")) throw new AssertionError("stats() should be \"+10 ATK, 5% crit\", got \"" + w.stats() + "\"");
        if (!w.typeName().equals("WEAPON")) throw new AssertionError("typeName() should be WEAPON");
        System.out.println("[PASS] stats() and typeName() give the right text");

        Player p = new Player(Hero.WARRIOR);
        p.weapon = new Weapon("Current", 8, 4, 0);  // the weapon the player holds: 8 ATK, 4% crit
        if (!new Weapon("A", 9, 0, 1).isUpgradeFor(p)) throw new AssertionError("more attack is an upgrade");
        if (new Weapon("B", 7, 99, 1).isUpgradeFor(p)) throw new AssertionError("less attack is not an upgrade, even with more crit");
        if (!new Weapon("C", 8, 5, 1).isUpgradeFor(p)) throw new AssertionError("same attack and more crit is an upgrade");
        if (new Weapon("D", 8, 4, 1).isUpgradeFor(p)) throw new AssertionError("identical stats are not an upgrade");
        System.out.println("[PASS] isUpgradeFor compares attack first, then crit");

        System.out.println("[SUCCESS] Weapon self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // Gives extra attack and extra critical hit chance while equipped.
 * public class Weapon extends Item {
 *     public int attack;
 *     public int crit;  // extra critical hit chance in percent
 *
 *     public Weapon(String name, int attack, int crit, int price) {
 *         super(name, price);
 *         this.attack = attack;
 *         this.crit = crit;
 *     }
 *
 *     public String stats() {
 *         return "+" + attack + " ATK, " + crit + "% crit";
 *     }
 *
 *     public String typeName() {
 *         return "WEAPON";
 *     }
 *
 *     // Better if it hits harder, or hits the same but crits more.
 *     public boolean isUpgradeFor(Player player) {
 *         Weapon current = player.weapon;
 *         if (attack != current.attack) {
 *             return attack > current.attack;
 *         }
 *         return crit > current.crit;
 *     }
 * }
 */
