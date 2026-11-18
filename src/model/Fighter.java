package model;

// Anything that can fight (the player and every enemy): health, attack, defense and the damage rules.
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
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    // The player overrides these three to add weapon and armor bonuses.
    public int getAttack()  { return attack; }
    public int getDefense() { return defense; }
    public double critChance() { return baseCrit; }

    // Rolls the damage of a normal attack, including a possible critical hit.
    public int attackDamage() {
        int damage = Dice.vary(getAttack(), swing);
        crit = Dice.chance(critChance());
        if (crit) {
            damage = (int) Math.round(damage * critMult);
        }
        return damage;
    }

    // Lowers hp by the incoming damage minus half the defense (guarding doubles the defense). Returns the damage taken.
    public int takeDamage(int incoming) {
        int defenseUsed = getDefense();
        if (guarding) {
            defenseUsed = defenseUsed * 2;
        }
        int damage = Math.max(1, incoming - defenseUsed / 2);
        guarding = false;
        hp = Math.max(0, hp - damage);
        return damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    // Restores hp (never above maxHp). Returns how much was really healed.
    public int heal(int amount) {
        int before = hp;
        hp = Math.min(maxHp, hp + amount);
        return hp - before;
    }
}
