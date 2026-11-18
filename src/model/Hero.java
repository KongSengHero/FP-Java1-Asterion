package model;

// A hero class the player can pick: starting stats, two skills and starting gear.
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
        Hero h = new Hero("Mage", "Arcane specialist: stronger spells, high mana and improved durability.");
        h.hp = 100;
        h.mana = 120;
        h.attack = 24;
        h.defense = 6;
        h.crit = 0;
        h.defendHp = 0;
        h.defendMana = 15;
        h.skills = new Skill[]{Skill.FIREBALL, Skill.FROST_NOVA};
        h.weapon = new Weapon("Apprentice Wand", 12, 6, 25);
        h.armor = new Armor("Silk Robes", 3, 20, 30);
        return h;
    }

    private static Hero makeRogue() {
        Hero h = new Hero("Rogue", "Fast and deadly: +10% critical hit chance.");
        h.hp = 110;
        h.mana = 65;
        h.attack = 20;
        h.defense = 8;
        h.crit = 10;
        h.defendHp = 5;
        h.defendMana = 5;
        h.skills = new Skill[]{Skill.SHADOW_STRIKE, Skill.POISON_DART};
        h.weapon = new Weapon("Dual Dirk", 9, 14, 25);
        h.armor = new Armor("Shadow Cowl", 3, 15, 30);
        return h;
    }

    // Text for what Defend gives this hero, for example "+12 HP".
    public String defendBonus() {
        if (defendHp > 0 && defendMana > 0) {
            return "+" + defendHp + " HP, +" + defendMana + " MP";
        }
        if (defendHp > 0) {
            return "+" + defendHp + " HP";
        }
        return "+" + defendMana + " MP";
    }
}
