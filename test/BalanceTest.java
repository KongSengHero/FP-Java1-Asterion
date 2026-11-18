import model.*;
import java.util.*;

public class BalanceTest {
    static int bestPotionIndex(Player p) {
        int bestIndex = -1;
        int bestValue = 0;
        for (int i = 0; i < p.inventory.size(); i++) {
            Item item = p.inventory.get(i);
            if (!(item instanceof Potion)) continue;
            Potion potion = (Potion) item;
            int hpValue = Math.min(potion.healHp, p.maxHp - p.hp);
            int mpValue = Math.min(potion.mana, p.maxMana - p.mana);
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
            else if (g.fighting.heavyNext) g.combatDefend();
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

    static void shop(Game g) {
        Area shop = find(g, Area.SHOP);
        goTo(g, shop);
        Player p = g.player;
        for (int i = shop.shopStock.size() - 1; i >= 0; i--) {
            Item it = shop.shopStock.get(i);
            if (!(it instanceof Potion) && it.isUpgradeFor(p) && p.gold >= it.price) {
                g.buy(i);
                p.equipItem(p.inventory.size() - 1);
            }
        }
        for (int i = 0; i < shop.shopStock.size(); i++) {
            Item it = shop.shopStock.get(i);
            if (it instanceof Potion && p.potionCount() < 12 && p.gold >= it.price) g.buy(i);
        }
        for (int i = 0; i < shop.shopStock.size(); i++) {
            Item it = shop.shopStock.get(i);
            for (int k = 0; it instanceof Potion && it.name.equals("Mixed Potion")
                    && k < 8 && p.gold >= it.price && p.potionCount() < 12; k++) g.buy(i);
        }
    }

    static int play(int seed, Hero hero, char strategy) {
        Game g = new Game(new Random(seed), hero);
        boolean chests = strategy == 'B' || strategy == 'C', fights = strategy == 'C' || strategy == 'D';
        if (strategy != 'A') {
            List<Area> order = new ArrayList<Area>();
            for (Area[] row : g.areas) for (Area a : row) if (a != null) order.add(a);
            order.sort((x, y) -> g.world.dist[x.row][x.col] - g.world.dist[y.row][y.col]);
            for (Area area : order) {
                if (area.type == Area.BOSS || area.type == Area.SHOP) continue;
                goTo(g, area);
                if (area.type == Area.REALM) {
                    if (g.player.hp < g.player.maxHp / 2) { area.realmUsed = true; g.player.enterLostRealm(); }
                    continue;
                }
                for (int r = 0; r < Area.ROWS; r++) for (int c = 0; c < Area.COLS; c++) {
                    int obj = area.objects[r][c];
                    if (obj == Area.CHEST && chests) {
                        g.player.gold += area.chests[r][c].gold;
                        if (area.chests[r][c].item != null) {
                            g.player.inventory.add(area.chests[r][c].item);
                            Item loot = area.chests[r][c].item;
                            if (loot.isUpgradeFor(g.player)) {
                                g.player.equipItem(g.player.inventory.size() - 1);
                            }
                        }
                        area.objects[r][c] = Area.NONE;
                    }
                    if (obj == Area.ENEMY && fights) {
                        g.playerRow = r;
                        g.playerCol = c - 1;
                        g.move(1);
                        fight(g);
                        if (g.gameOver) return -1;
                    }
                }
            }
            shop(g);
            Area realm = find(g, Area.REALM);
            if (!realm.realmUsed) g.player.enterLostRealm();
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
        for (Hero hero : Hero.ALL) {
            String line = String.format("%-10s", hero.name);
            for (char strategy : "ABCD".toCharArray()) {
                int wins = 0;
                for (int seed = 0; seed < games; seed++) if (play(seed, hero, strategy) == 1) wins++;
                line += String.format(" %11d%%", 100 * wins / games);
            }
            System.out.println(line);
        }
    }
}
