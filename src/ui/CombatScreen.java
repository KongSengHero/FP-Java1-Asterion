package ui; 

import model.*; 
import static ui.Term.*; 

// The fight: stays until one side is dead. The player chooses one action per turn.
public class CombatScreen { 
    private static final String SEP = "   "; 
    
    public static void run(Game game) { 
        drain(); 
        Enemy enemy = game.fighting; 
        Player p = game.player; 
        String last = ""; 
        while (game.fighting != null) { 
            draw(game, enemy, last, true); 
            char c = key(); 
            if (c == 'i' || c == '5') { 
                if (p.potionCount() == 0) { 
                    last = paint(RED, "No potions in your inventory."); 
                    continue; 
                }
                int potionIndex = InventoryScreen.choosePotion(game); 
                if (potionIndex < 0) continue; 
                if (!p.canUsePotion(potionIndex)) { 
                    last = paint(RED, "That potion cannot restore HP or MP right now."); 
                    continue; 
                }
                game.combatPotion(potionIndex); 
                last = news(game, enemy); 
                continue; 
            }
            
            int action = "1234".indexOf(c); // 0 attack, 1-2 skills, 3 defend 
            if (action < 0) continue; 
            String problem = problem(p, action); 
            if (problem != null) { 
                last = paint(RED, problem); 
                continue; 
            }
            if (action == 0) game.combatAttack(); 
            else if (action <= 2) game.combatSkill(action - 1); 
            else game.combatDefend(); 
            last = news(game, enemy); 
        }
        draw(game, enemy, last, false); 
        pause("press any key to continue"); 
    }
    
    // Why the action can not be used right now, or null if it can. 
    private static String problem(Player p, int action) { 
        if ((action == 1 || action == 2) && p.mana < p.hero.skills[action - 1].mana) return "Not enough mana."; 
        return null; 
    }
    
    // One line about the last round, colored. 
    private static String news(Game g, Enemy e) { 
        String s = ""; 
        if (g.dealt > 0)        s += paint(g.crit ? ORANGE : WHITE, (g.crit ? "CRIT! " : "") + (g.used.equals("") ? "You hit " : g.used + " ") + g.dealt) + SEP; 
        if (g.healed > 0)       s += paint(GREEN, "+" + g.healed + " HP") + SEP; 
        if (g.manaGained > 0)   s += paint(BLUE, "+" + g.manaGained + " MP") + SEP; 
        if (g.reward > 0)       s += paint(YELLOW, "+" + g.reward + " gold") + SEP + paint(MAGENTA, "+" + g.xpGain + " XP"); 
        if (g.taken > 0)        s += paint(RED, (g.blocked ? "Guarded! " : "") + e.move.equals("charge") ? "Bull Charge " : e.name + (e.crit ? " CRIT " : " hits ") + g.taken) + SEP; 
        else if (e.move.equals("guard") || e.move.equals("heal")) s += dim(e.name + (e.move.equals("guard") ? " guards" : " heals")) + SEP; 
        return s; 
    }
    
    private static void draw(Game g, Enemy e, String last, boolean active) { 
        clear(); 
        Player p = g.player; 
        String tag = e.guarding ? "  GUARDING" : (e instanceof Boss && ((Boss) e).enraged) ? "  ENRAGED" : ""; 
        println(); 
        println("  " + paint(RED, e.name) + dim(tag)); 
        println("  HP " + bar(e.hp, e.maxHp, 24, hpColor(e.hp, e.maxHp)) + String.format(" %d / %d", e.hp, e.maxHp)); 
        println(active && e.heavyNext() ? paint(RED, "  !!  HEAVY ATTACK INCOMING  !!") : ""); 
        println(); 
        println("  " + paint(heroColor(p.hero), p.name) + "  Lv " + p.level + dim(p.guarding ? "  GUARDING" : "")); 
        println("  HP " + bar(p.hp, p.maxHp, 24, hpColor(p.hp, p.maxHp)) + String.format(" %d / %d", p.hp, p.maxHp)); 
        println("  MP " + bar(p.mana, p.maxMana, 24, BLUE) + String.format(" %d / %d", p.mana, p.maxMana)); 
        println(); 
        println("  " + last); 
        println(); 
        if (!active) { 
            println("  " + (p.isDead() ? paint(RED, "You died.") : paint(GREEN, "Victory!"))); 
            println(); 
            return; 
        }
        String[] name = {"Attack", p.hero.skills[0].label, p.hero.skills[1].label, "Defend", "Choose potion"}; 
        String[] cost = {"", p.hero.skills[0].mana + " MP", p.hero.skills[1].mana + " MP", "DEF x2, " + p.hero.defendBonus(), "x" + p.potionCount()}; 
        for (int i = 0; i < 4; i++) { 
            String line = String.format("   %d  %-14s %s", i + 1, name[i], cost[i]); 
            println(problem(p, i) == null ? line : dim(line)); 
        }
        println(String.format("   i/5  %-12s %s", name[4], cost[4])); 
        println(); 
    }
}
