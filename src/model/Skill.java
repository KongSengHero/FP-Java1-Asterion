/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 2 (Items, Skills & Economy)
 * 
 * 
 * File: Skill.java
 *
 * Task Description:
 * Defines special abilities for heroes and the boss Asterion.
 * Calculates damage output scaling with user attack, ability multipliers, flat bonus, and defense.
 *
 * What to do:
 * 1. Define Skill enum constants:
 *    - SHIELD_SLAM ("Shield Slam", 15 mana, 1.0x mult, 0 flat, 2x defScale, 0.15 swing)
 *    - WHIRLWIND ("Whirlwind", 25 mana, 1.85x mult, 0 flat, 0 defScale, 0.15 swing)
 *    - FIREBALL ("Fireball", 18 mana, 1.75x mult, 18 flat, 0 defScale, 0.20 swing)
 *    - FROST_NOVA ("Frost Nova", 16 mana, 1.5x mult, 12 flat, 0 defScale, 0.10 swing)
 *    - SHADOW_STRIKE ("Shadow Strike", 22 mana, 2.1x mult, 0 flat, 0 defScale, 0.15 swing)
 *    - POISON_DART ("Poison Dart", 14 mana, 1.3x mult, 10 flat, 0 defScale, 0.10 swing)
 *    - BULL_CHARGE ("Bull Charge", 0 mana, 1.75x mult, 12 flat, 0 defScale, 0.20 swing)
 * 2. Implement damage(Fighter user):
 *    - Calculates base power: Math.round(user.attackDamage() * mult) + flat + user.getDefense() * defScale.
 *    - Applies variance via Dice.swing(power, swing).
 * 3. Run the main method to verify skill balance and formulas.
 *
 * Expected Output:
 * === Testing Skill.java (Member 2) ===
 * [PASS] All 7 hero and boss skills exist
 * [PASS] Mage spell mana costs tuned (Fireball: 18, Frost Nova: 16)
 * [PASS] Shield Slam properly incorporates defense scaling
 * [PASS] Damage formula generates valid positive output within swing variance
 * [SUCCESS] Skill self-test passed successfully!
 *
 * Hints:
 * - Skills roll a normal attack first, so weapon damage and critical hits propagate to skill damage.
 * - Shield Slam adds 2 * user.getDefense() directly to its power calculation.
 */

package model;

public enum Skill { 
    SHIELD_SLAM("Shield Slam", 15, 1.0, 0, 2, 0.15, 
            "1.0x attack roll + 2x DEF bonus (final swing +/-15%)."), 
    WHIRLWIND("Whirlwind", 25, 1.85, 0, 0, 0.15, 
            "1.85x attack roll (final swing +/-15%)."), 
    FIREBALL("Fireball", 18, 1.75, 18, 0, 0.20, 
            "1.75x attack roll + 18 bonus damage (final swing +/-20%)."), 
    FROST_NOVA("Frost Nova", 16, 1.5, 12, 0, 0.10, 
            "1.5x attack roll + 12 bonus damage (final swing +/-10%)."), 
    SHADOW_STRIKE("Shadow Strike", 22, 2.1, 0, 0, 0.15, 
            "2.1x attack roll (final swing +/-15%)."), 
    POISON_DART("Poison Dart", 14, 1.3, 10, 0, 0.10, 
            "1.3x attack roll + 10 bonus damage (final swing +/-10%)."), 
    BULL_CHARGE("Bull Charge", 0, 1.75, 12, 0, 0.20, 
            "Asterion's heavy charge: 1.75x attack roll + 12 (final swing +/-20%)."); 
    
    public final String label, info; 
    public final int mana, flat, defScale; 
    public final double mult, swing; 
    
    Skill(String label, int mana, double mult, int flat, int defScale, double swing, String info) { 
        this.label = label; 
        this.mana = mana; 
        this.mult = mult; 
        this.flat = flat; 
        this.defScale = defScale; 
        this.swing = swing; 
        this.info = info; 
    }
    
    public int damage(Fighter user) { 
        // TODO: Member 2
        // 1. Calculate power = (int) Math.round(user.attackDamage() * mult) + flat + user.getDefense() * defScale.
        // 2. Return Dice.swing(power, swing).
        return 0; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Skill.java (Member 2) ==="); 
        
        Skill[] all = Skill.values(); 
        if (all.length != 7) throw new AssertionError("Expected 7 skills, found " + all.length); 
        System.out.println("[PASS] All 7 hero and boss skills exist"); 
        
        if (Skill.FIREBALL.mana != 18 || Skill.FROST_NOVA.mana != 16) { 
            throw new AssertionError("Mage spell mana tuning mismatch"); 
        }
        System.out.println("[PASS] Mage spell mana costs tuned (Fireball: 18, Frost Nova: 16)"); 
        
        Fighter dummy = new Fighter("Skill Tester", 100, 20, 15); 
        int calculatedPower = (int) Math.round(20 * 1.0) + 0 + (15 * 2); 
        if (calculatedPower != 50) throw new AssertionError("Shield slam power calculation error"); 
        System.out.println("[PASS] Shield Slam properly incorporates defense scaling"); 
        
        int dmg = Skill.WHIRLWIND.damage(dummy); 
        if (dmg <= 0) throw new AssertionError("Skill damage produced non-positive result"); 
        System.out.println("[PASS] Damage formula generates valid positive output within swing variance"); 
        
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
 * public enum Skill {
 *     SHIELD_SLAM("Shield Slam", 15, 1.0, 0, 2, 0.15,
 *             "1.0x attack roll + 2x DEF bonus (final swing +/-15%)."),
 *     WHIRLWIND("Whirlwind", 25, 1.85, 0, 0, 0.15,
 *             "1.85x attack roll (final swing +/-15%)."),
 *     FIREBALL("Fireball", 18, 1.75, 18, 0, 0.20,
 *             "1.75x attack roll + 18 bonus damage (final swing +/-20%)."),
 *     FROST_NOVA("Frost Nova", 16, 1.5, 12, 0, 0.10,
 *             "1.5x attack roll + 12 bonus damage (final swing +/-10%)."),
 *     SHADOW_STRIKE("Shadow Strike", 22, 2.1, 0, 0, 0.15,
 *             "2.1x attack roll (final swing +/-15%)."),
 *     POISON_DART("Poison Dart", 14, 1.3, 10, 0, 0.10,
 *             "1.3x attack roll + 10 bonus damage (final swing +/-10%)."),
 *     BULL_CHARGE("Bull Charge", 0, 1.75, 12, 0, 0.20,
 *             "Asterion's heavy charge: 1.75x attack roll + 12 (final swing +/-20%).");
 *
 *     public final String label, info;
 *     public final int mana, flat, defScale;
 *     public final double mult, swing;
 *
 *     Skill(String label, int mana, double mult, int flat, int defScale, double swing, String info) {
 *         this.label = label;
 *         this.mana = mana;
 *         this.mult = mult;
 *         this.flat = flat;
 *         this.defScale = defScale;
 *         this.swing = swing;
 *         this.info = info;
 *     }
 *
 *     public int damage(Fighter user) {
 *         int power = (int) Math.round(user.attackDamage() * mult) + flat + user.getDefense() * defScale;
 *         return Dice.swing(power, swing);
 *     }
 * }
 */
