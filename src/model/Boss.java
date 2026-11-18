package model;

// The final enemy. Every few turns it uses Flame Breath, and it gets stronger below half health.
public class Boss extends Enemy {
    private int turnsSinceBreath = 0;

    public Boss() {
        super("Void Dragon", 520, 40, 20, 400, 300);
        swing = 0.20;
    }

    // Normal turn, or Flame Breath when it is ready. Returns the damage dealt.
    public int act(Fighter target) {
        int damage;
        if (heavyNext) {
            turnsSinceBreath = 0;
            move = "breath";
            damage = target.takeDamage(Skill.FLAME_BREATH.damage(this));
        } else {
            turnsSinceBreath++;
            if (Dice.chance(20)) {
                damage = guard();
            } else {
                damage = hit(target);
            }
        }

        if (!enraged && hp * 2 <= maxHp) {
            enraged = true;
            attack += 6;
        }

        // Warn the player: Flame Breath comes every 3rd turn, every 2nd turn when enraged.
        int turnsNeeded = enraged ? 2 : 3;
        heavyNext = turnsSinceBreath + 1 >= turnsNeeded;
        return damage;
    }
}
