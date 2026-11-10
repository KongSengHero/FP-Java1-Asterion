/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 4 (Adversaries & Game Engine Orchestration)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Boss.java
 *
 * Task Description:
 * Implements the final boss Asterion extending Enemy.
 * Manages Bull Charge cooldown turns, phase 2 Enrage mechanic when health drops to 50% or below, and attack buffs.
 *
 * What to do:
 * 1. Implement constructor Boss() calling super("Asterion", 520, 40, 20, 400, 300) with swing = 0.20.
 * 2. Implement getAttack() adding +6 damage when enraged.
 * 3. Implement heavyNext() returning true when Bull Charge is telegraphed:
 *    - Normal phase: every 3rd turn (turns + 1 >= 3).
 *    - Enraged phase: every 2nd turn (turns + 1 >= 2).
 * 4. Implement startRound() checking if hp * 2 <= maxHp to trigger enraged = true.
 * 5. Implement act(Fighter target):
 *    - If charge ready: executes Skill.BULL_CHARGE.damage(this) and resets turn counter.
 *    - 20% guard chance.
 *    - Otherwise standard attackDamage().
 * 6. Run the main method to verify turn counters, enrage triggers, and Bull Charge damage.
 *
 * Expected Output:
 * === Testing Boss.java (Member 4) ===
 * [PASS] Asterion base stats initialized (520 HP, 40 ATK, 20 DEF)
 * [PASS] Bull Charge triggers on 3rd turn in normal phase
 * [PASS] Enrage activates when HP drops to 50% or below
 * [PASS] Enraged state grants +6 ATK and reduces charge interval to every 2nd turn
 * [SUCCESS] Boss self-test passed successfully!
 *
 * Hints:
 * - heavyNext() informs the UI screen to warn the player to Defend against the incoming Bull Charge.
 * - Enrage condition: hp * 2 <= maxHp (i.e. hp <= 260).
 */

package model;

public class Boss extends Enemy { 
    public boolean enraged = false; 
    private int turns = 0; 
    
    public Boss() { 
        super("Asterion", 520, 40, 20, 400, 300); 
        swing = 0.20; 
    }
    
    public int getAttack() { 
        // TODO: Member 4
        // Base attack + (enraged ? 6 : 0).
        return attack + (enraged ? 6 : 0); 
    }
    
    public boolean heavyNext() { 
        // TODO: Member 4
        // Returns true if Bull Charge is ready next turn:
        // turns + 1 >= (enraged ? 2 : 3).
        return false; 
    }
    
    public void startRound() { 
        // TODO: Member 4
        // If hp * 2 <= maxHp, trigger enraged = true.
    }
    
    public int act(Fighter target) { 
        // TODO: Member 4
        // 1. boolean charge = heavyNext().
        // 2. turns = charge ? 0 : turns + 1.
        // 3. If charge:
        //    - set move = "charge"
        //    - return target.takeDamage(Skill.BULL_CHARGE.damage(this)).
        // 4. If Dice.chance(20):
        //    - set move = "guard", guarding = true, return 0.
        // 5. Default attack:
        //    - set move = "hit"
        //    - return target.takeDamage(attackDamage()).
        return 0; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Boss.java (Member 4) ==="); 
        
        Boss b = new Boss(); 
        if (b.maxHp != 520 || b.attack != 40 || b.defense != 20) { 
            throw new AssertionError("Asterion boss base stats mismatch"); 
        }
        System.out.println("[PASS] Asterion base stats initialized (520 HP, 40 ATK, 20 DEF)"); 
        
        Fighter dummy = new Fighter("Theseus Dummy", 1000, 10, 10); 
        if (b.heavyNext()) throw new AssertionError("heavyNext should be false on turn 1"); 
        b.act(dummy); 
        if (b.heavyNext()) throw new AssertionError("heavyNext should be false on turn 2"); 
        b.act(dummy); 
        if (!b.heavyNext()) throw new AssertionError("heavyNext should be true before turn 3"); 
        b.act(dummy); 
        if (!b.move.equals("charge")) throw new AssertionError("Asterion should have used Bull Charge on turn 3"); 
        System.out.println("[PASS] Bull Charge triggers on 3rd turn in normal phase"); 
        
        b.hp = 260; 
        b.startRound(); 
        if (!b.enraged) throw new AssertionError("Boss should become enraged at 50% HP"); 
        System.out.println("[PASS] Enrage activates when HP drops to 50% or below"); 
        
        if (b.getAttack() != 46) throw new AssertionError("Enraged boss should have 46 ATK (40 + 6)"); 
        b.act(dummy); 
        if (!b.heavyNext()) throw new AssertionError("Enraged boss should telegraph charge on turn 2"); 
        System.out.println("[PASS] Enraged state grants +6 ATK and reduces charge interval to every 2nd turn"); 
        
        System.out.println("[SUCCESS] Boss self-test passed successfully!"); 
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * public class Boss extends Enemy {
 *     public boolean enraged = false;
 *     private int turns = 0;
 *
 *     public Boss() {
 *         super("Asterion", 520, 40, 20, 400, 300);
 *         swing = 0.20;
 *     }
 *
 *     public int getAttack() { return attack + (enraged ? 6 : 0); }
 *
 *     public boolean heavyNext() { return turns + 1 >= (enraged ? 2 : 3); }
 *
 *     public void startRound() {
 *         if (hp * 2 <= maxHp) enraged = true;
 *     }
 *
 *     public int act(Fighter target) {
 *         boolean charge = heavyNext();
 *         turns = charge ? 0 : turns + 1;
 *         if (charge) {
 *             move = "charge";
 *             return target.takeDamage(Skill.BULL_CHARGE.damage(this));
 *         }
 *         if (Dice.chance(20)) {
 *             move = "guard";
 *             guarding = true;
 *             return 0;
 *         }
 *         move = "hit";
 *         return target.takeDamage(attackDamage());
 *     }
 * }
 */
