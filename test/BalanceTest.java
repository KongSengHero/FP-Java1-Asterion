import model.*;
import java.util.*;

// Optional tool: lets simple bots play thousands of games (no screen, no input) to check the balance.
//   ./test.sh            (1000 games per hero and strategy, takes a few seconds)
// Strategies:  A straight to the boss   B loot every chest and shop but never fight
//              C do everything          D only fight (no chests)
// Goal: A about 0%, B low, C high. If you change numbers in model/, run this to see what happened.
public class BalanceTest {
    // How the bot fights: heal when low, Defend when the charge is coming, else the strongest skill it can pay for.
    static int bestPotionIndex(Player p) {
        int bestIndex = -1;
        int bestValue = 0;
        for (int i = 0; i < p.inventory.size(); i++) {
            Item item = p.inventory.get(i);
            if (item.type != Item.POTION) continue;
            int hpValue = Math.min(item.bonus, p.maxHp - p.hp);
            int mpValue = Math.min(item.extra, p.maxMana - p.mana);
            int value = hpValue + mpValue;
            if (value > bestValue) {
                bestValue = value;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    static void fight(Game g) {
        Player p = g.player;
        Skill[] sk = p.hero.skills;
        int big = sk[0].mana >= sk[1].mana ? 0 : 1;
        for (int guard = 0; g.fighting != null && guard < 800; guard++) {
            int potionIndex = bestPotionIndex(p);
            if (p.hp < p.maxHp * 0.35 && potionIndex >= 0) g.combatPotion(potionIndex);
            else if (g.fighting.heavyNext()) g.combatDefend();
            else if (p.mana >= sk[big].mana) g.combatSkill(big);
            else if (p.mana >= sk[1 - big].mana) g.combatSkill(1 - big);
            else g.combatAttack();
        }
    }

    static Area find(Game g, int type) {
        for (Area[] row : g.areas) for (Area a : row) if (a != null && a.type == type) return a;
        return null;
    }

    static void goTo(Game g, Area a) {
        g.areaRow = a.row;
        g.areaCol = a.col;
    }

    // Buys every upgrade it can afford, then potions.
    static void shop(Game g) {
        Area shop = find(g, Area.SHOP);
        goTo(g, shop);
        Player p = g.player;
        for (int i = shop.shopStock.size() - 1; i >= 0; i--) {
            Item it = shop.shopStock.get(i);
            if (it.type != Item.POTION && p.isUpgrade(it) && p.gold >= it.price) {
                g.buy(i);
                p.equipItem(p.inventory.size() - 1);
            }
        }
        // Buy one of each potion type first, then top up with standard potions.
        for (int i = 0; i < shop.shopStock.size(); i++) {
            Item it = shop.shopStock.get(i);
            if (it.type == Item.POTION && p.potionCount() < 12 && p.gold >= it.price) g.buy(i);
        }
        for (int i = 0; i < shop.shopStock.size(); i++) {
            Item it = shop.shopStock.get(i);
            for (int k = 0; it.type == Item.POTION && it.name.equals("Potion")
                    && k < 8 && p.gold >= it.price && p.potionCount() < 12; k++) g.buy(i);
        }
    }

    // Returns 1 = won, 0 = lost at the boss, -1 = died before the boss
    static int play(int seed, Hero hero, char strategy) {
        Game g = new Game(seed % 2 == 0, new Random(seed), hero);
        boolean chests = strategy == 'B' || strategy == 'C', fights = strategy == 'C' || strategy == 'D';
        if (strategy != 'A') {
            List<Area> order = new ArrayList<Area>();   // nearest areas first
            for (Area[] row : g.areas) for (Area a : row) if (a != null) order.add(a);
            order.sort((x, y) -> g.world.dist[x.row][x.col] - g.world.dist[y.row][y.col]);
            for (Area area : order) {
                if (area.type == Area.BOSS || area.type == Area.SHOP) continue;
                goTo(g, area);
                if (area.type == Area.STATUE) {
                    if (g.player.hp < g.player.maxHp / 2) { area.statueUsed = true; g.player.bless(); }
                    continue;
                }
                for (int r = 0; r < Area.ROWS; r++) for (int c = 0; c < Area.COLS; c++) {
                    int obj = area.objects[r][c];
                    if (obj == Area.CHEST && chests) {
                        g.player.gold += area.chests[r][c].gold;
                        if (area.chests[r][c].item != null) {
                            g.player.pickUp(area.chests[r][c].item);
                            Item loot = area.chests[r][c].item;
                            if (loot.type != Item.POTION && g.player.isUpgrade(loot)) {
                                g.player.equipItem(g.player.inventory.size() - 1);
                            }
                        }
                        area.objects[r][c] = Area.NONE;
                    }
                    if (obj == Area.ENEMY && fights) {
                        g.playerRow = r;
                        g.playerCol = c - 1;
                        g.move(1);   // bump into the enemy
                        fight(g);
                        if (g.gameOver) return -1;
                    }
                }
            }
            shop(g);
            Area statue = find(g, Area.STATUE);
            if (!statue.statueUsed) g.player.bless();
        }
        Area boss = find(g, Area.BOSS);
        goTo(g, boss);
        g.playerRow = Area.CENTER_ROW;
        g.playerCol = Area.CENTER_COL - 2;
        g.move(1);
        fight(g);
        return g.won ? 1 : 0;
    }

    public static void main(String[] args) {
        int games = args.length > 0 ? Integer.parseInt(args[0]) : 1000;
        System.out.printf("%-10s %12s %12s %12s %12s%n", "wins", "A boss only", "B no fights", "C everything", "D only fights");
        for (Hero hero : Hero.values()) {
            String line = String.format("%-10s", hero.label);
            for (char strategy : "ABCD".toCharArray()) {
                int wins = 0;
                for (int seed = 0; seed < games; seed++) if (play(seed, hero, strategy) == 1) wins++;
                line += String.format(" %11d%%", 100 * wins / games);
            }
            System.out.println(line);
        }
    }
}
