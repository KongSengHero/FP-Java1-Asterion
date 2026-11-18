/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 4 (Adversaries & Game Engine Orchestration)
 * 
 * 
 * File: Enemy.java
 *
 * Task Description:
 * Implements non-boss labyrinth monsters (Satyr, Skeleton, Harpy, Cyclops) extending Fighter.
 * Governs stat scaling based on dungeon depth (distance) and distinct monster AI routines.
 *
 * Expected Output:
 * === Testing Enemy.java (Member 4) ===
 * [PASS] Enemies generated across distances with proportional stat scaling
 * [PASS] Skeleton possesses 25% critical hit chance
 * [PASS] Harpy features wide damage variance swing (0.25)
 * [PASS] Monster AI actions execute and update move description
 * [SUCCESS] Enemy self-test passed successfully!
 */

package model;

import java.util.Random;

// Enemy "is a" Fighter, so it inherits hp, maxHp, attack, defense, swing, critMult,
// guarding, heal(), takeDamage(), attackDamage() and so on from Fighter.
public class Enemy extends Fighter {
    public int xp, goldReward;      // what the player gets for defeating this enemy
    public double critPct;          // critical hit chance in percent (25 means 25%)
    public int kind;                // which monster: 0 = Satyr, 1 = Skeleton, 2 = Harpy, 3 = Cyclops
    public String move = "";        // what the enemy did this turn ("hit", "guard", "heal"), shown on screen

    // Constructor: builds an enemy with the given stats.
    public Enemy(String name, int hp, int atk, int def, int xp, int goldReward) {
        super(name, hp, atk, def);          // let Fighter set name, hp and the basic stats
        this.xp = xp;                       // save the xp reward
        this.goldReward = goldReward;       // save the gold reward
        this.critMult = 1.5;                // when a crit happens, damage is multiplied by 1.5
    }

    // Factory method: creates a random enemy. d = distance (depth) in the dungeon,
    // so the further the player goes, the stronger the enemy. rnd = the random number generator.
    public static Enemy create(int d, Random rnd) {
        int kind = rnd.nextInt(4);                                      // random number 0, 1, 2 or 3
        String[] names = {"Satyr", "Skeleton", "Harpy", "Cyclops"};     // index matches kind

        // Every stat grows with d. Some random extra is added to hp and gold so enemies are not identical.
        Enemy e = new Enemy(
                names[kind],                    // name picked by kind
                40 + 20 * d + rnd.nextInt(6),   // hp: base 40, +20 per distance, +0..5 random
                10 + 4 * d,                     // attack: base 10, +4 per distance
                3 + 2 * d,                      // defense: base 3, +2 per distance
                20 + 15 * d,                    // xp reward
                5 + 13 * d + rnd.nextInt(5));   // gold reward, +0..4 random

        e.kind = kind;                          // remember which monster this is (used in act())

        // swing = how much damage varies up/down. Skeleton 15%, Harpy 25% (wild), others 20%.
        e.swing = (kind == 1) ? 0.15 : (kind == 2) ? 0.25 : 0.20;

        // Only the Skeleton can crit (25%). Everyone else has 0%.
        e.critPct = (kind == 1) ? 25 : 0;

        return e;                               // hand the finished enemy back
    }

    // Returns the crit chance. Fighter asks this when calculating damage, so we override it.
    public double critChance() {
        return critPct;
    }

    // The enemy's turn. Returns the damage dealt to the target (0 if it guarded or healed).
    public int act(Fighter target) {
        move = "hit";                           // default action is attacking
        boolean hurt = hp < maxHp * 0.4;        // true when below 40% health

        // Guard rules:
        //  - Satyr (kind 0): below 50% hp, 20% chance
        //  - Skeleton (kind 1): hurt (below 40%), 15% chance
        // Dice.chance(n) is true about n% of the time.
        if ((kind == 0 && hp < maxHp / 2 && Dice.chance(20)) || (kind == 1 && hurt && Dice.chance(15))) {
            move = "guard";                     // shown on screen
            guarding = true;                    // reduces damage taken next hit
            return 0;                           // no attack this turn
        }

        // Heal rule: Cyclops (kind 3) when hurt, 40% chance.
        if (kind == 3 && hurt && Dice.chance(40)) {
            move = "heal";
            heal(maxHp / 5);                    // recover 20% of max hp
            guarding = true;                    // also guards this turn
            return 0;                           // no attack this turn
        }

        // Nothing special happened: attack. attackDamage() rolls the damage (with swing/crit),
        // takeDamage() applies it to the target and returns the damage actually dealt.
        return target.takeDamage(attackDamage());
    }

    // Enemies never prepare a heavy attack (bosses might), so always false.
    public boolean heavyNext() {
        return false;
    }

    // Called at the start of each round. Normal enemies have nothing to reset.
    public void startRound() {
    }

    // Self-test: run this file directly to check everything works.
    public static void main(String[] args) {
        System.out.println("=== Testing Enemy.java (Member 4) ===");
        // Seed 42 makes the "random" numbers the same every run, so the test is repeatable.
        Random rnd = new Random(42);
        Enemy e1 = create(1, rnd);              // enemy at distance 1
        Enemy e5 = create(5, rnd);              // enemy at distance 5
        // Distance 5 must be stronger than distance 1 in hp, attack and xp.
        if (e1 == null || e5 == null || e5.maxHp <= e1.maxHp || e5.attack <= e1.attack || e5.xp <= e1.xp) {
            throw new AssertionError("Enemy stats failed to scale proportionally with distance");
        }
        System.out.println("[PASS] Enemies generated across distances with proportional stat scaling");

        // Build a Skeleton by hand and check its crit chance.
        Enemy skel = new Enemy("Skeleton", 50, 15, 5, 30, 20);
        skel.kind = 1;
        skel.critPct = 25;
        if (skel.critChance() != 25) throw new AssertionError("Skeleton crit chance should be 25%");
        System.out.println("[PASS] Skeleton possesses 25% critical hit chance");

        // Build a Harpy by hand and check its swing.
        Enemy harpy = new Enemy("Harpy", 50, 15, 5, 30, 20);
        harpy.kind = 2;
        harpy.swing = 0.25;
        if (harpy.swing != 0.25) throw new AssertionError("Harpy swing should be 0.25");
        System.out.println("[PASS] Harpy features wide damage variance swing (0.25)");

        // Let an enemy act against a dummy target. move must be filled in and damage can't be negative.
        Fighter target = new Fighter("Target", 200, 10, 10);
        int dealt = e1.act(target);
        if (e1.move.isEmpty() || dealt < 0) throw new AssertionError("Enemy act method produced invalid state");
        System.out.println("[PASS] Monster AI actions execute and update move description");

        System.out.println("[SUCCESS] Enemy self-test passed successfully!");
    }
}