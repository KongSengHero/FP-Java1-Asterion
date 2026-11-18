/*
 * Team Project: Void-Dungeon
 * Member: Member 2 (Fighting & the Player)
 *
 * File: Player.java
 *
 * Task Description:
 * The player's character: stats, level, gold, inventory and equipped gear.
 * Needs Fighter, Hero, Weapon, Armor and Potion to work (use their solutions if a teammate is not done yet).
 *
 * What to do:
 * 1. Constructor: keep the hero, give full mana, equip the hero's weapon and armor, and fill the
 *    inventory with: the weapon, the armor, and two Mixed Potions (in that order).
 * 2. getAttack(), getDefense(), critChance(): base stats plus the equipped gear.
 * 3. potionCount(): how many Potions are in the inventory (use instanceof).
 * 4. equipItem(index): put on the weapon or armor at that inventory position.
 * 5. canUsePotion(index) and usePotion(index): drink a potion (only if it would restore something).
 * 6. restoreMana(amount) and spendMana(amount).
 * 7. gainXp(amount): level up (as many times as needed) and enterLostRealm().
 *
 * Expected Output:
 * === Testing Player.java (Member 2) ===
 * [PASS] Starting stats and starting inventory are correct
 * [PASS] Attack, defense and crit include the equipped gear
 * [PASS] Equipping a weapon works; equipping armor changes max HP but does not heal
 * [PASS] Potions: counted, used once, and refused when they would do nothing
 * [PASS] Mana can be restored (up to max) and spent (only if enough)
 * [PASS] gainXp levels up with the right stat gains and a full refill
 * [PASS] Lost Realm gives +30 max HP, +10 max MP and refills
 * [SUCCESS] Player self-test passed successfully!
 *
 * Hints:
 * - The first line of the constructor (super(...)) is already written: starting HP = hero HP + armor HP.
 * - Level up: xp -= xpNext; xpNext = round(xpNext * 1.5); level++; +25 maxHp, +15 maxMana, +4 attack,
 *   +2 defense; then hp = maxHp and mana = maxMana. Use a while loop (one big XP gain can level up twice).
 * - Equipping armor: maxHp = max(1, maxHp + newArmor.maxHp - oldArmor.maxHp); hp = min(hp, maxHp).
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Fighter {
    public Hero hero;
    public int mana, maxMana;
    public int level = 1, xp = 0, xpNext = 100;
    public int gold = 30;
    public Weapon weapon;
    public Armor armor;
    public final List<Item> inventory = new ArrayList<Item>();

    public Player(Hero hero) {
        super("You", hero.hp + hero.armor.maxHp, hero.attack, hero.defense);
        // TODO: Member 2
        // 1. this.hero = hero;
        // 2. maxMana = hero.mana; mana = maxMana;
        // 3. weapon = hero.weapon; armor = hero.armor;
        // 4. inventory.add(hero.weapon), inventory.add(hero.armor), then Potion.mixed() twice.
    }

    // Base stats plus the bonuses of the equipped gear.
    public int getAttack() {
        // TODO: Member 2
        // attack + weapon.attack
        return 0;
    }

    public int getDefense() {
        // TODO: Member 2
        // defense + armor.defense
        return 0;
    }

    public double critChance() {
        // TODO: Member 2
        // 5 + weapon.crit + hero.crit
        return 0;
    }

    // How many potions are in the inventory.
    public int potionCount() {
        // TODO: Member 2
        // Loop over inventory and count the items that are `instanceof Potion`.
        return 0;
    }

    // Puts on the weapon or armor at this inventory position. Returns false if it is not gear.
    public boolean equipItem(int inventoryIndex) {
        // TODO: Member 2
        // 1. If the index is outside the inventory, return false.
        // 2. Weapon: weapon = (Weapon) item; return true.
        // 3. Armor: update maxHp (see Hints), keep hp <= maxHp, armor = newArmor; return true.
        // 4. Anything else (a potion): return false.
        return false;
    }

    // True if the item at this position is a potion that would restore something right now.
    public boolean canUsePotion(int inventoryIndex) {
        // TODO: Member 2
        // False for a bad index or a non-potion. Otherwise true if
        // (potion.healHp > 0 and hp < maxHp) or (potion.mana > 0 and mana < maxMana).
        return false;
    }

    // Drinks the potion at this position and removes it from the inventory. Returns false if it cannot be used.
    public boolean usePotion(int inventoryIndex) {
        // TODO: Member 2
        // If !canUsePotion(index) return false. Otherwise remove it from the inventory,
        // heal(potion.healHp), restoreMana(potion.mana) and return true.
        return false;
    }

    public void restoreMana(int amount) {
        // TODO: Member 2
        // mana = Math.min(maxMana, mana + amount)
    }

    // Pays mana for a skill. Returns false (and pays nothing) if there is not enough.
    public boolean spendMana(int amount) {
        // TODO: Member 2
        return false;
    }

    // Adds XP and levels up as many times as needed. Each level raises the stats and refills HP and mana.
    public void gainXp(int amount) {
        // TODO: Member 2
        // xp += amount; then while (xp >= xpNext) { ...level up, see Hints... }
    }

    // The Lost Realm: +30 max HP, +10 max mana, and a full refill.
    public void enterLostRealm() {
        // TODO: Member 2
    }

    // ===== SELF-TEST (run this file's main method; you do not need to change anything below) =====

    public static void main(String[] args) {
        System.out.println("=== Testing Player.java (Member 2) ===");

        Player p = new Player(Hero.WARRIOR);
        if (p.maxHp != 175 || p.hp != 175 || p.maxMana != 40 || p.mana != 40 || p.level != 1 || p.gold != 30 || p.xpNext != 100) {
            throw new AssertionError("Warrior should start with 175 HP, 40 MP, level 1, 30 gold");
        }
        if (p.inventory.size() != 4 || p.potionCount() != 2 || p.weapon != Hero.WARRIOR.weapon || p.armor != Hero.WARRIOR.armor) {
            throw new AssertionError("Inventory should hold weapon, armor and 2 Mixed Potions, with the weapon and armor equipped");
        }
        System.out.println("[PASS] Starting stats and starting inventory are correct");

        if (p.getAttack() != 26 || p.getDefense() != 17 || p.critChance() != 9) {
            throw new AssertionError("Warrior: attack 26, defense 17, crit 9 expected, got "
                    + p.getAttack() + ", " + p.getDefense() + ", " + p.critChance());
        }
        if (new Player(Hero.ROGUE).critChance() != 29) throw new AssertionError("Rogue crit should be 5 + 14 + 10 = 29");
        System.out.println("[PASS] Attack, defense and crit include the equipped gear");

        Weapon sword = new Weapon("Test Sword", 20, 10, 100);
        p.inventory.add(sword);
        if (p.weapon == sword) throw new AssertionError("Adding to the inventory must not equip");
        if (!p.equipItem(p.inventory.size() - 1) || p.weapon != sword) throw new AssertionError("equipItem should equip the weapon");
        Armor plate = new Armor("Test Plate", 10, 50, 100);
        p.inventory.add(plate);
        p.hp = 40;
        if (!p.equipItem(p.inventory.size() - 1) || p.armor != plate) throw new AssertionError("equipItem should equip the armor");
        if (p.maxHp != 175 - 25 + 50 || p.hp != 40) throw new AssertionError("armor should change maxHp to 200 without healing");
        if (p.equipItem(2) || p.equipItem(99) || p.equipItem(-1)) throw new AssertionError("potions and bad indexes cannot be equipped");
        System.out.println("[PASS] Equipping a weapon works; equipping armor changes max HP but does not heal");

        p.hp = p.maxHp;
        p.mana = p.maxMana;
        if (p.canUsePotion(2) || p.usePotion(2)) throw new AssertionError("a potion must be refused when HP and MP are full");
        p.hp = 10;
        p.mana = 0;
        if (!p.canUsePotion(2) || !p.usePotion(2)) throw new AssertionError("a Mixed Potion should be usable now");
        if (p.potionCount() != 1 || p.hp != 85 || p.mana != 30) throw new AssertionError("Mixed Potion gives +75 HP and +30 MP and is used up");
        if (p.canUsePotion(0) || p.canUsePotion(99)) throw new AssertionError("weapons and bad indexes are not potions");
        System.out.println("[PASS] Potions: counted, used once, and refused when they would do nothing");

        p.mana = 0;
        p.restoreMana(1000);
        if (p.mana != p.maxMana) throw new AssertionError("restoreMana must stop at maxMana");
        if (!p.spendMana(15) || p.mana != p.maxMana - 15) throw new AssertionError("spendMana should pay mana");
        int left = p.mana;
        if (p.spendMana(left + 1) || p.mana != left) throw new AssertionError("spendMana must refuse (and pay nothing) when not enough");
        System.out.println("[PASS] Mana can be restored (up to max) and spent (only if enough)");

        Player lv = new Player(Hero.MAGE);
        lv.hp = 1;
        lv.mana = 0;
        int hp0 = lv.maxHp, mp0 = lv.maxMana, atk0 = lv.attack, def0 = lv.defense;
        lv.gainXp(50);
        if (lv.level != 1 || lv.xp != 50) throw new AssertionError("50 XP should not level up");
        lv.gainXp(50);
        if (lv.level != 2 || lv.xp != 0 || lv.xpNext != 150) throw new AssertionError("100 XP total should reach level 2 with xpNext 150");
        if (lv.maxHp != hp0 + 25 || lv.maxMana != mp0 + 15 || lv.attack != atk0 + 4 || lv.defense != def0 + 2) {
            throw new AssertionError("a level gives +25 HP, +15 MP, +4 ATK, +2 DEF");
        }
        if (lv.hp != lv.maxHp || lv.mana != lv.maxMana) throw new AssertionError("a level refills HP and MP");
        lv.gainXp(150 + 225);
        if (lv.level != 4) throw new AssertionError("a big XP gain should level up several times, level is " + lv.level);
        System.out.println("[PASS] gainXp levels up with the right stat gains and a full refill");

        Player realm = new Player(Hero.WARRIOR);
        realm.hp = 1;
        realm.mana = 0;
        realm.enterLostRealm();
        if (realm.maxHp != 205 || realm.maxMana != 50 || realm.hp != 205 || realm.mana != 50) {
            throw new AssertionError("Lost Realm should give +30 max HP, +10 max MP and refill");
        }
        System.out.println("[PASS] Lost Realm gives +30 max HP, +10 max MP and refills");

        System.out.println("[SUCCESS] Player self-test passed successfully!");
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * import java.util.ArrayList;
 * import java.util.List;
 *
 * // The player's character: stats, level, gold, inventory and equipped gear.
 * public class Player extends Fighter {
 *     public Hero hero;
 *     public int mana, maxMana;
 *     public int level = 1, xp = 0, xpNext = 100;
 *     public int gold = 30;
 *     public Weapon weapon;
 *     public Armor armor;
 *     public final List<Item> inventory = new ArrayList<Item>();
 *
 *     public Player(Hero hero) {
 *         super("You", hero.hp + hero.armor.maxHp, hero.attack, hero.defense);
 *         this.hero = hero;
 *         this.maxMana = hero.mana;
 *         this.mana = maxMana;
 *         this.weapon = hero.weapon;
 *         this.armor = hero.armor;
 *
 *         inventory.add(hero.weapon);
 *         inventory.add(hero.armor);
 *         inventory.add(Potion.mixed());
 *         inventory.add(Potion.mixed());
 *     }
 *
 *     // Base stats plus the bonuses of the equipped gear.
 *     public int getAttack()  { return attack + weapon.attack; }
 *     public int getDefense() { return defense + armor.defense; }
 *     public double critChance() { return 5 + weapon.crit + hero.crit; }
 *
 *     // How many potions are in the inventory.
 *     public int potionCount() {
 *         int count = 0;
 *         for (Item item : inventory) {
 *             if (item instanceof Potion) {
 *                 count++;
 *             }
 *         }
 *         return count;
 *     }
 *
 *     // Puts on the weapon or armor at this inventory position. Returns false if it is not gear.
 *     public boolean equipItem(int inventoryIndex) {
 *         if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) {
 *             return false;
 *         }
 *         Item item = inventory.get(inventoryIndex);
 *         if (item instanceof Weapon) {
 *             weapon = (Weapon) item;
 *             return true;
 *         }
 *         if (item instanceof Armor) {
 *             Armor newArmor = (Armor) item;
 *             maxHp = Math.max(1, maxHp + newArmor.maxHp - armor.maxHp);
 *             hp = Math.min(hp, maxHp);
 *             armor = newArmor;
 *             return true;
 *         }
 *         return false;
 *     }
 *
 *     // True if the item at this position is a potion that would restore something right now.
 *     public boolean canUsePotion(int inventoryIndex) {
 *         if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) {
 *             return false;
 *         }
 *         Item item = inventory.get(inventoryIndex);
 *         if (!(item instanceof Potion)) {
 *             return false;
 *         }
 *         Potion potion = (Potion) item;
 *         boolean helpsHp = potion.healHp > 0 && hp < maxHp;
 *         boolean helpsMana = potion.mana > 0 && mana < maxMana;
 *         return helpsHp || helpsMana;
 *     }
 *
 *     // Drinks the potion at this position and removes it from the inventory. Returns false if it cannot be used.
 *     public boolean usePotion(int inventoryIndex) {
 *         if (!canUsePotion(inventoryIndex)) {
 *             return false;
 *         }
 *         Potion potion = (Potion) inventory.remove(inventoryIndex);
 *         heal(potion.healHp);
 *         restoreMana(potion.mana);
 *         return true;
 *     }
 *
 *     public void restoreMana(int amount) {
 *         mana = Math.min(maxMana, mana + amount);
 *     }
 *
 *     // Pays mana for a skill. Returns false (and pays nothing) if there is not enough.
 *     public boolean spendMana(int amount) {
 *         if (mana < amount) {
 *             return false;
 *         }
 *         mana -= amount;
 *         return true;
 *     }
 *
 *     // Adds XP and levels up as many times as needed. Each level raises the stats and refills HP and mana.
 *     public void gainXp(int amount) {
 *         xp += amount;
 *         while (xp >= xpNext) {
 *             xp -= xpNext;
 *             xpNext = (int) Math.round(xpNext * 1.5);
 *             level++;
 *             maxHp += 25;
 *             maxMana += 15;
 *             attack += 4;
 *             defense += 2;
 *             hp = maxHp;
 *             mana = maxMana;
 *         }
 *     }
 *
 *     // The Lost Realm: +30 max HP, +10 max mana, and a full refill.
 *     public void enterLostRealm() {
 *         maxHp += 30;
 *         maxMana += 10;
 *         hp = maxHp;
 *         mana = maxMana;
 *     }
 * }
 */
