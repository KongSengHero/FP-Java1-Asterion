/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 1 (Combat & Hero Systems)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Hero.java
 *
 * Task Description:
 * Playable class enumerations (Warrior, Mage, Rogue) defining base attributes,
 * combat recovery bonuses, unique class skills, and starting gear.
 *
 * What to do:
 * 1. Define enum constants:
 *    - WARRIOR (HP 150, Mana 40, ATK 18, DEF 12, Crit 0%, Defend +12 HP / +0 MP)
 *    - MAGE (HP 100, Mana 120, ATK 24, DEF 6, Crit 0%, Defend +0 HP / +15 MP)
 *    - ROGUE (HP 110, Mana 65, ATK 20, DEF 8, Crit 10%, Defend +5 HP / +5 MP)
 * 2. Implement constructor Hero(...) storing all class attributes.
 * 3. Implement defendBonus() formatting class defend recovery into a readable string.
 * 4. Run the main method to verify class balance and starting items.
 *
 * Expected Output:
 * === Testing Hero.java (Member 1) ===
 * [PASS] Warrior stats verified (Tanky: 150 HP, Defend +12 HP)
 * [PASS] Mage stats verified (Caster: 120 Mana, Defend +15 MP)
 * [PASS] Rogue stats verified (Speed/Crit: 10% Crit, Defend +5 HP, +5 MP)
 * [PASS] defendBonus() string formatting matches requirements
 * [SUCCESS] Hero self-test passed successfully!
 *
 * Hints:
 * - Starting weapon and armor items are passed directly in enum constructor.
 * - Warrior equips Rusty Claymore and Splint Vest.
 * - Mage equips Apprentice Wand and Silk Robes.
 * - Rogue equips Dual Dirk and Shadow Cowl.
 */

package model;

public enum Hero { 
    WARRIOR("Warrior", "Heavy armor, huge health and crushing strikes.", 
            150, 40, 18, 12, 0, 12, 0, Skill.SHIELD_SLAM, Skill.WHIRLWIND, 
            new Item("Rusty Claymore", Item.WEAPON, 8, 4, 25), new Item("Splint Vest", Item.ARMOR, 5, 25, 30)), 
    MAGE("Mage", "Arcane specialist: stronger spells, high mana and improved durability.", 
            100, 120, 24, 6, 0, 0, 15, Skill.FIREBALL, Skill.FROST_NOVA, 
            new Item("Apprentice Wand", Item.WEAPON, 12, 6, 25), new Item("Silk Robes", Item.ARMOR, 3, 20, 30)), 
    ROGUE("Rogue", "Fast and deadly: +10% critical hit chance.", 
            110, 65, 20, 8, 10, 5, 5, Skill.SHADOW_STRIKE, Skill.POISON_DART, 
            new Item("Dual Dirk", Item.WEAPON, 9, 14, 25), new Item("Shadow Cowl", Item.ARMOR, 3, 15, 30)); 
    
    public final String label, info; 
    public final int hp, mana, atk, def, crit; 
    public final int defendHp, defendMana; 
    public final Skill[] skills; 
    public final Item weapon, armor; 
    
    Hero(String label, String info, int hp, int mana, int atk, int def, int crit, 
         int defendHp, int defendMana, Skill skill1, Skill skill2, Item weapon, Item armor) { 
        this.label = label; 
        this.info = info; 
        this.hp = hp; 
        this.mana = mana; 
        this.atk = atk; 
        this.def = def; 
        this.crit = crit; 
        this.defendHp = defendHp; 
        this.defendMana = defendMana; 
        this.skills = new Skill[]{skill1, skill2}; 
        this.weapon = weapon; 
        this.armor = armor; 
    }
    
    public String defendBonus() { 
        // TODO: Member 1
        // Format the recovery bonus string when hero defends:
        // - If defendHp > 0 and defendMana > 0, return "+X HP, +Y MP"
        // - If only defendHp > 0, return "+X HP"
        // - If only defendMana > 0, return "+Y MP"
        return ""; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Hero.java (Member 1) ==="); 
        
        Hero w = Hero.WARRIOR; 
        if (w.hp != 150 || w.def != 12 || w.defendHp != 12 || w.skills.length != 2) { 
            throw new AssertionError("Warrior configuration mismatch"); 
        }
        System.out.println("[PASS] Warrior stats verified (Tanky: 150 HP, Defend +12 HP)"); 
        
        Hero m = Hero.MAGE; 
        if (m.hp != 100 || m.mana != 120 || m.defendMana != 15) { 
            throw new AssertionError("Mage configuration mismatch"); 
        }
        System.out.println("[PASS] Mage stats verified (Caster: 120 Mana, Defend +15 MP)"); 
        
        Hero r = Hero.ROGUE; 
        if (r.hp != 110 || r.crit != 10 || r.defendHp != 5 || r.defendMana != 5) { 
            throw new AssertionError("Rogue configuration mismatch"); 
        }
        System.out.println("[PASS] Rogue stats verified (Speed/Crit: 10% Crit, Defend +5 HP, +5 MP)"); 
        
        if (!w.defendBonus().equals("+12 HP") || !m.defendBonus().equals("+15 MP") || !r.defendBonus().equals("+5 HP, +5 MP")) { 
            throw new AssertionError("defendBonus string formatting mismatch"); 
        }
        System.out.println("[PASS] defendBonus() string formatting matches requirements"); 
        
        System.out.println("[SUCCESS] Hero self-test passed successfully!"); 
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * public enum Hero {
 *     WARRIOR("Warrior", "Heavy armor, huge health and crushing strikes.",
 *             150, 40, 18, 12, 0, 12, 0, Skill.SHIELD_SLAM, Skill.WHIRLWIND,
 *             new Item("Rusty Claymore", Item.WEAPON, 8, 4, 25), new Item("Splint Vest", Item.ARMOR, 5, 25, 30)),
 *     MAGE("Mage", "Arcane specialist: stronger spells, high mana and improved durability.",
 *             100, 120, 24, 6, 0, 0, 15, Skill.FIREBALL, Skill.FROST_NOVA,
 *             new Item("Apprentice Wand", Item.WEAPON, 12, 6, 25), new Item("Silk Robes", Item.ARMOR, 3, 20, 30)),
 *     ROGUE("Rogue", "Fast and deadly: +10% critical hit chance.",
 *             110, 65, 20, 8, 10, 5, 5, Skill.SHADOW_STRIKE, Skill.POISON_DART,
 *             new Item("Dual Dirk", Item.WEAPON, 9, 14, 25), new Item("Shadow Cowl", Item.ARMOR, 3, 15, 30));
 *
 *     public final String label, info;
 *     public final int hp, mana, atk, def, crit;
 *     public final int defendHp, defendMana;
 *     public final Skill[] skills;
 *     public final Item weapon, armor;
 *
 *     Hero(String label, String info, int hp, int mana, int atk, int def, int crit,
 *          int defendHp, int defendMana, Skill skill1, Skill skill2, Item weapon, Item armor) {
 *         this.label = label;
 *         this.info = info;
 *         this.hp = hp;
 *         this.mana = mana;
 *         this.atk = atk;
 *         this.def = def;
 *         this.crit = crit;
 *         this.defendHp = defendHp;
 *         this.defendMana = defendMana;
 *         this.skills = new Skill[]{skill1, skill2};
 *         this.weapon = weapon;
 *         this.armor = armor;
 *     }
 *
 *     public String defendBonus() {
 *         if (defendHp > 0 && defendMana > 0) return "+" + defendHp + " HP, +" + defendMana + " MP";
 *         if (defendHp > 0) return "+" + defendHp + " HP";
 *         return "+" + defendMana + " MP";
 *     }
 * }
 */
