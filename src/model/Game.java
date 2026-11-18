package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// The whole game state and rules: the dungeon, walking, chests, the shop, the Lost Realm and combat.
public class Game {
    public WorldMap world = new WorldMap();
    public Area[][] areas = new Area[WorldMap.SIZE][WorldMap.SIZE];
    public Player player;

    public int areaRow, areaCol;      // which area the player is in
    public int playerRow, playerCol;  // where the player stands inside that area

    public boolean inShop = false;
    public boolean gameOver = false;
    public boolean won = false;
    public String lastMessage = "";   // one-time message the screen shows after an action

    public Enemy fighting = null;     // the enemy in the current fight, or null
    private int fightRow, fightCol;   // where that enemy stands
    public int kills = 0;
    public boolean[][] visited = new boolean[WorldMap.SIZE][WorldMap.SIZE];

    // Results of the last combat round, read by the combat screen.
    public int dealt, taken, healed, manaGained, reward, xpGain;
    public boolean crit, blocked;
    public String used = "";          // name of the skill used, or "" for a normal attack
    private int hpBefore, manaBefore;

    // Builds a new dungeon for the chosen hero.
    public Game(Random rnd, Hero hero) {
        player = new Player(hero);
        world.useFixedMap(rnd);

        List<Area> normalAreas = createAreas();
        placeSpecialAreas(normalAreas, rnd);
        fillAreas(normalAreas, rnd);
        placePlayerAtStart();
    }

    // Creates an Area for every cell of the map. Marks the start and boss areas and returns all the others.
    private List<Area> createAreas() {
        List<Area> others = new ArrayList<Area>();
        for (int r = 0; r < WorldMap.SIZE; r++) {
            for (int c = 0; c < WorldMap.SIZE; c++) {
                if (!world.isArea(r, c)) {
                    continue;
                }
                Area area = new Area(world, r, c);
                areas[r][c] = area;
                if (r == world.startRow && c == world.startCol) {
                    area.type = Area.START;
                } else if (r == world.bossRow && c == world.bossCol) {
                    area.type = Area.BOSS;
                    area.placeCenterObject(Area.OBJ_BOSS);
                } else {
                    others.add(area);
                }
            }
        }
        return others;
    }

    // Randomly turns two of the other areas into the shop (Sacred Exchange) and the Lost Realm.
    // The areas left over stay normal and are removed from the list.
    private void placeSpecialAreas(List<Area> others, Random rnd) {
        Collections.shuffle(others, rnd);
        Area shop = others.get(0);
        shop.type = Area.SHOP;
        shop.placeCenterObject(Area.OBJ_SHOP);
        Area realm = others.get(1);
        realm.type = Area.REALM;
        realm.placeCenterObject(Area.OBJ_REALM);
        others.remove(0);
        others.remove(0);
    }

    // Puts enemies and chests in the normal areas and creates their real content.
    private void fillAreas(List<Area> normalAreas, Random rnd) {
        boolean anyEmpty = false;
        for (Area area : normalAreas) {
            area.placeRandomObjects(rnd);
            if (area.countObjects() == 0) {
                anyEmpty = true;
            }
        }
        // Always keep at least one empty normal area so the player has somewhere safe.
        if (!anyEmpty) {
            normalAreas.get(0).objects = new int[Area.ROWS][Area.COLS];
        }

        for (int r = 0; r < WorldMap.SIZE; r++) {
            for (int c = 0; c < WorldMap.SIZE; c++) {
                if (areas[r][c] != null) {
                    areas[r][c].populate(world.dist[r][c], rnd);
                }
            }
        }
    }

    // Puts the player in the start area, next to its first door.
    private void placePlayerAtStart() {
        areaRow = world.startRow;
        areaCol = world.startCol;
        visited[areaRow][areaCol] = true;
        Area start = areas[areaRow][areaCol];
        for (int d = 0; d < 4; d++) {
            if (start.doors[d]) {
                playerRow = Area.ENTRY_ROW[d];
                playerCol = Area.ENTRY_COL[d];
                break;
            }
        }
    }

    public Area currentArea() {
        return areas[areaRow][areaCol];
    }

    // Tries to walk one step. d = 0 north, 1 east, 2 south, 3 west. Walking into an object uses it.
    public void move(int d) {
        if (gameOver || won || fighting != null) {
            return;
        }
        Area area = currentArea();
        int newRow = playerRow + WorldMap.DR[d];
        int newCol = playerCol + WorldMap.DC[d];

        boolean outside = newRow < 0 || newRow >= Area.ROWS || newCol < 0 || newCol >= Area.COLS;
        if (outside || area.tiles[newRow][newCol] == Area.WALL) {
            return;
        }
        if (area.objects[newRow][newCol] != Area.NONE) {
            interact(area, newRow, newCol);
            return;
        }
        if (area.tiles[newRow][newCol] == Area.DOOR) {
            // Go through the door into the next area and appear at the opposite door.
            areaRow += WorldMap.DR[d];
            areaCol += WorldMap.DC[d];
            visited[areaRow][areaCol] = true;
            int oppositeDoor = (d + 2) % 4;
            newRow = Area.ENTRY_ROW[oppositeDoor];
            newCol = Area.ENTRY_COL[oppositeDoor];
        }
        playerRow = newRow;
        playerCol = newCol;
    }

    // The player walked into the object at (r, c): start a fight, open a chest, enter the shop or the Lost Realm.
    private void interact(Area area, int r, int c) {
        int object = area.objects[r][c];
        if (object == Area.ENEMY) {
            startFight(area.enemies[r][c], r, c);
        } else if (object == Area.OBJ_BOSS) {
            startFight(area.boss, r, c);
        } else if (object == Area.CHEST) {
            openChest(area, r, c);
        } else if (object == Area.OBJ_SHOP) {
            inShop = true;
        } else if (object == Area.OBJ_REALM && !area.realmUsed) {
            area.realmUsed = true;
            player.enterLostRealm();
            lastMessage = "The Lost Realm grants +30 max HP and +10 max MP, and restores you fully.";
        }
    }

    private void startFight(Enemy enemy, int r, int c) {
        fighting = enemy;
        fightRow = r;
        fightCol = c;
    }

    // Gives the player the gold and item of the chest and removes the chest.
    private void openChest(Area area, int r, int c) {
        Treasure treasure = area.chests[r][c];
        player.gold += treasure.gold;
        if (treasure.item != null) {
            player.inventory.add(treasure.item);
            lastMessage = "Found " + treasure.item.name + " (" + treasure.item.stats() + "). Added to inventory; equip or use it with i.";
        } else {
            lastMessage = "The chest contained gold only.";
        }
        area.objects[r][c] = Area.NONE;
        area.chests[r][c] = null;
    }

    // The player's four fight actions. Each one plays a full round: the player acts, then the enemy answers.

    public void combatAttack() {
        if (fighting == null) return;
        startRound();
        dealt = fighting.takeDamage(player.attackDamage());
        crit = player.crit;
        endRound();
    }

    public void combatSkill(int skillNumber) {
        if (fighting == null) return;
        Skill skill = player.hero.skills[skillNumber];
        if (!player.spendMana(skill.mana)) return;
        startRound();
        dealt = fighting.takeDamage(skill.damage(player));
        crit = player.crit;
        used = skill.name;
        endRound();
    }

    public void combatDefend() {
        if (fighting == null) return;
        startRound();
        player.guarding = true;
        player.heal(player.hero.defendHp);
        player.restoreMana(player.hero.defendMana);
        endRound();
    }

    public void combatPotion(int inventoryIndex) {
        if (fighting == null) return;
        if (!player.canUsePotion(inventoryIndex)) return;
        startRound();
        player.usePotion(inventoryIndex);
        endRound();
    }

    // Clears the results of the last round and remembers HP and mana so we can show what changed.
    private void startRound() {
        dealt = taken = healed = manaGained = reward = xpGain = 0;
        crit = blocked = false;
        used = "";
        fighting.move = "";
        hpBefore = player.hp;
        manaBefore = player.mana;
    }

    // Finishes the round: the enemy dies (player wins) or answers with its own turn.
    private void endRound() {
        healed = player.hp - hpBefore;
        manaGained = Math.max(0, player.mana - manaBefore);
        if (fighting.isDead()) {
            win(fighting);
            return;
        }

        blocked = player.guarding;
        taken = fighting.act(player);
        blocked = blocked && taken > 0;
        if (player.isDead()) {
            gameOver = true;
            fighting = null;
        }
    }

    // The enemy died: give gold and XP and remove it. Killing the boss wins the game.
    private void win(Enemy enemy) {
        Area area = currentArea();
        fighting = null;
        reward = enemy.goldReward;
        xpGain = enemy.xp;
        player.gold += reward;
        player.gainXp(xpGain);
        if (area.type == Area.BOSS) {
            for (int r = Area.CENTER_ROW - 1; r <= Area.CENTER_ROW + 1; r++) {
                for (int c = Area.CENTER_COL - 1; c <= Area.CENTER_COL + 1; c++) {
                    area.objects[r][c] = Area.NONE;
                }
            }
            won = true;
        } else {
            area.objects[fightRow][fightCol] = Area.NONE;
            area.enemies[fightRow][fightCol] = null;
            kills++;
        }
    }

    // Buys the shop item at this position if the player has enough gold. Gear is sold only once; potions are not.
    public void buy(int index) {
        Area area = currentArea();
        if (index < 0 || index >= area.shopStock.size()) {
            return;
        }
        Item item = area.shopStock.get(index);
        if (player.gold < item.price) {
            return;
        }
        player.gold -= item.price;
        player.inventory.add(item);
        if (!(item instanceof Potion)) {
            area.shopStock.remove(index);
        }
    }

    // The letter shown for this area on the small map.
    public static char symbol(Area area) {
        if (area.type == Area.START) return 'S';
        if (area.type == Area.BOSS) return 'B';
        if (area.type == Area.SHOP) return 'H';
        if (area.type == Area.REALM) return 'L';
        return (char) ('0' + area.countObjects());
    }
}
