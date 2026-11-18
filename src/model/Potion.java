/*
 * Team Project: Void-Dungeon
 * Member: Member 4 (Items, Inventory & Paperwork)
 *
 * File: Potion.java
 *
 * Task Description:
 * A one-use item that restores HP, mana or both. The game has four potions.
 *
 * What to do:
 * 1. Constructor: pass name and price to super(...), then store healHp and mana.
 * 2. The four factory methods, each returning a new Potion:
 *      healing()  "Healing Potion"  110 HP,   0 MP,  price 75
 *      mana()     "Mana Potion"       0 HP,  60 MP,  price 70
 *      mixed()    "Mixed Potion"     75 HP,  30 MP,  price 60
 *      great()    "Great Potion"    140 HP,  55 MP,  price 135
 * 3. stats(): text like "110 HP, 0 MP".   4. typeName(): "POTION".
 *
 * Expected Output:
 * === Testing Potion.java (Member 4) ===
 * [PASS] Constructor stores name, HP, MP and price
 * [PASS] The four potions have the right numbers
 * [PASS] stats() and typeName() give the right text
 * [SUCCESS] Potion self-test passed successfully!
 *
 * Hints:
 * - A factory method is a static method that builds an object: return new Potion("Healing Potion", 110, 0, 75);
 */

package model;

public class Potion extends Item {
    public int healHp;
    public int mana;  // mana restored

    public Potion(String name, int healHp, int mana, int price) {
        super(name, price);
        // TODO: Member 4
        // Store healHp and mana.
    }

    // The four potions of the game.
    public static Potion healing() {
        // TODO: Member 4
        return null;
    }

    public static Potion mana() {
        // TODO: Member 4
        return null;
    }

    public static Potion mixed() {
        // TODO: Member 4
        return null;
    }

    public static Potion great() {
        // TODO: Member 4
        return null;
    }

    public String stats() {
        // TODO: Member 4
        // Return healHp + " HP, " + mana + " MP"
        return "";
    }

    public String typeName() {
        // TODO: Member 4
        return "";
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void check(Potion p, String name, int hp, int mp, int price) {
        if (p == null || !p.name.equals(name) || p.healHp != hp || p.mana != mp || p.price != price) {
            throw new AssertionError(name + " should be " + hp + " HP, " + mp + " MP, price " + price);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Potion.java (Member 4) ===");

        Potion p = new Potion("Test Potion", 10, 20, 30);
        if (!p.name.equals("Test Potion") || p.healHp != 10 || p.mana != 20 || p.price != 30) {
            throw new AssertionError("Constructor should store name, HP, MP and price");
        }
        System.out.println("[PASS] Constructor stores name, HP, MP and price");

        check(Potion.healing(), "Healing Potion", 110, 0, 75);
        check(Potion.mana(), "Mana Potion", 0, 60, 70);
        check(Potion.mixed(), "Mixed Potion", 75, 30, 60);
        check(Potion.great(), "Great Potion", 140, 55, 135);
        System.out.println("[PASS] The four potions have the right numbers");

        if (!Potion.healing().stats().equals("110 HP, 0 MP")) throw new AssertionError("stats() should be \"110 HP, 0 MP\"");
        if (!Potion.healing().typeName().equals("POTION")) throw new AssertionError("typeName() should be POTION");
        System.out.println("[PASS] stats() and typeName() give the right text");

        System.out.println("[SUCCESS] Potion self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // A one-use item that restores HP, mana or both.
 * public class Potion extends Item {
 *     public int healHp;
 *     public int mana;  // mana restored
 *
 *     public Potion(String name, int healHp, int mana, int price) {
 *         super(name, price);
 *         this.healHp = healHp;
 *         this.mana = mana;
 *     }
 *
 *     // The four potions of the game.
 *     public static Potion healing() { return new Potion("Healing Potion", 110, 0, 75); }
 *     public static Potion mana()    { return new Potion("Mana Potion", 0, 60, 70); }
 *     public static Potion mixed()   { return new Potion("Mixed Potion", 75, 30, 60); }
 *     public static Potion great()   { return new Potion("Great Potion", 140, 55, 135); }
 *
 *     public String stats() {
 *         return healHp + " HP, " + mana + " MP";
 *     }
 *
 *     public String typeName() {
 *         return "POTION";
 *     }
 * }
 */
