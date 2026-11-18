/*
 * Team Project: Void-Dungeon
 * Member: Member 3 (Monsters, Heroes & Skills)
 *
 * File: Boss.java
 *
 * Task Description:
 * The final enemy, the Void Dragon. Every few turns it uses Flame Breath (a heavy attack that the screen
 * warns about one turn before), and it gets stronger when it falls below half health.
 *
 * What to do:
 * Write act(target). It returns the damage dealt.
 * 1. If heavyNext is true: reset turnsSinceBreath to 0, set move = "breath" and the target takes
 *    Skill.FLAME_BREATH.damage(this).
 * 2. Otherwise: turnsSinceBreath++, then 20% chance (Dice.chance(20)) to guard(), else hit(target).
 * 3. Enrage: if not enraged yet and hp * 2 <= maxHp, set enraged = true and attack += 6.
 * 4. Warn for the next turn: turnsNeeded = 2 if enraged, else 3. heavyNext = turnsSinceBreath + 1 >= turnsNeeded.
 * 5. Return the damage.
 *
 * Expected Output:
 * === Testing Boss.java (Member 3) ===
 * [PASS] The boss is the Void Dragon with 520 HP
 * [PASS] Flame Breath is announced and then used
 * [PASS] Enraged below half HP: +6 attack and Flame Breath comes sooner
 * [SUCCESS] Boss self-test passed successfully!
 *
 * Hints:
 * - hit(...) and guard() come from Enemy (they are inherited).
 * - Turn 1: turnsSinceBreath = 1, 1 + 1 >= 3 is false. Turn 2: 2 + 1 >= 3 is true, so Flame Breath is next.
 */

package model;

public class Boss extends Enemy {
    private int turnsSinceBreath = 0;

    public Boss() {
        super("Void Dragon", 520, 40, 20, 400, 300);
        swing = 0.20;
    }

    // Normal turn, or Flame Breath when it is ready. Returns the damage dealt.
    public int act(Fighter target) {
        // TODO: Member 3
        return 0;
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Boss.java (Member 3) ===");

        Boss boss = new Boss();
        if (!boss.name.equals("Void Dragon") || boss.maxHp != 520 || boss.xp != 400 || boss.goldReward != 300) {
            throw new AssertionError("The boss should be the Void Dragon with 520 HP, 400 XP and 300 gold");
        }
        System.out.println("[PASS] The boss is the Void Dragon with 520 HP");

        Fighter target = new Fighter("Target", 1000000, 0, 0);
        if (boss.heavyNext) throw new AssertionError("no warning on turn 0");
        boss.act(target);
        if (boss.heavyNext) throw new AssertionError("no Flame Breath warning after the 1st turn");
        boss.act(target);
        if (!boss.heavyNext) throw new AssertionError("Flame Breath should be announced after the 2nd turn");
        int hpBefore = target.hp;
        int damage = boss.act(target);
        if (!boss.move.equals("breath") || damage <= 0 || target.hp != hpBefore - damage) {
            throw new AssertionError("the 3rd turn should be a Flame Breath (move \"breath\") that damages the target");
        }
        if (boss.heavyNext) throw new AssertionError("after a Flame Breath the warning must be off again");
        System.out.println("[PASS] Flame Breath is announced and then used");

        Boss angry = new Boss();
        angry.hp = angry.maxHp / 2;
        angry.act(target);
        if (!angry.enraged || angry.attack != 46) throw new AssertionError("at half HP the boss should enrage: enraged true, attack 46");
        if (!angry.heavyNext) throw new AssertionError("when enraged Flame Breath comes every 2nd turn (announced after the 1st)");
        System.out.println("[PASS] Enraged below half HP: +6 attack and Flame Breath comes sooner");

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
 * // The final enemy. Every few turns it uses Flame Breath, and it gets stronger below half health.
 * public class Boss extends Enemy {
 *     private int turnsSinceBreath = 0;
 *
 *     public Boss() {
 *         super("Void Dragon", 520, 40, 20, 400, 300);
 *         swing = 0.20;
 *     }
 *
 *     // Normal turn, or Flame Breath when it is ready. Returns the damage dealt.
 *     public int act(Fighter target) {
 *         int damage;
 *         if (heavyNext) {
 *             turnsSinceBreath = 0;
 *             move = "breath";
 *             damage = target.takeDamage(Skill.FLAME_BREATH.damage(this));
 *         } else {
 *             turnsSinceBreath++;
 *             if (Dice.chance(20)) {
 *                 damage = guard();
 *             } else {
 *                 damage = hit(target);
 *             }
 *         }
 *
 *         if (!enraged && hp * 2 <= maxHp) {
 *             enraged = true;
 *             attack += 6;
 *         }
 *
 *         // Warn the player: Flame Breath comes every 3rd turn, every 2nd turn when enraged.
 *         int turnsNeeded = enraged ? 2 : 3;
 *         heavyNext = turnsSinceBreath + 1 >= turnsNeeded;
 *         return damage;
 *     }
 * }
 */
