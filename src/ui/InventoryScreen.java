package ui;

import model.*;
import java.util.ArrayList;
import java.util.List;
import static ui.Term.*;

// The inventory, the potion picker and the shop screens.
public class InventoryScreen {
    private static final int PAGE_SIZE = 8;

    // The inventory screen: browse items, equip gear and drink potions.
    public static void run(Game game) {
        Player p = game.player;
        int page = 0;
        String message = "";

        while (true) {
            int pages = Math.max(1, (p.inventory.size() + PAGE_SIZE - 1) / PAGE_SIZE);
            page = Math.min(page, pages - 1);
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, p.inventory.size());

            clear();
            println();
            println("  " + paint(YELLOW, "INVENTORY") + "     ATK " + p.getAttack()
                    + "   DEF " + p.getDefense() + "   Crit " + (int) p.critChance() + "%");
            println("  Equipped weapon: " + p.weapon.name + dim("  " + p.weapon.stats()));
            println("  Equipped armor:  " + p.armor.name + dim("  " + p.armor.stats()));
            println();
            if (p.inventory.isEmpty()) {
                println("  Your inventory is empty.");
            } else {
                for (int i = start; i < end; i++) {
                    Item it = p.inventory.get(i);
                    String type = it.typeName();
                    String state = (it == p.weapon || it == p.armor) ? paint(GREEN, " [EQUIPPED]") : "";
                    if (it.isUpgradeFor(p)) state += paint(CYAN, " [UPGRADE]");
                    String line = String.format("  %d  %-6s %-24s %-25s", i - start + 1, type, it.name, it.stats());
                    println(line + state);
                }
            }
            println();
            println("  " + message);
            println(dim("  1-8: select item   n: next page   b: previous page   q: close"));
            println(dim("  Select a weapon/armor to equip, or a potion to use."));

            char key = key();
            if (key == 'q' || key == ESC || key == ENTER) return;
            if (key == 'n' && page + 1 < pages) {
                page++;
                message = "";
                continue;
            }
            if (key == 'b' && page > 0) {
                page--;
                message = "";
                continue;
            }

            int slot = key - '1';
            if (slot < 0 || slot >= end - start) continue;
            int inventoryIndex = start + slot;
            Item selected = p.inventory.get(inventoryIndex);
            if (selected instanceof Potion) {
                int hpBefore = p.hp;
                int manaBefore = p.mana;
                if (p.usePotion(inventoryIndex)) {
                    int hpRestored = p.hp - hpBefore;
                    int manaRestored = p.mana - manaBefore;
                    String restored = (hpRestored > 0 ? "+" + hpRestored + " HP" : "")
                            + (hpRestored > 0 && manaRestored > 0 ? ", " : "")
                            + (manaRestored > 0 ? "+" + manaRestored + " MP" : "");
                    message = paint(GREEN, "Used " + selected.name + ": " + restored + ".");
                } else {
                    message = paint(RED, "That potion cannot restore HP or MP right now.");
                }
            } else if (p.equipItem(inventoryIndex)) {
                message = paint(GREEN, "Equipped " + selected.name + ".");
            }
        }
    }

    // Lets the player pick a potion (used in fights). Returns its inventory index, or -1 if cancelled.
    public static int choosePotion(Game game) {
        Player p = game.player;
        List<Integer> potionIndexes = new ArrayList<Integer>();
        for (int i = 0; i < p.inventory.size(); i++) {
            if (p.inventory.get(i) instanceof Potion) potionIndexes.add(i);
        }
        if (potionIndexes.isEmpty()) return -1;

        int page = 0;
        while (true) {
            int pages = (potionIndexes.size() + PAGE_SIZE - 1) / PAGE_SIZE;
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, potionIndexes.size());

            clear();
            println();
            println("  " + paint(GREEN, "CHOOSE A POTION FROM INVENTORY") + "     HP " + p.hp + "/" + p.maxHp
                    + "   MP " + p.mana + "/" + p.maxMana);
            println();
            for (int i = start; i < end; i++) {
                Item potion = p.inventory.get(potionIndexes.get(i));
                String line = String.format("  %d  %-24s %s", i - start + 1, potion.name, potion.stats());
                boolean usable = p.canUsePotion(potionIndexes.get(i));
                println(usable ? line : dim(line + "  (no effect right now)"));
            }
            println();
            println(dim("  1-8: choose potion   n: next page   b: previous page   q: cancel"));

            char key = key();
            if (key == 'q' || key == ESC || key == ENTER) return -1;
            if (key == 'n' && page + 1 < pages) {
                page++;
                continue;
            }
            if (key == 'b' && page > 0) {
                page--;
                continue;
            }
            int slot = key - '1';
            if (slot >= 0 && slot < end - start) return potionIndexes.get(start + slot);
        }
    }

    // The Sacred Exchange shop screen: buy items with gold.
    public static void shop(Game game) {
        drain();
        Player p = game.player;
        String message = "";
        while (true) {
            Area shop = game.currentArea();
            clear();
            println();
            println("  " + paint(YELLOW, "SACRED EXCHANGE") + "        Gold " + paint(YELLOW, "" + p.gold));
            println();
            for (int i = 0; i < shop.shopStock.size(); i++) {
                Item it = shop.shopStock.get(i);
                String note = "";
                if (!(it instanceof Potion)) {
                    note = it.isUpgradeFor(p) ? "upgrade" : "not an upgrade";
                }
                String line = String.format("   %d  %-16s %-18s %4d gold   %s", i + 1, it.name, it.stats(), it.price, note);
                println(p.gold >= it.price ? line : dim(line));
            }
            println();
            println("  " + message);
            println(dim("  press a number to buy, Enter to leave; equip items later from i"));
            char c = key();
            if (c == ENTER || c == ESC || c == 'q') break;
            message = "";
            int i = c - '1';
            if (i >= 0 && i < shop.shopStock.size()) {
                Item it = shop.shopStock.get(i);
                if (p.gold < it.price) {
                    message = paint(RED, "Not enough gold.");
                } else {
                    game.buy(i);
                    message = paint(GREEN, "Bought " + it.name + ". Added to inventory; equip/use it with i.");
                }
            }
        }
        game.inShop = false;
    }
}
