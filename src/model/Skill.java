/*
 * Team Project: Void-Dungeon
 * Member: Member 3 (Monsters, Heroes & Skills)
 *
 * File: Skill.java
 *
 * Task Description:
 * A special attack that costs mana. The seven skills are already listed below; you write the code
 * that stores their numbers, rolls their damage and describes them.
 * Damage = (normal attack roll x multiplier) + bonus + (defense x defenseScale), then varied by swing.
 *
 * What to do:
 * 1. Constructor: store name, mana, multiplier, bonus, defenseScale and swing.
 * 2. damage(user): roll the damage this skill deals when the given fighter uses it.
 * 3. info(): one line that describes the skill (see the examples below).
 *
 * Expected Output:
 * === Testing Skill.java (Member 3) ===
 * [PASS] Constructor stores every number
 * [PASS] Whirlwind damage stays in its range
 * [PASS] Shield Slam adds 2x the user's defense
 * [PASS] Fireball adds its flat bonus
 * [PASS] info() text is correct
 * [SUCCESS] Skill self-test passed successfully!
 *
 * Hints:
 * - power = (int) Math.round(user.attackDamage() * multiplier); then power = power + bonus + user.getDefense() * defenseScale;
 *   finally return Dice.vary(power, swing).
 * - info() examples:
 *     Whirlwind:    "1.85x attack roll (final swing +/-15%)."
 *     Fireball:     "1.75x attack roll + 18 bonus damage (final swing +/-20%)."
 *     Shield Slam:  "1.0x attack roll + 2x DEF bonus (final swing +/-15%)."
 *   Start with multiplier + "x attack roll", add " + N bonus damage" only if bonus > 0,
 *   add " + Nx DEF bonus" only if defenseScale > 0, finish with " (final swing +/-N%)." where
 *   N = Math.round(swing * 100).
 */

package model;

public class Skill {
    //                                            name            mana  multiplier  bonus  defenseScale  swing
    public static final Skill SHIELD_SLAM   = new Skill("Shield Slam",   15, 1.0,  0, 2, 0.15);
    public static final Skill WHIRLWIND     = new Skill("Whirlwind",     25, 1.85, 0, 0, 0.15);
    public static final Skill FIREBALL      = new Skill("Fireball",      18, 1.75, 18, 0, 0.20);
    public static final Skill FROST_NOVA    = new Skill("Frost Nova",    16, 1.5,  12, 0, 0.10);
    public static final Skill SHADOW_STRIKE = new Skill("Shadow Strike", 22, 2.1,  0, 0, 0.15);
    public static final Skill POISON_DART   = new Skill("Poison Dart",   14, 1.3,  10, 0, 0.10);
    public static final Skill FLAME_BREATH  = new Skill("Flame Breath",  0,  1.75, 12, 0, 0.20); // the Void Dragon's heavy attack

    public String name;
    public int mana;           // mana cost
    public double multiplier;  // multiplies the normal attack roll
    public int bonus;          // flat damage added on top
    public int defenseScale;   // adds this many times your defense as damage
    public double swing;       // final damage varies by +/- this fraction

    public Skill(String name, int mana, double multiplier, int bonus, int defenseScale, double swing) {
        // TODO: Member 3
        // Store all six values in the fields.
    }

    // Rolls the damage this skill deals when the given fighter uses it.
    public int damage(Fighter user) {
        // TODO: Member 3
        return 0;
    }

    // One line that explains the skill on the hero selection screen.
    public String info() {
        // TODO: Member 3
        return "";
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Skill.java (Member 3) ===");

        Skill f = Skill.FIREBALL;
        if (!f.name.equals("Fireball") || f.mana != 18 || f.multiplier != 1.75 || f.bonus != 18 || f.defenseScale != 0 || f.swing != 0.20) {
            throw new AssertionError("Constructor must store name, mana, multiplier, bonus, defenseScale and swing");
        }
        System.out.println("[PASS] Constructor stores every number");

        Fighter user = new Fighter("User", 100, 100, 10);
        for (int i = 0; i < 500; i++) {
            int d = Skill.WHIRLWIND.damage(user);
            if (d < 130 || d > 250) throw new AssertionError("Whirlwind with 100 attack should deal about 133 to 245, got " + d);
        }
        System.out.println("[PASS] Whirlwind damage stays in its range");

        Fighter weakTank = new Fighter("Tank", 100, 1, 50);
        for (int i = 0; i < 200; i++) {
            int d = Skill.SHIELD_SLAM.damage(weakTank);
            if (d < 80 || d > 125) throw new AssertionError("Shield Slam with 1 attack and 50 defense should deal about 85 to 115, got " + d);
        }
        System.out.println("[PASS] Shield Slam adds 2x the user's defense");

        Fighter noAttack = new Fighter("Zero", 100, 1, 0);
        for (int i = 0; i < 200; i++) {
            int d = Skill.FIREBALL.damage(noAttack);
            if (d < 14 || d > 26) throw new AssertionError("Fireball with 1 attack should deal about 15 to 25, got " + d);
        }
        System.out.println("[PASS] Fireball adds its flat bonus");

        if (!Skill.WHIRLWIND.info().equals("1.85x attack roll (final swing +/-15%).")) throw new AssertionError("Whirlwind info wrong: " + Skill.WHIRLWIND.info());
        if (!Skill.FIREBALL.info().equals("1.75x attack roll + 18 bonus damage (final swing +/-20%).")) throw new AssertionError("Fireball info wrong: " + Skill.FIREBALL.info());
        if (!Skill.SHIELD_SLAM.info().equals("1.0x attack roll + 2x DEF bonus (final swing +/-15%).")) throw new AssertionError("Shield Slam info wrong: " + Skill.SHIELD_SLAM.info());
        System.out.println("[PASS] info() text is correct");

        System.out.println("[SUCCESS] Skill self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // A special attack that costs mana. Damage = (normal attack x multiplier) + bonus + (defense x defenseScale), then varied.
 * public class Skill {
 *     //                                            name            mana  multiplier  bonus  defenseScale  swing
 *     public static final Skill SHIELD_SLAM   = new Skill("Shield Slam",   15, 1.0,  0, 2, 0.15);
 *     public static final Skill WHIRLWIND     = new Skill("Whirlwind",     25, 1.85, 0, 0, 0.15);
 *     public static final Skill FIREBALL      = new Skill("Fireball",      18, 1.75, 18, 0, 0.20);
 *     public static final Skill FROST_NOVA    = new Skill("Frost Nova",    16, 1.5,  12, 0, 0.10);
 *     public static final Skill SHADOW_STRIKE = new Skill("Shadow Strike", 22, 2.1,  0, 0, 0.15);
 *     public static final Skill POISON_DART   = new Skill("Poison Dart",   14, 1.3,  10, 0, 0.10);
 *     public static final Skill FLAME_BREATH  = new Skill("Flame Breath",  0,  1.75, 12, 0, 0.20); // the Void Dragon's heavy attack
 *
 *     public final String name;
 *     public final int mana;           // mana cost
 *     public final double multiplier;  // multiplies the normal attack roll
 *     public final int bonus;          // flat damage added on top
 *     public final int defenseScale;   // adds this many times your defense as damage
 *     public final double swing;       // final damage varies by +/- this fraction
 *
 *     public Skill(String name, int mana, double multiplier, int bonus, int defenseScale, double swing) {
 *         this.name = name;
 *         this.mana = mana;
 *         this.multiplier = multiplier;
 *         this.bonus = bonus;
 *         this.defenseScale = defenseScale;
 *         this.swing = swing;
 *     }
 *
 *     // Rolls the damage this skill deals when the given fighter uses it.
 *     public int damage(Fighter user) {
 *         int power = (int) Math.round(user.attackDamage() * multiplier);
 *         power = power + bonus + user.getDefense() * defenseScale;
 *         return Dice.vary(power, swing);
 *     }
 *
 *     // One line that explains the skill on the hero selection screen.
 *     public String info() {
 *         String text = multiplier + "x attack roll";
 *         if (bonus > 0) {
 *             text = text + " + " + bonus + " bonus damage";
 *         }
 *         if (defenseScale > 0) {
 *             text = text + " + " + defenseScale + "x DEF bonus";
 *         }
 *         return text + " (final swing +/-" + Math.round(swing * 100) + "%).";
 *     }
 * }
 */
