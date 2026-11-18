/*
 * Team Project: Void-Dungeon
 * Member: Member 3 (Monsters, Heroes & Skills)
 *
 * File: Hero.java
 *
 * Task Description:
 * A hero class the player can pick. Each hero has starting stats, two skills and starting gear.
 * The Warrior is already finished as an example. You build the Mage and the Rogue the same way.
 *
 * What to do:
 * 1. makeMage():  hp 100, mana 120, attack 24, defense 6, crit 0, defendHp 0, defendMana 15,
 *                 skills Fireball + Frost Nova, weapon "Apprentice Wand" (12 ATK, 6% crit, 25 gold),
 *                 armor "Silk Robes" (3 DEF, 20 HP, 30 gold), description:
 *                 "Arcane specialist: stronger spells, high mana and improved durability."
 * 2. makeRogue(): hp 110, mana 65, attack 20, defense 8, crit 10, defendHp 5, defendMana 5,
 *                 skills Shadow Strike + Poison Dart, weapon "Dual Dirk" (9 ATK, 14% crit, 25 gold),
 *                 armor "Shadow Cowl" (3 DEF, 15 HP, 30 gold), description:
 *                 "Fast and deadly: +10% critical hit chance."
 * 3. defendBonus(): text for what Defend gives this hero: "+12 HP", "+15 MP" or "+5 HP, +5 MP".
 *
 * Expected Output:
 * === Testing Hero.java (Member 3) ===
 * [PASS] Three heroes in the ALL list, in menu order
 * [PASS] Warrior stats are correct
 * [PASS] Mage stats, skills and gear are correct
 * [PASS] Rogue stats, skills and gear are correct
 * [PASS] defendBonus text is correct for all three heroes
 * [SUCCESS] Hero self-test passed successfully!
 *
 * Hints:
 * - Copy makeWarrior() and change the values. Skills: new Skill[]{Skill.FIREBALL, Skill.FROST_NOVA}.
 * - defendBonus: if both are above 0 show both, else show only the one that is above 0.
 */

package model;

public class Hero {
    public static final Hero WARRIOR = makeWarrior();
    public static final Hero MAGE = makeMage();
    public static final Hero ROGUE = makeRogue();

    // All heroes in menu order.
    public static final Hero[] ALL = {WARRIOR, MAGE, ROGUE};

    public String name;
    public String description;
    public int hp, mana, attack, defense;
    public int crit;                    // extra critical hit chance in percent
    public int defendHp, defendMana;    // what the Defend action gives back
    public Skill[] skills;
    public Weapon weapon;
    public Armor armor;

    private Hero(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Finished example: use it as your guide for the other two.
    private static Hero makeWarrior() {
        Hero h = new Hero("Warrior", "Heavy armor, huge health and crushing strikes.");
        h.hp = 150;
        h.mana = 40;
        h.attack = 18;
        h.defense = 12;
        h.crit = 0;
        h.defendHp = 12;
        h.defendMana = 0;
        h.skills = new Skill[]{Skill.SHIELD_SLAM, Skill.WHIRLWIND};
        h.weapon = new Weapon("Rusty Claymore", 8, 4, 25);
        h.armor = new Armor("Splint Vest", 5, 25, 30);
        return h;
    }

    private static Hero makeMage() {
        // TODO: Member 3
        // Create the Mage like makeWarrior() does (see the numbers in "What to do" above).
        return null;
    }

    private static Hero makeRogue() {
        // TODO: Member 3
        return null;
    }

    // Text for what Defend gives this hero, for example "+12 HP".
    public String defendBonus() {
        // TODO: Member 3
        return "";
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    private static void need(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Hero.java (Member 3) ===");

        need(Hero.ALL.length == 3 && Hero.ALL[0] == Hero.WARRIOR && Hero.ALL[1] == Hero.MAGE && Hero.ALL[2] == Hero.ROGUE,
                "ALL should be Warrior, Mage, Rogue (all must exist)");
        System.out.println("[PASS] Three heroes in the ALL list, in menu order");

        Hero w = Hero.WARRIOR;
        need(w.name.equals("Warrior") && w.hp == 150 && w.mana == 40 && w.attack == 18 && w.defense == 12 && w.crit == 0
                && w.defendHp == 12 && w.defendMana == 0, "Warrior stats wrong");
        System.out.println("[PASS] Warrior stats are correct");

        Hero m = Hero.MAGE;
        need(m.name.equals("Mage") && m.hp == 100 && m.mana == 120 && m.attack == 24 && m.defense == 6 && m.crit == 0
                && m.defendHp == 0 && m.defendMana == 15, "Mage stats wrong");
        need(m.skills.length == 2 && m.skills[0] == Skill.FIREBALL && m.skills[1] == Skill.FROST_NOVA, "Mage skills should be Fireball and Frost Nova");
        need(m.weapon.name.equals("Apprentice Wand") && m.weapon.attack == 12 && m.weapon.crit == 6 && m.weapon.price == 25, "Mage weapon wrong");
        need(m.armor.name.equals("Silk Robes") && m.armor.defense == 3 && m.armor.maxHp == 20 && m.armor.price == 30, "Mage armor wrong");
        need(m.description.equals("Arcane specialist: stronger spells, high mana and improved durability."), "Mage description wrong");
        System.out.println("[PASS] Mage stats, skills and gear are correct");

        Hero r = Hero.ROGUE;
        need(r.name.equals("Rogue") && r.hp == 110 && r.mana == 65 && r.attack == 20 && r.defense == 8 && r.crit == 10
                && r.defendHp == 5 && r.defendMana == 5, "Rogue stats wrong");
        need(r.skills.length == 2 && r.skills[0] == Skill.SHADOW_STRIKE && r.skills[1] == Skill.POISON_DART, "Rogue skills should be Shadow Strike and Poison Dart");
        need(r.weapon.name.equals("Dual Dirk") && r.weapon.attack == 9 && r.weapon.crit == 14 && r.weapon.price == 25, "Rogue weapon wrong");
        need(r.armor.name.equals("Shadow Cowl") && r.armor.defense == 3 && r.armor.maxHp == 15 && r.armor.price == 30, "Rogue armor wrong");
        need(r.description.equals("Fast and deadly: +10% critical hit chance."), "Rogue description wrong");
        System.out.println("[PASS] Rogue stats, skills and gear are correct");

        need(w.defendBonus().equals("+12 HP"), "Warrior defendBonus should be \"+12 HP\", got \"" + w.defendBonus() + "\"");
        need(m.defendBonus().equals("+15 MP"), "Mage defendBonus should be \"+15 MP\", got \"" + m.defendBonus() + "\"");
        need(r.defendBonus().equals("+5 HP, +5 MP"), "Rogue defendBonus should be \"+5 HP, +5 MP\", got \"" + r.defendBonus() + "\"");
        System.out.println("[PASS] defendBonus text is correct for all three heroes");

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
 * // A hero class the player can pick: starting stats, two skills and starting gear.
 * public class Hero {
 *     public static final Hero WARRIOR = makeWarrior();
 *     public static final Hero MAGE = makeMage();
 *     public static final Hero ROGUE = makeRogue();
 *
 *     // All heroes in menu order.
 *     public static final Hero[] ALL = {WARRIOR, MAGE, ROGUE};
 *
 *     public String name;
 *     public String description;
 *     public int hp, mana, attack, defense;
 *     public int crit;                    // extra critical hit chance in percent
 *     public int defendHp, defendMana;    // what the Defend action gives back
 *     public Skill[] skills;
 *     public Weapon weapon;
 *     public Armor armor;
 *
 *     private Hero(String name, String description) {
 *         this.name = name;
 *         this.description = description;
 *     }
 *
 *     private static Hero makeWarrior() {
 *         Hero h = new Hero("Warrior", "Heavy armor, huge health and crushing strikes.");
 *         h.hp = 150;
 *         h.mana = 40;
 *         h.attack = 18;
 *         h.defense = 12;
 *         h.crit = 0;
 *         h.defendHp = 12;
 *         h.defendMana = 0;
 *         h.skills = new Skill[]{Skill.SHIELD_SLAM, Skill.WHIRLWIND};
 *         h.weapon = new Weapon("Rusty Claymore", 8, 4, 25);
 *         h.armor = new Armor("Splint Vest", 5, 25, 30);
 *         return h;
 *     }
 *
 *     private static Hero makeMage() {
 *         Hero h = new Hero("Mage", "Arcane specialist: stronger spells, high mana and improved durability.");
 *         h.hp = 100;
 *         h.mana = 120;
 *         h.attack = 24;
 *         h.defense = 6;
 *         h.crit = 0;
 *         h.defendHp = 0;
 *         h.defendMana = 15;
 *         h.skills = new Skill[]{Skill.FIREBALL, Skill.FROST_NOVA};
 *         h.weapon = new Weapon("Apprentice Wand", 12, 6, 25);
 *         h.armor = new Armor("Silk Robes", 3, 20, 30);
 *         return h;
 *     }
 *
 *     private static Hero makeRogue() {
 *         Hero h = new Hero("Rogue", "Fast and deadly: +10% critical hit chance.");
 *         h.hp = 110;
 *         h.mana = 65;
 *         h.attack = 20;
 *         h.defense = 8;
 *         h.crit = 10;
 *         h.defendHp = 5;
 *         h.defendMana = 5;
 *         h.skills = new Skill[]{Skill.SHADOW_STRIKE, Skill.POISON_DART};
 *         h.weapon = new Weapon("Dual Dirk", 9, 14, 25);
 *         h.armor = new Armor("Shadow Cowl", 3, 15, 30);
 *         return h;
 *     }
 *
 *     // Text for what Defend gives this hero, for example "+12 HP".
 *     public String defendBonus() {
 *         if (defendHp > 0 && defendMana > 0) {
 *             return "+" + defendHp + " HP, +" + defendMana + " MP";
 *         }
 *         if (defendHp > 0) {
 *             return "+" + defendHp + " HP";
 *         }
 *         return "+" + defendMana + " MP";
 *     }
 * }
 */
