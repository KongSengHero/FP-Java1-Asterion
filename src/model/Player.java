package model;

import java.util.ArrayList;
import java.util.List;

// The player's character: stats, level, gold, inventory and equipped gear.
public class Player extends Fighter {
    public Hero hero;
    public int mana, maxMana;
    public int level = 1, xp = 0, xpNext = 100;
    public int gold = 30;
    public Weapon weapon;
    public Armor armor;
    public final List<Item> inventory = new ArrayList<Item>();

    public Player(Hero hero) {
        super("Theseus", hero.hp + hero.armor.maxHp, hero.attack, hero.defense);
        this.hero = hero;
        this.maxMana = hero.mana;
        this.mana = maxMana;
        this.weapon = hero.weapon;
        this.armor = hero.armor;

        inventory.add(hero.weapon);
        inventory.add(hero.armor);
        inventory.add(Potion.mixed());
        inventory.add(Potion.mixed());
    }

    // Base stats plus the bonuses of the equipped gear.
    public int getAttack()  { return attack + weapon.attack; }
    public int getDefense() { return defense + armor.defense; }
    public double critChance() { return 5 + weapon.crit + hero.crit; }

    // How many potions are in the inventory.
    public int potionCount() {
        int count = 0;
        for (Item item : inventory) {
            if (item instanceof Potion) {
                count++;
            }
        }
        return count;
    }

    // Puts on the weapon or armor at this inventory position. Returns false if it is not gear.
    public boolean equipItem(int inventoryIndex) {
        if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) {
            return false;
        }
        Item item = inventory.get(inventoryIndex);
        if (item instanceof Weapon) {
            weapon = (Weapon) item;
            return true;
        }
        if (item instanceof Armor) {
            Armor newArmor = (Armor) item;
            maxHp = Math.max(1, maxHp + newArmor.maxHp - armor.maxHp);
            hp = Math.min(hp, maxHp);
            armor = newArmor;
            return true;
        }
        return false;
    }

    // True if the item at this position is a potion that would restore something right now.
    public boolean canUsePotion(int inventoryIndex) {
        if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) {
            return false;
        }
        Item item = inventory.get(inventoryIndex);
        if (!(item instanceof Potion)) {
            return false;
        }
        Potion potion = (Potion) item;
        boolean helpsHp = potion.healHp > 0 && hp < maxHp;
        boolean helpsMana = potion.mana > 0 && mana < maxMana;
        return helpsHp || helpsMana;
    }

    // Drinks the potion at this position and removes it from the inventory. Returns false if it cannot be used.
    public boolean usePotion(int inventoryIndex) {
        if (!canUsePotion(inventoryIndex)) {
            return false;
        }
        Potion potion = (Potion) inventory.remove(inventoryIndex);
        heal(potion.healHp);
        restoreMana(potion.mana);
        return true;
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    // Pays mana for a skill. Returns false (and pays nothing) if there is not enough.
    public boolean spendMana(int amount) {
        if (mana < amount) {
            return false;
        }
        mana -= amount;
        return true;
    }

    // Adds XP and levels up as many times as needed. Each level raises the stats and refills HP and mana.
    public void gainXp(int amount) {
        xp += amount;
        while (xp >= xpNext) {
            xp -= xpNext;
            xpNext = (int) Math.round(xpNext * 1.5);
            level++;
            maxHp += 25;
            maxMana += 15;
            attack += 4;
            defense += 2;
            hp = maxHp;
            mana = maxMana;
        }
    }

    // The Lost Realm: +30 max HP, +10 max mana, and a full refill.
    public void enterLostRealm() {
        maxHp += 30;
        maxMana += 10;
        hp = maxHp;
        mana = maxMana;
    }
}
