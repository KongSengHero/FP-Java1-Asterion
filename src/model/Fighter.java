/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 1 (Combat & Hero Systems)
 * 
 * 
 * File: Fighter.java
 *
 * Task Description:
 * Base class for all combatants in the game (Player, Enemy, Boss).
 * Implements core damage calculations, defense absorption, guarding mechanics, and health management.
 *
 * What to do:
 * 1. Implement constructor Fighter(name, maxHp, attack, defense).
 * 2. Implement getAttack(), getDefense(), and critChance() (defaults to 0%).
 * 3. Implement attackDamage():
 *    - Rolls base damage with Dice.swing(getAttack(), swing).
 *    - Checks for critical hit with Dice.chance(critChance()).
 *    - Multiplies damage by critMult if critical hit.
 * 4. Implement takeDamage(int raw):
 *    - Defense absorption: uses getDefense() * (guarding ? 2 : 1).
 *    - Actual damage = Math.max(1, raw - (absorption / 2)).
 *    - Consumes guard state (guarding = false).
 *    - Reduces hp down to a minimum of 0.
 * 5. Implement isDead() and heal(int amount).
 *
 * Expected Output:
 * === Testing Fighter.java (Member 1) ===
 * [PASS] Initial stats match constructor arguments
 * [PASS] Normal takeDamage absorbs half defense value (30 raw - 10/2 = 25 damage)
 * [PASS] Guarded takeDamage doubles defense absorption (30 raw - 20/2 = 20 damage)
 * [PASS] Guard stance resets after single hit
 * [PASS] Heal properly restores HP and is capped at maxHp
 * [PASS] isDead returns true when HP hits 0
 * [SUCCESS] Fighter self-test passed successfully!
 *
 * Hints:
 * - Math.max(1, ...) prevents damage from ever becoming zero or negative.
 * - Guard doubles the defense stat before division by 2, absorbing double.
 */

package model;

public class Fighter { 
    public String name; 
    public int maxHp, hp; 
    public int attack; 
    public int defense; 
    public boolean guarding; 
    public boolean crit; 
    protected double swing = 0.15; 
    protected double critMult = 1.75; 
    
    public Fighter(String name, int maxHp, int attack, int defense) { 
        // TODO: Member 1
        // Initialize name, maxHp, hp (starts at maxHp), attack, and defense.
        this.name = name; 
        this.maxHp = maxHp; 
        this.hp = maxHp; 
        this.attack = attack; 
        this.defense = defense; 
    }
    
    public int getAttack() { 
        // TODO: Member 1
        // Return attack power.
        return attack; 
    }
    
    public int getDefense() { 
        // TODO: Member 1
        // Return defense power.
        return defense; 
    }
    
    public double critChance() { 
        // TODO: Member 1
        // Return critical hit chance percentage (default 0.0 for base fighter).
        return 0; 
    }
    
    public int attackDamage() { 
        // TODO: Member 1
        // 1. Roll base damage using Dice.swing(getAttack(), swing).
        // 2. Set crit = Dice.chance(critChance()).
        // 3. Return (int) Math.round(damage * critMult) if crit is true, else return damage.
        return 0; 
    }
    
    public int takeDamage(int raw) { 
        // TODO: Member 1
        // 1. defenseMultiplier is 2 if guarding is true, otherwise 1.
        // 2. Calculate damage = Math.max(1, raw - (getDefense() * defenseMultiplier) / 2).
        // 3. Reset guarding = false.
        // 4. Reduce hp = Math.max(0, hp - damage).
        // 5. Return damage.
        return 0; 
    }
    
    public boolean isDead() { 
        // TODO: Member 1
        // Return true if hp is less than or equal to 0.
        return false; 
    }
    
    public int heal(int amount) { 
        // TODO: Member 1
        // 1. Store int before = hp.
        // 2. hp = Math.min(maxHp, hp + amount).
        // 3. Return hp - before (the actual amount healed).
        return 0; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Fighter.java (Member 1) ==="); 
        
        Fighter test = new Fighter("Warrior Test", 100, 20, 10); 
        if (!test.name.equals("Warrior Test") || test.hp != 100 || test.getAttack() != 20 || test.getDefense() != 10) { 
            throw new AssertionError("Fighter failed initial stats validation"); 
        }
        System.out.println("[PASS] Initial stats match constructor arguments"); 
        
        int dealt = test.takeDamage(30); 
        if (dealt != 25 || test.hp != 75) { 
            throw new AssertionError("takeDamage calculation error: expected 25 dealt and 75 hp, got " + dealt + " and " + test.hp); 
        }
        System.out.println("[PASS] Normal takeDamage absorbs half defense value (30 raw - 10/2 = 25 damage)"); 
        
        test.guarding = true; 
        int guarded = test.takeDamage(30); 
        if (guarded != 20 || test.hp != 55) { 
            throw new AssertionError("guarded takeDamage error: expected 20 dealt and 55 hp, got " + guarded + " and " + test.hp); 
        }
        System.out.println("[PASS] Guarded takeDamage doubles defense absorption (30 raw - 20/2 = 20 damage)"); 
        
        if (test.guarding) { 
            throw new AssertionError("guarding should reset to false after taking damage"); 
        }
        System.out.println("[PASS] Guard stance resets after single hit"); 
        
        int healed = test.heal(25); 
        if (healed != 25 || test.hp != 80) throw new AssertionError("heal calculation failed"); 
        int overHealed = test.heal(50); 
        if (overHealed != 20 || test.hp != 100) throw new AssertionError("heal should cap at maxHp"); 
        System.out.println("[PASS] Heal properly restores HP and is capped at maxHp"); 
        
        test.takeDamage(500); 
        if (!test.isDead() || test.hp != 0) throw new AssertionError("isDead should return true when hp <= 0"); 
        System.out.println("[PASS] isDead returns true when HP hits 0"); 
        
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
 * public class Fighter {
 *     public String name;
 *     public int maxHp, hp;
 *     public int attack;
 *     public int defense;
 *     public boolean guarding;
 *     public boolean crit;
 *     protected double swing = 0.15;
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
 *     public int getAttack()  { return attack; }
 *     public int getDefense() { return defense; }
 *     public double critChance() { return 0; }
 *
 *     public int attackDamage() {
 *         int damage = Dice.swing(getAttack(), swing);
 *         crit = Dice.chance(critChance());
 *         return crit ? (int) Math.round(damage * critMult) : damage;
 *     }
 *
 *     public int takeDamage(int raw) {
 *         int defenseMultiplier = guarding ? 2 : 1;
 *         int damage = Math.max(1, raw - (getDefense() * defenseMultiplier) / 2);
 *         if (guarding) guarding = false;
 *         hp = Math.max(0, hp - damage);
 *         return damage;
 *     }
 *
 *     public boolean isDead() { return hp <= 0; }
 *
 *     public int heal(int amount) {
 *         int before = hp;
 *         hp = Math.min(maxHp, hp + amount);
 *         return hp - before;
 *     }
 * }
 */
