package ui;

import model.*;
import static ui.Term.*;

// The main screen while walking around the dungeon.
public class RoomScreen {
    private final Game game;
    private String notice = "";

    public RoomScreen(Game game) {
        this.game = game;
    }

    // The main loop: draw the room, read a key, walk, and open fights, the shop or the inventory.
    public boolean play() {
        while (!game.gameOver && !game.won) {
            draw();
            char key = key();
            int[] before = snap();
            if (key == 'q') {
                if (confirmQuit()) return false;
                continue;
            } else if (key == 'i') {
                InventoryScreen.run(game);
                notice = "";
                continue;
            } else if (key == '?') {
                help();
                continue;
            } else if ("wdsa".indexOf(key) >= 0) {
                game.move("wdsa".indexOf(key));
            } else {
                continue;
            }
            if (game.fighting != null) {
                CombatScreen.run(game);
                notice = gained(before, false);
            } else if (game.inShop) {
                InventoryScreen.shop(game);
                notice = "";
            } else {
                notice = gained(before, true);
            }
        }
        return true;
    }

    // Draws the status bar, the current area, the small map and the latest notice.
    private void draw() {
        clear();
        Player p = game.player;
        println();
        println("  " + paint(heroColor(p.hero), p.name + " the " + p.hero.name) + "   Lv " + p.level + dim("   XP " + p.xp + "/" + p.xpNext));
        println("  HP " + bar(p.hp, p.maxHp, 12, hpColor(p.hp, p.maxHp)) + String.format(" %3d/%d", p.hp, p.maxHp)
                + "   MP " + bar(p.mana, p.maxMana, 12, BLUE) + String.format(" %3d/%d", p.mana, p.maxMana)
                + "   Gold " + paint(YELLOW, "" + p.gold) + "   Potions " + paint(GREEN, "" + p.potionCount()));
        println();
        String[] map = minimap();
        for (int r = 0; r < Area.ROWS; r++) {
            println("  " + row(r) + (r < map.length ? "     " + map[r] : ""));
        }
        println();
        println("  " + notice);
        println("  " + dim("wasd or arrows: move   i: inventory   ?: help   q: quit"));
    }


    // Draws one row of the current area (walls, doors, enemies, chests, the player).
    private String row(int r) {
        Area a = game.currentArea();
        String s = "";
        for (int c = 0; c < Area.COLS; c++) {
            int obj = a.objects[r][c];
            if (r == game.playerRow && c == game.playerCol) s += paint("1;" + heroColor(game.player.hero), "@ ");
            else if (obj == Area.ENEMY) s += paint(RED, "E ");
            else if (obj == Area.CHEST) s += paint(YELLOW, "C ");
            else if (obj == Area.OBJ_BOSS) s += paint(RED, "BB");
            else if (obj == Area.OBJ_SHOP) s += paint(YELLOW, "HH");
            else if (obj == Area.OBJ_REALM) s += paint(a.realmUsed ? GRAY : CYAN, "LR");
            else if (a.tiles[r][c] == Area.WALL) s += dim(ascii ? "##" : "▒▒");
            else if (a.tiles[r][c] == Area.DOOR) s += paint(BROWN, door());
            else s += dim(ascii ? ". " : "· ");
        }
        return s;
    }

    // The door symbol.
    private static String door() {
        return ascii ? "::" : "░░";
    }


    // Builds the small map of all areas shown on the right.
    private String[] minimap() {
        int n = WorldMap.SIZE;
        String[] lines = new String[2 * n - 1];
        for (int r = 0; r < n; r++) {
            String cells = "", links = "";
            for (int c = 0; c < n; c++) {
                Area a = game.areas[r][c];
                cells += (a == null ? "   " : cell(a)) + (a != null && game.world.isArea(r, c + 1) ? dim(ascii ? "-" : "─") : " ");
                links += (a != null && game.world.isArea(r + 1, c) ? dim(ascii ? " | " : " │ ") : "   ") + " ";
            }
            lines[2 * r] = cells;
            if (r < n - 1) lines[2 * r + 1] = links;
        }
        return lines;
    }

    // Draws one area of the small map: @ = you, ? = not visited, otherwise its letter or enemy count.
    private String cell(Area a) {
        String inner;
        if (a.row == game.areaRow && a.col == game.areaCol) inner = paint(WHITE, "@");
        else if (!game.visited[a.row][a.col]) inner = dim("?");
        else {
            char sym = Game.symbol(a);
            String[] color = {GREEN, WHITE, YELLOW, CYAN, RED};
            inner = sym == '0' ? dim(ascii ? "." : "·") : paint(color[a.type], "" + sym);
        }
        return dim("[") + inner + dim("]");
    }


    // Takes a snapshot of the player numbers so we can show what changed after an action.
    private int[] snap() {
        Player p = game.player;
        return new int[]{p.gold, p.potionCount(), p.hp, p.mana, p.maxHp, p.maxMana, p.getAttack(), p.getDefense(), p.level};
    }

    // Compares the numbers with the snapshot and describes the gains, for example "+10 gold".
    private String gained(int[] before, boolean showHp) {
        int[] now = snap();
        String[] label = {"gold", "potion", "HP", "MP", "max HP", "max MP", "ATK", "DEF", "level"};
        String[] color = {YELLOW, GREEN, GREEN, BLUE, GREEN, BLUE, ORANGE, CYAN, YELLOW};
        String text = "";
        for (int i = 0; i < label.length; i++) {
            if (now[i] > before[i] && (showHp || i == 0 || i > 3)) {
                text += paint(color[i], "+" + (now[i] - before[i]) + " " + label[i]) + "   ";
            }
        }
        if (!game.lastMessage.isEmpty()) {
            text += paint(CYAN, game.lastMessage);
            game.lastMessage = "";
        }
        return text;
    }


    // Asks "Quit to the menu?" and returns true if the player pressed y.
    private boolean confirmQuit() {
        clear();
        println();
        println("  Quit to the menu?  " + paint(YELLOW, "y") + " / " + paint(YELLOW, "n"));
        drain();
        return key() == 'y';
    }

    // Shows the how-to-play screen.
    private void help() {
        clear();
        println();
        println("  " + paint(YELLOW, "How to play"));
        println();
        println("  Press w a s d (or the arrow keys) to walk. Walk into things to use them.");
        println("  Press i to open your inventory, equip gear, or use a potion.");
        println("  During combat, i opens the potion picker; gear cannot be changed mid-fight.");
        println("  Defend doubles your DEF against the next hit and gives your class bonus:");
        println("    Warrior +12 HP, Mage +15 MP, Rogue +5 HP and +5 MP.");
        println("  Doors are the " + paint(BROWN, door()) + " gaps in the wall.");
        println();
        println("  " + paint(RED, "M") + " enemy (a fight starts, no running away)    " + paint(YELLOW, "C") + " chest");
        println("  " + paint(YELLOW, "SS") + " Sacred Exchange    " + paint(CYAN, "RR") + " Lost Realm (+30 max HP, +10 max MP, once)    " + paint(RED, "DD") + " Void Dragon");
        println();
        println("  Your skills:");
        for (Skill s : game.player.hero.skills) println("    " + s.name + " (" + s.mana + " MP): " + dim(s.info()));
        println();
        pause("press any key to go back");
    }
}
