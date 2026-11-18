package model;

// A special attack that costs mana. Damage = (normal attack x multiplier) + bonus + (defense x defenseScale), then varied.
public class Skill {
    //                                            name            mana  multiplier  bonus  defenseScale  swing
    public static final Skill SHIELD_SLAM   = new Skill("Shield Slam",   15, 1.0,  0, 2, 0.15);
    public static final Skill WHIRLWIND     = new Skill("Whirlwind",     25, 1.85, 0, 0, 0.15);
    public static final Skill FIREBALL      = new Skill("Fireball",      18, 1.75, 18, 0, 0.20);
    public static final Skill FROST_NOVA    = new Skill("Frost Nova",    16, 1.5,  12, 0, 0.10);
    public static final Skill SHADOW_STRIKE = new Skill("Shadow Strike", 22, 2.1,  0, 0, 0.15);
    public static final Skill POISON_DART   = new Skill("Poison Dart",   14, 1.3,  10, 0, 0.10);
    public static final Skill FLAME_BREATH  = new Skill("Flame Breath",  0,  1.75, 12, 0, 0.20); // the Void Dragon's heavy attack

    public final String name;
    public final int mana;           // mana cost
    public final double multiplier;  // multiplies the normal attack roll
    public final int bonus;          // flat damage added on top
    public final int defenseScale;   // adds this many times your defense as damage
    public final double swing;       // final damage varies by +/- this fraction

    public Skill(String name, int mana, double multiplier, int bonus, int defenseScale, double swing) {
        this.name = name;
        this.mana = mana;
        this.multiplier = multiplier;
        this.bonus = bonus;
        this.defenseScale = defenseScale;
        this.swing = swing;
    }

    // Rolls the damage this skill deals when the given fighter uses it.
    public int damage(Fighter user) {
        int power = (int) Math.round(user.attackDamage() * multiplier);
        power = power + bonus + user.getDefense() * defenseScale;
        return Dice.vary(power, swing);
    }

    // One line that explains the skill on the hero selection screen.
    public String info() {
        String text = multiplier + "x attack roll";
        if (bonus > 0) {
            text = text + " + " + bonus + " bonus damage";
        }
        if (defenseScale > 0) {
            text = text + " + " + defenseScale + "x DEF bonus";
        }
        return text + " (final swing +/-" + Math.round(swing * 100) + "%).";
    }
}
