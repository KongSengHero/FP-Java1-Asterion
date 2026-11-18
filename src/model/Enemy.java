package model;

import java.util.Random;

// A normal enemy. The four kinds go from easy to hard: Skeleton, Cyclops, Blaze, Void Guardian.
public class Enemy extends Fighter {
    public static final int SKELETON = 0;
    public static final int CYCLOPS = 1;
    public static final int BLAZE = 2;
    public static final int VOID_GUARDIAN = 3;

    public int kind;
    public int xp, goldReward;
    public String move = "";        // what it did this turn: "hit", "guard", "heal" or "breath"
    public boolean enraged;         // only the boss uses this
    public boolean heavyNext;       // true = a heavy attack comes next turn (only the boss uses this)

    // A fully custom enemy (used by the tests).
    public Enemy(String name, int hp, int attack, int defense, int xp, int goldReward) {
        super(name, hp, attack, defense);
        this.xp = xp;
        this.goldReward = goldReward;
        this.critMult = 1.5;
    }

    // An enemy of the given kind; the farther the area from the start (distance), the stronger it is.
    public Enemy(int kind, int distance, Random rnd) {
        this(nameOf(kind), 40 + 20 * distance + rnd.nextInt(6), 10 + 4 * distance, 3 + 2 * distance, 20 + 15 * distance, 5 + 13 * distance + rnd.nextInt(5));
        this.kind = kind;
        if (kind == SKELETON) swing = 0.15;
        else if (kind == CYCLOPS) swing = 0.20;
        else if (kind == BLAZE) swing = 0.25;
        else {
            swing = 0.15;
            baseCrit = 25;
        }
    }

    private static String nameOf(int kind) {
        if (kind == SKELETON) return "Skeleton";
        if (kind == CYCLOPS) return "Cyclops";
        if (kind == BLAZE) return "Blaze";
        return "Void Guardian";
    }

    // Picks a kind for an area: easy enemies near the start, harder ones farther away (sometimes one step easier).
    public static Enemy create(int distance, Random rnd) {
        int kind = Math.min(3, distance / 2);
        if (kind > 0 && rnd.nextInt(3) == 0) kind = kind - 1;
        return new Enemy(kind, distance, rnd);
    }

    // The enemy takes its turn against the target. Returns the damage dealt.
    public int act(Fighter target) {
        if (kind == SKELETON && hp < maxHp / 2 && Dice.chance(20)) return guard();
        if (kind == CYCLOPS && hp < maxHp * 0.4 && Dice.chance(40)) return healSelf();
        if (kind == VOID_GUARDIAN && hp < maxHp * 0.4 && Dice.chance(25)) return guard();
        return hit(target);
    }

    protected int hit(Fighter target) {
        move = "hit";
        return target.takeDamage(attackDamage());
    }

    // Skips the attack and takes less damage from the next hit.
    protected int guard() {
        move = "guard";
        guarding = true;
        return 0;
    }

    // Heals a fifth of its health and guards.
    private int healSelf() {
        move = "heal";
        heal(maxHp / 5);
        guarding = true;
        return 0;
    }
}
