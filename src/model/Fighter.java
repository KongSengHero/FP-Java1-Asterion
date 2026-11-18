/*
 * Team Project: Void-Dungeon
 * Member: Member 2 (Fighting & the Player)
 *
 * File: Fighter.java
 *
 * Task Description:
 * Base class of everything that fights (the Player, every Enemy and the Boss).
 * It holds health, attack and defense, and the rules for dealing and taking damage.
 *
 * What to do:
 * 1. Constructor: store name, maxHp, attack, defense. hp starts full (= maxHp).
 * 2. getAttack(), getDefense(), critChance(): return the attack, the defense and baseCrit.
 * 3. attackDamage(): roll the damage of a normal attack, including a possible critical hit.
 * 4. takeDamage(incoming): lower hp, using defense. Guarding doubles the defense for one hit.
 * 5. isDead() and heal(amount).
 *
 * Expected Output:
 * === Testing Fighter.java (Member 2) ===
 * [PASS] Constructor stores the stats and starts at full HP
 * [PASS] Normal hit: 30 incoming against 10 defense deals 25
 * [PASS] Guarded hit: 30 incoming against 10 defense deals 20 and guard ends
 * [PASS] A hit always deals at least 1 damage
 * [PASS] heal restores HP, stops at maxHp and returns what was really healed
 * [PASS] isDead is true at 0 HP
 * [PASS] attackDamage stays in range and critical hits hit harder
 * [SUCCESS] Fighter self-test passed successfully!
 *
 * Hints:
 * - Damage taken = Math.max(1, incoming - defenseUsed / 2). Guarding: defenseUsed = defense * 2.
 * - After any hit, guarding becomes false again.
 * - Never let hp go below 0: hp = Math.max(0, hp - damage).
 * - attackDamage uses Dice.vary(getAttack(), swing) and Dice.chance(critChance()).
 */

package model;

public class Fighter {
    public String name;
    public int maxHp, hp;
    public int attack;
    public int defense;
    public boolean guarding;        // true = the next hit against this fighter is blocked better
    public boolean crit;            // true if the last attack was a critical hit
    protected double baseCrit = 0;  // critical hit chance in percent
    protected double swing = 0.15;  // damage varies by +/- this fraction
    protected double critMult = 1.75;

    public Fighter(String name, int maxHp, int attack, int defense) {
        // TODO: Member 2
        // Store name, maxHp, attack and defense. Set hp = maxHp.
    }

    // The player overrides these three to add weapon and armor bonuses.
    public int getAttack() {
        // TODO: Member 2
        return 0;
    }

    public int getDefense() {
        // TODO: Member 2
        return 0;
    }

    public double critChance() {
        // TODO: Member 2
        return 0;
    }

    // Rolls the damage of a normal attack, including a possible critical hit.
    public int attackDamage() {
        // TODO: Member 2
        // 1. int damage = Dice.vary(getAttack(), swing);
        // 2. crit = Dice.chance(critChance());
        // 3. If crit, damage = (int) Math.round(damage * critMult).
        // 4. Return damage.
        return 0;
    }

    // Lowers hp by the incoming damage minus half the defense (guarding doubles the defense). Returns the damage taken.
    public int takeDamage(int incoming) {
        // TODO: Member 2
        // 1. int defenseUsed = getDefense(); if guarding, double it.
        // 2. int damage = Math.max(1, incoming - defenseUsed / 2);
        // 3. guarding = false;
        // 4. hp = Math.max(0, hp - damage);
        // 5. Return damage.
        return 0;
    }

    public boolean isDead() {
        // TODO: Member 2
        return false;
    }

    // Restores hp (never above maxHp). Returns how much was really healed.
    public int heal(int amount) {
        // TODO: Member 2
        // 1. Remember hp before. 2. hp = Math.min(maxHp, hp + amount). 3. Return hp - before.
        return 0;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Fighter.java (Member 2) ===");

        Fighter f = new Fighter("Test", 100, 20, 10);
        if (!f.name.equals("Test") || f.maxHp != 100 || f.hp != 100 || f.getAttack() != 20 || f.getDefense() != 10 || f.critChance() != 0) {
            throw new AssertionError("Constructor or getters wrong");
        }
        System.out.println("[PASS] Constructor stores the stats and starts at full HP");

        int dealt = f.takeDamage(30);
        if (dealt != 25 || f.hp != 75) throw new AssertionError("Normal hit should deal 25, got " + dealt + " (hp " + f.hp + ")");
        System.out.println("[PASS] Normal hit: 30 incoming against 10 defense deals 25");

        f.guarding = true;
        dealt = f.takeDamage(30);
        if (dealt != 20 || f.hp != 55 || f.guarding) throw new AssertionError("Guarded hit should deal 20 and end the guard, got " + dealt);
        System.out.println("[PASS] Guarded hit: 30 incoming against 10 defense deals 20 and guard ends");

        Fighter tank = new Fighter("Tank", 100, 0, 1000);
        if (tank.takeDamage(5) != 1) throw new AssertionError("A hit must always deal at least 1 damage");
        System.out.println("[PASS] A hit always deals at least 1 damage");

        f.hp = 50;
        if (f.heal(20) != 20 || f.hp != 70) throw new AssertionError("heal(20) should heal 20");
        if (f.heal(100) != 30 || f.hp != 100) throw new AssertionError("heal must stop at maxHp and return 30");
        System.out.println("[PASS] heal restores HP, stops at maxHp and returns what was really healed");

        f.takeDamage(5000);
        if (!f.isDead() || f.hp != 0) throw new AssertionError("isDead should be true and hp exactly 0");
        System.out.println("[PASS] isDead is true at 0 HP");

        Fighter hitter = new Fighter("Hitter", 100, 100, 0);
        hitter.baseCrit = 50;
        boolean sawCrit = false, sawNormal = false;
        for (int i = 0; i < 500; i++) {
            int d = hitter.attackDamage();
            if (hitter.crit) {
                sawCrit = true;
                if (d < 140 || d > 205) throw new AssertionError("critical damage out of range: " + d);
            } else {
                sawNormal = true;
                if (d < 85 || d > 115) throw new AssertionError("normal damage out of range: " + d);
            }
        }
        if (!sawCrit || !sawNormal) throw new AssertionError("with 50% crit chance both crits and normal hits should happen");
        System.out.println("[PASS] attackDamage stays in range and critical hits hit harder");

        System.out.println("[SUCCESS] Fighter self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * // Anything that can fight (the player and every enemy): health, attack, defense and the damage rules.
 * public class Fighter {
 *     public String name;
 *     public int maxHp, hp;
 *     public int attack;
 *     public int defense;
 *     public boolean guarding;        // true = the next hit against this fighter is blocked better
 *     public boolean crit;            // true if the last attack was a critical hit
 *     protected double baseCrit = 0;  // critical hit chance in percent
 *     protected double swing = 0.15;  // damage varies by +/- this fraction
 *     protected double critMult = 1.75;
 *
 *     public Fighter(String name, int maxHp, int attack, int defense) {
 *         this.name = name;
 *         this.maxHp = maxHp;
 *         this.hp = maxHp;
 *         this.attack = attack;
 *         this.defense = defense;
 *     }
 *
 *     // The player overrides these three to add weapon and armor bonuses.
 *     public int getAttack()  { return attack; }
 *     public int getDefense() { return defense; }
 *     public double critChance() { return baseCrit; }
 *
 *     // Rolls the damage of a normal attack, including a possible critical hit.
 *     public int attackDamage() {
 *         int damage = Dice.vary(getAttack(), swing);
 *         crit = Dice.chance(critChance());
 *         if (crit) {
 *             damage = (int) Math.round(damage * critMult);
 *         }
 *         return damage;
 *     }
 *
 *     // Lowers hp by the incoming damage minus half the defense (guarding doubles the defense). Returns the damage taken.
 *     public int takeDamage(int incoming) {
 *         int defenseUsed = getDefense();
 *         if (guarding) {
 *             defenseUsed = defenseUsed * 2;
 *         }
 *         int damage = Math.max(1, incoming - defenseUsed / 2);
 *         guarding = false;
 *         hp = Math.max(0, hp - damage);
 *         return damage;
 *     }
 *
 *     public boolean isDead() {
 *         return hp <= 0;
 *     }
 *
 *     // Restores hp (never above maxHp). Returns how much was really healed.
 *     public int heal(int amount) {
 *         int before = hp;
 *         hp = Math.min(maxHp, hp + amount);
 *         return hp - before;
 *     }
 * }
 */
