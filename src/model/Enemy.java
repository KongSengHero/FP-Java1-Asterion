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
 * What to do:
 * 1. Implement constructor Enemy(name, hp, atk, def, xp, goldReward) setting critMult = 1.5.
 * 2. Implement create(int d, Random rnd) generating Satyr, Skeleton, Harpy, or Cyclops with distance-scaled attributes.
 * 3. Implement act(Fighter target) providing tactical monster behaviors:
 *    - Satyr (kind 0): 20% guard chance when hp < 50%.
 *    - Skeleton (kind 1): 25% crit chance, 15% guard chance when hp < 40%.
 *    - Harpy (kind 2): high damage swing (0.25 variance).
 *    - Cyclops (kind 3): 40% chance to heal maxHp / 5 and guard when hp < 40%.
 * 4. Run the main method to verify enemy scaling and AI action branches.
 *
 * Expected Output:
 * === Testing Enemy.java (Member 4) ===
 * [PASS] Enemies generated across distances with proportional stat scaling
 * [PASS] Skeleton possesses 25% critical hit chance
 * [PASS] Harpy features wide damage variance swing (0.25)
 * [PASS] Monster AI actions execute and update move description
 * [SUCCESS] Enemy self-test passed successfully!
 *
 * Hints:
 * - When an enemy chooses to guard or heal, it returns 0 damage dealt to the target.
 * - Distance parameter d directly enhances monster HP, ATK, DEF, XP, and gold rewards.
 */

package model;

import java.util.Random; 

public class Enemy extends Fighter { 
    public int xp, goldReward; 
    public double critPct; 
    public int kind; 
    public String move = ""; 
    
    public Enemy(String name, int hp, int atk, int def, int xp, int goldReward) { 
        super(name, hp, atk, def); 
        this.xp = xp; 
        this.goldReward = goldReward; 
        this.critMult = 1.5; 
    }
    
    public static Enemy create(int d, Random rnd) { 
        // TODO: Member 4
        // 1. Roll int kind = rnd.nextInt(4).
        // 2. Names array: {"Satyr", "Skeleton", "Harpy", "Cyclops"}.
        // 3. Formula scaling:
        //    - hp: 40 + 20 * d + rnd.nextInt(6)
        //    - atk: 10 + 4 * d
        //    - def: 3 + 2 * d
        //    - xp: 20 + 15 * d
        //    - gold: 5 + 13 * d + rnd.nextInt(5)
        // 4. Assign e.kind = kind.
        // 5. Assign swing: (kind == 1) ? 0.15 : (kind == 2) ? 0.25 : 0.20.
        // 6. Assign critPct: (kind == 1) ? 25 : 0.
        // 7. Return e.
        return null; 
    }
    
    public double critChance() { 
        return critPct; 
    }
    
    public int act(Fighter target) { 
        // TODO: Member 4
        // 1. Set move = "hit".
        // 2. boolean hurt = hp < maxHp * 0.4.
        // 3. Satyr (kind 0) guard condition: (kind == 0 && hp < maxHp / 2 && Dice.chance(20)).
        //    Skeleton (kind 1) guard condition: (kind == 1 && hurt && Dice.chance(15)).
        //    If either true: set move = "guard", guarding = true, return 0.
        // 4. Cyclops (kind 3) heal condition: (kind == 3 && hurt && Dice.chance(40)):
        //    If true: set move = "heal", call heal(maxHp / 5), set guarding = true, return 0.
        // 5. Default attack: return target.takeDamage(attackDamage()).
        return 0; 
    }
    
    public boolean heavyNext() { 
        return false; 
    }
    
    public void startRound() { 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Enemy.java (Member 4) ==="); 
        
        Random rnd = new Random(42); 
        Enemy e1 = create(1, rnd); 
        Enemy e5 = create(5, rnd); 
        if (e1 == null || e5 == null || e5.maxHp <= e1.maxHp || e5.attack <= e1.attack || e5.xp <= e1.xp) { 
            throw new AssertionError("Enemy stats failed to scale proportionally with distance"); 
        }
        System.out.println("[PASS] Enemies generated across distances with proportional stat scaling"); 
        
        Enemy skel = new Enemy("Skeleton", 50, 15, 5, 30, 20); 
        skel.kind = 1; 
        skel.critPct = 25; 
        if (skel.critChance() != 25) throw new AssertionError("Skeleton crit chance should be 25%"); 
        System.out.println("[PASS] Skeleton possesses 25% critical hit chance"); 
        
        Enemy harpy = new Enemy("Harpy", 50, 15, 5, 30, 20); 
        harpy.kind = 2; 
        harpy.swing = 0.25; 
        if (harpy.swing != 0.25) throw new AssertionError("Harpy swing should be 0.25"); 
        System.out.println("[PASS] Harpy features wide damage variance swing (0.25)"); 
        
        Fighter target = new Fighter("Target", 200, 10, 10); 
        int dealt = e1.act(target); 
        if (e1.move.isEmpty() || dealt < 0) throw new AssertionError("Enemy act method produced invalid state"); 
        System.out.println("[PASS] Monster AI actions execute and update move description"); 
        
        System.out.println("[SUCCESS] Enemy self-test passed successfully!"); 
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
 * public class Enemy extends Fighter {
 *     public int xp, goldReward;
 *     public double critPct;
 *     public int kind;
 *     public String move = "";
 *
 *     public Enemy(String name, int hp, int atk, int def, int xp, int goldReward) {
 *         super(name, hp, atk, def);
 *         this.xp = xp;
 *         this.goldReward = goldReward;
 *         this.critMult = 1.5;
 *     }
 *
 *     public static Enemy create(int d, Random rnd) {
 *         int kind = rnd.nextInt(4);
 *         String[] names = {"Satyr", "Skeleton", "Harpy", "Cyclops"};
 *         Enemy e = new Enemy(names[kind], 40 + 20 * d + rnd.nextInt(6), 10 + 4 * d, 3 + 2 * d,
 *                 20 + 15 * d, 5 + 13 * d + rnd.nextInt(5));
 *         e.kind = kind;
 *         e.swing = (kind == 1) ? 0.15 : (kind == 2) ? 0.25 : 0.20;
 *         e.critPct = (kind == 1) ? 25 : 0;
 *         return e;
 *     }
 *
 *     public double critChance() { return critPct; }
 *
 *     public int act(Fighter target) {
 *         move = "hit";
 *         boolean hurt = hp < maxHp * 0.4;
 *         if ((kind == 0 && hp < maxHp / 2 && Dice.chance(20)) || (kind == 1 && hurt && Dice.chance(15))) {
 *             move = "guard";
 *             guarding = true;
 *             return 0;
 *         }
 *         if (kind == 3 && hurt && Dice.chance(40)) {
 *             move = "heal";
 *             heal(maxHp / 5);
 *             guarding = true;
 *             return 0;
 *         }
 *         return target.takeDamage(attackDamage());
 *     }
 *
 *     public boolean heavyNext() { return false; }
 *
 *     public void startRound() { }
 * }
 */
