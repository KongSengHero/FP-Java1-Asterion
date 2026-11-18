package ui;

import model.*;
import static ui.Term.*;

// The title screen, the hero selection screen and the ending screen.
public class Menu {
    // Shows the title screen. Returns true to play, false to quit.
    public static boolean title() {
        drain();
        clear();
        println();
        println("  " + paint(YELLOW, rule(30)));
        println("  " + paint(YELLOW, "   V O I D - D U N G E O N"));
        println("  " + paint(YELLOW, rule(30)));
        println();
        println("  Escape the dungeon. Defeat the Void Dragon.");
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

    // Shows the hero list. Returns the chosen hero, or null if the player goes back.
    public static Hero chooseHero() {
        drain();
        clear();
        println();
        println("  " + paint(YELLOW, "Choose your hero"));
        println();
        Hero[] heroes = Hero.ALL;
        for (int i = 0; i < heroes.length; i++) {
            Hero h = heroes[i];
            println("   " + (i + 1) + "  " + paint(heroColor(h), String.format("%-10s", h.name))
                    + "HP " + h.hp + "   MP " + h.mana + "   ATK " + h.attack + "   DEF " + h.defense);
            println("      " + dim(h.description));
            println("      Guard bonus: " + h.defendBonus() + " when Defend is used.");
            Skill first = h.skills[0];
            Skill second = h.skills[1];
            println("      " + first.name + " (" + first.mana + " MP): " + first.info());
            println("      " + second.name + " (" + second.mana + " MP): " + second.info());
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

    // Shows the victory or defeat screen with the final numbers.
    public static void ending(Game g) {
        clear();
        Player p = g.player;
        println();
        if (g.won) println("  " + paint(YELLOW, "VICTORY!") + "  The Void Dragon falls. You escaped the dungeon.");
        else println("  " + paint(RED, "YOU DIED") + "  The dungeon claims another hero.");
        println();
        println("  " + p.hero.name + ", level " + p.level + "   ·   " + g.kills + " enemies slain   ·   " + p.gold + " gold");
        println();
        pause("press any key to return to the menu");
    }
}
