package ui;

import model.*;
import java.util.ArrayList;
import java.util.List;
import static ui.Term.*;

// Inventory management outside combat. Gear changes are free only between fights.
public class InventoryScreen {
    private static final int PAGE_SIZE = 8;

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
                    String type = it.type == Item.WEAPON ? "WEAPON" : it.type == Item.ARMOR ? "ARMOR " : "POTION";
                    String state = p.isEquipped(it) ? paint(GREEN, " [EQUIPPED]") : "";
                    if (it.type != Item.POTION && p.isUpgrade(it)) state += paint(CYAN, " [UPGRADE]");
                    String itemName = it.name;
                    if (it.type == Item.POTION && it.quantity > 1) itemName += " (x" + it.quantity + ")";
                    String line = String.format("  %d  %-6s %-24s %-25s", i - start + 1, type, itemName, it.stats());
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
            if (selected.type == Item.POTION) {
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

    // Returns the selected potion's inventory index, or -1 when the player cancels.
    // This selector is used during combat; selecting a potion does not consume it by itself.
    public static int choosePotion(Game game) {
        Player p = game.player;
        List<Integer> potionIndexes = new ArrayList<Integer>();
        for (int i = 0; i < p.inventory.size(); i++) {
            if (p.inventory.get(i).type == Item.POTION) potionIndexes.add(i);
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
                String name = potion.name + (potion.quantity > 1 ? " (x" + potion.quantity + ")" : "");
                String line = String.format("  %d  %-24s %s", i - start + 1, name, potion.stats());
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
}
