package ui; 

import model.*; 
import static ui.Term.*; 

// Hermes' shop: press the number of what you want to buy, Enter to leave. 
public class ShopScreen { 
    public static void run(Game game) { 
        drain(); 
        Player p = game.player; 
        String message = ""; 
        while (true) { 
            Area shop = game.currentArea(); 
            clear(); 
            println(); 
            println("  " + paint(YELLOW, "HERMES' SHOP") + "        Gold " + paint(YELLOW, "" + p.gold)); 
            println(); 
            for (int i = 0; i < shop.shopStock.size(); i++) { 
                Item it = shop.shopStock.get(i); 
                String note = ""; 
                if (it.type != Item.POTION) note = p.isUpgrade(it) ? "upgrade" : "not an upgrade"; 
                String line = String.format("   %d  %-16s %-18s %4d gold   %s", i + 1, it.name, it.stats(), it.price, note); 
                println(p.gold >= it.price ? line : dim(line));   // dim = too expensive 
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
                if (p.gold < it.price) message = paint(RED, "Not enough gold."); 
                else { 
                    game.buy(i); 
                    message = paint(GREEN, "Bought " + it.name + ". Added to inventory; equip/use it with i."); 
                }
            }
        }
        game.inShop = false; 
    }
}
