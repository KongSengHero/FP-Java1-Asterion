package ui;

import model.*;
import static ui.Term.*;

// The screens around the game: title, hero selection, ending.
public class Menu {
    // Returns true to play, false to quit.
    public static boolean title() {
        drain();
        clear();
        println();
        println("  " + paint(YELLOW, rule(44)));
        println("  " + paint(YELLOW, "   L A B Y R I N T H   O F   A S T E R I O N"));
        println("  " + paint(YELLOW, rule(44)));
        println();
        println("  Escape the labyrinth. Defeat Asterion.");
        println();
        println("   1  Play");
        println("   2  Quit");
        println();
        println(dim("  press 1 or 2"));
        while (true) {
            char c = key();
            if (c == '1' || c == ENTER) return true;
            if (c == '2' || c == 'q') return false;
        }
    }

    // Returns the chosen hero, or null for "back".
    public static Hero chooseHero() {
        drain();
        clear();
        println();
        println("  " + paint(YELLOW, "Choose your hero"));
        println();
        Hero[] heroes = Hero.values();
        for (int i = 0; i < heroes.length; i++) {
            Hero h = heroes[i];
            println("   " + (i + 1) + "  " + paint(heroColor(h), String.format("%-10s", h.label))
                    + "HP " + h.hp + "   MP " + h.mana + "   ATK " + h.atk + "   DEF " + h.def);
            println("      " + dim(h.info));
            println("      Guard bonus: " + h.defendBonus() + " when Defend is used.");
            Skill first = h.skills[0];
            Skill second = h.skills[1];
            println("      " + first.label + " (" + first.mana + " MP): " + first.info);
            println("      " + second.label + " (" + second.mana + " MP): " + second.info);
            println();
        }
        println(dim("   Skill attack rolls include weapon bonuses and can critically hit."));
        println();
        println("   b  Back");
        println();
        println(dim("  press 1, 2 or 3"));
        while (true) {
            char c = key();
            if (c == 'b' || c == 'q' || c == ESC) return null;
            int i = c - '1';
            if (i >= 0 && i < heroes.length) return heroes[i];
        }
    }

    // Shown after the game ends (win or death).
    public static void ending(Game g) {
        clear();
        Player p = g.player;
        println();
        if (g.won) println("  " + paint(YELLOW, "VICTORY!") + "  Asterion falls. You escaped the labyrinth.");
        else println("  " + paint(RED, "YOU DIED") + "  The labyrinth claims another hero.");
        println();
        println("  " + p.hero.label + ", level " + p.level + "   ·   " + g.kills + " enemies slain   ·   " + p.gold + " gold");
        println();
        pause("press any key to return to the menu");
    }
}
