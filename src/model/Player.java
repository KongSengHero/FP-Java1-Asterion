/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 1 (Combat & Hero Systems)
 * Name: Kong Seng
 * Student ID: 2025362
 * File: Player.java
 *
 * Task Description:
 * Core hero player class (Theseus) extending Fighter.
 * Manages inventory, equipment, potion consumption, level progression, XP curve, and mana pool.
 *
 * What to do:
 * 1. Implement constructor Player(Hero hero):
 *    - Calls super("Theseus", hero.hp + hero.armor.extra, hero.atk, hero.def).
 *    - Adds starting weapon, armor, and 2 standard potions to inventory.
 * 2. Implement stat getters:
 *    - getAttack(): base attack + weapon.bonus.
 *    - getDefense(): base defense + armor.bonus.
 *    - critChance(): 5% base + weapon.extra + hero.crit.
 * 3. Implement pickUp(Item it):
 *    - Merges identical potions into existing potion stacks (samePotion check).
 *    - Adds weapons, armors, and new potions as new items in inventory.
 * 4. Implement equipItem(int index):
 *    - Equips weapon directly.
 *    - Equips armor by adjusting maxHp by the difference (it.extra - armor.extra) WITHOUT healing current HP.
 * 5. Implement usePotion(int index), spendMana(int), gainXp(int), and bless().
 * 6. Run the main method to verify inventory, stacking, equipment, and leveling mechanics.
 *
 * Expected Output:
 * === Testing Player.java (Member 1) ===
 * [PASS] Initial inventory contains weapon, armor, and stacked starting potions
 * [PASS] Potion pickup merges into stack rather than creating duplicate row
 * [PASS] Manual weapon equip updates attack stat
 * [PASS] Armor equip modifies maxHp without restoring current HP
 * [PASS] Potion consumption restores HP/MP and decrements stack count
 * [PASS] XP progression scales level and grants stat boosts
 * [PASS] Athena blessing restores full HP and Mana
 * [SUCCESS] Player self-test passed successfully!
 *
 * Hints:
 * - Equipment is never auto-equipped upon pickup.
 * - Armor changing maxHp must keep current HP clamped to Math.min(hp, maxHp).
 * - XP required scales by 1.5x each level: xpNext = Math.round(xpNext * 1.5).
 */

package model;

import java.util.ArrayList; 
import java.util.List; 

public class Player extends Fighter { 
    public Hero hero; 
    public int mana, maxMana; 
    public int level = 1, xp = 0, xpNext = 100; 
    public int gold = 30; 
    public Item weapon, armor; 
    public final List<Item> inventory = new ArrayList<Item>(); 
    
    public Player(Hero hero) { 
        super("Theseus", hero.hp + hero.armor.extra, hero.atk, hero.def); 
        this.hero = hero; 
        this.maxMana = hero.mana; 
        this.mana = maxMana; 
        this.weapon = hero.weapon; 
        this.armor = hero.armor; 
        
        // Starting equipment stays in the inventory
        inventory.add(hero.weapon); 
        inventory.add(hero.armor); 
        pickUp(Item.potion()); 
        pickUp(Item.potion()); 
    }
    
    public int getAttack() { 
        // TODO: Member 1
        // Return base attack + weapon.bonus.
        return 0; 
    }
    
    public int getDefense() { 
        // TODO: Member 1
        // Return base defense + armor.bonus.
        return 0; 
    }
    
    public double critChance() { 
        // TODO: Member 1
        // Return 5% base crit + weapon.extra + hero.crit.
        return 0; 
    }
    
    public void pickUp(Item it) { 
        // TODO: Member 1
        // 1. If it == null, return.
        // 2. If it.type == Item.POTION:
        //    - Search inventory for owned item where owned.samePotion(it) is true.
        //    - If found, increment owned.quantity by it.quantity and return.
        //    - If not found, create new Item copy with it.quantity and add to inventory.
        // 3. For weapons and armor, add directly to inventory.
    }
    
    public int potionCount() { 
        // TODO: Member 1
        // Count total quantity of all POTION items in inventory.
        return 0; 
    }
    
    public int firstPotionIndex() { 
        // TODO: Member 1
        // Find index of first POTION item in inventory. Return -1 if none found.
        return -1; 
    }
    
    public boolean isEquipped(Item it) { 
        return it == weapon || it == armor; 
    }
    
    public boolean isUpgrade(Item candidate) { 
        // TODO: Member 1
        // Compare candidate against equipped weapon or armor.
        // Return true if candidate has higher bonus, or equal bonus with higher extra.
        return false; 
    }
    
    public boolean equipItem(int inventoryIndex) { 
        // TODO: Member 1
        // 1. Validate inventoryIndex bounds.
        // 2. If weapon: set weapon = it and return true.
        // 3. If armor:
        //    - If already equipped, return true.
        //    - int hpChange = it.extra - armor.extra.
        //    - maxHp = Math.max(1, maxHp + hpChange).
        //    - hp = Math.min(hp, maxHp) (do not heal current HP).
        //    - armor = it. Return true.
        return false; 
    }
    
    public boolean canUsePotion(int inventoryIndex) { 
        // TODO: Member 1
        // Return true only if item at inventoryIndex is a POTION AND
        // (potion heals HP and hp < maxHp) OR (potion restores MP and mana < maxMana).
        return false; 
    }
    
    public boolean usePotion(int inventoryIndex) { 
        // TODO: Member 1
        // 1. Check canUsePotion(inventoryIndex). If false, return false.
        // 2. If potion.bonus > 0, call heal(potion.bonus).
        // 3. If potion.extra > 0, call restoreMana(potion.extra).
        // 4. If potion.quantity > 1, decrement quantity. Else remove item from inventory.
        // 5. Return true.
        return false; 
    }
    
    public boolean usePotion() { 
        return usePotion(firstPotionIndex()); 
    }
    
    public void restoreMana(int amount) { 
        mana = Math.min(maxMana, mana + amount); 
    }
    
    public boolean spendMana(int amount) { 
        if (mana < amount) return false; 
        mana -= amount; 
        return true; 
    }
    
    public void gainXp(int amount) { 
        // TODO: Member 1
        // 1. Add amount to xp.
        // 2. While xp >= xpNext:
        //    - xp -= xpNext.
        //    - xpNext = (int) Math.round(xpNext * 1.5).
        //    - level++, maxHp += 25, maxMana += 15, attack += 4, defense += 2.
        //    - hp = maxHp, mana = maxMana (full refill).
    }
    
    public void bless() { 
        // TODO: Member 1
        // Full refill: hp = maxHp, mana = maxMana.
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Player.java (Member 1) ==="); 
        
        Player p = new Player(Hero.WARRIOR); 
        if (p.inventory.size() != 3 || p.potionCount() != 2) { 
            throw new AssertionError("Starting inventory size or potion count mismatch"); 
        }
        System.out.println("[PASS] Initial inventory contains weapon, armor, and stacked starting potions"); 
        
        p.pickUp(Item.potion()); 
        if (p.inventory.size() != 3 || p.potionCount() != 3) { 
            throw new AssertionError("Identical potion did not stack correctly"); 
        }
        System.out.println("[PASS] Potion pickup merges into stack rather than creating duplicate row"); 
        
        Item upgradeWeapon = new Item("Epic Blade", Item.WEAPON, 25, 10, 100); 
        p.pickUp(upgradeWeapon); 
        int prevAtk = p.getAttack(); 
        p.equipItem(p.inventory.size() - 1); 
        if (p.weapon != upgradeWeapon || p.getAttack() <= prevAtk) { 
            throw new AssertionError("Weapon equip did not update attack stat properly"); 
        }
        System.out.println("[PASS] Manual weapon equip updates attack stat"); 
        
        Item upgradeArmor = new Item("Epic Armor", Item.ARMOR, 15, 60, 150); 
        p.pickUp(upgradeArmor); 
        int oldMaxHp = p.maxHp; 
        p.hp = 50; 
        p.equipItem(p.inventory.size() - 1); 
        if (p.maxHp != oldMaxHp + (60 - Hero.WARRIOR.armor.extra) || p.hp != 50) { 
            throw new AssertionError("Armor equip wrongly healed player or failed maxHp calculation"); 
        }
        System.out.println("[PASS] Armor equip modifies maxHp without restoring current HP"); 
        
        int prePotions = p.potionCount(); 
        p.usePotion(); 
        if (p.potionCount() != prePotions - 1 || p.hp <= 50) { 
            throw new AssertionError("Potion consumption failed"); 
        }
        System.out.println("[PASS] Potion consumption restores HP/MP and decrements stack count"); 
        
        p.gainXp(120); 
        if (p.level != 2 || p.xp != 20) { 
            throw new AssertionError("XP progression failed"); 
        }
        System.out.println("[PASS] XP progression scales level and grants stat boosts"); 
        
        p.hp = 10; 
        p.mana = 5; 
        p.bless(); 
        if (p.hp != p.maxHp || p.mana != p.maxMana) { 
            throw new AssertionError("Blessing did not restore full resources"); 
        }
        System.out.println("[PASS] Athena blessing restores full HP and Mana"); 
        
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
 * public class Player extends Fighter {
 *     public Hero hero;
 *     public int mana, maxMana;
 *     public int level = 1, xp = 0, xpNext = 100;
 *     public int gold = 30;
 *     public Item weapon, armor;
 *     public final List<Item> inventory = new ArrayList<Item>();
 *
 *     public Player(Hero hero) {
 *         super("Theseus", hero.hp + hero.armor.extra, hero.atk, hero.def);
 *         this.hero = hero;
 *         this.maxMana = hero.mana;
 *         this.mana = maxMana;
 *         this.weapon = hero.weapon;
 *         this.armor = hero.armor;
 *
 *         inventory.add(hero.weapon);
 *         inventory.add(hero.armor);
 *         pickUp(Item.potion());
 *         pickUp(Item.potion());
 *     }
 *
 *     public int getAttack()  { return attack + weapon.bonus; }
 *     public int getDefense() { return defense + armor.bonus; }
 *     public double critChance() { return 5 + weapon.extra + hero.crit; }
 *
 *     public void pickUp(Item it) {
 *         if (it == null) return;
 *         if (it.type == Item.POTION) {
 *             for (Item owned : inventory) {
 *                 if (owned.samePotion(it)) {
 *                     owned.quantity += it.quantity;
 *                     return;
 *                 }
 *             }
 *             Item stack = new Item(it.name, it.type, it.bonus, it.extra, it.price);
 *             stack.quantity = it.quantity;
 *             inventory.add(stack);
 *             return;
 *         }
 *         inventory.add(it);
 *     }
 *
 *     public int potionCount() {
 *         int count = 0;
 *         for (Item it : inventory) if (it.type == Item.POTION) count += it.quantity;
 *         return count;
 *     }
 *
 *     public int firstPotionIndex() {
 *         for (int i = 0; i < inventory.size(); i++) {
 *             if (inventory.get(i).type == Item.POTION) return i;
 *         }
 *         return -1;
 *     }
 *
 *     public boolean isEquipped(Item it) {
 *         return it == weapon || it == armor;
 *     }
 *
 *     public boolean isUpgrade(Item candidate) {
 *         if (candidate == null) return false;
 *         if (candidate.type == Item.WEAPON) {
 *             return candidate.bonus > weapon.bonus
 *                     || (candidate.bonus == weapon.bonus && candidate.extra > weapon.extra);
 *         }
 *         if (candidate.type == Item.ARMOR) {
 *             return candidate.bonus > armor.bonus
 *                     || (candidate.bonus == armor.bonus && candidate.extra > armor.extra);
 *         }
 *         return false;
 *     }
 *
 *     public boolean equipItem(int inventoryIndex) {
 *         if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) return false;
 *         Item it = inventory.get(inventoryIndex);
 *         if (it.type == Item.WEAPON) {
 *             weapon = it;
 *             return true;
 *         }
 *         if (it.type == Item.ARMOR) {
 *             if (it == armor) return true;
 *             int hpChange = it.extra - armor.extra;
 *             maxHp = Math.max(1, maxHp + hpChange);
 *             hp = Math.min(hp, maxHp);
 *             armor = it;
 *             return true;
 *         }
 *         return false;
 *     }
 *
 *     public boolean canUsePotion(int inventoryIndex) {
 *         if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) return false;
 *         Item potion = inventory.get(inventoryIndex);
 *         if (potion.type != Item.POTION) return false;
 *         return (potion.bonus > 0 && hp < maxHp) || (potion.extra > 0 && mana < maxMana);
 *     }
 *
 *     public boolean usePotion(int inventoryIndex) {
 *         if (!canUsePotion(inventoryIndex)) return false;
 *         Item potion = inventory.get(inventoryIndex);
 *
 *         if (potion.bonus > 0) heal(potion.bonus);
 *         if (potion.extra > 0) restoreMana(potion.extra);
 *         if (potion.quantity > 1) potion.quantity--;
 *         else inventory.remove(inventoryIndex);
 *         return true;
 *     }
 *
 *     public boolean usePotion() {
 *         return usePotion(firstPotionIndex());
 *     }
 *
 *     public void restoreMana(int amount) { mana = Math.min(maxMana, mana + amount); }
 *
 *     public boolean spendMana(int amount) {
 *         if (mana < amount) return false;
 *         mana -= amount;
 *         return true;
 *     }
 *
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
 *     public void bless() {
 *         hp = maxHp;
 *         mana = maxMana;
 *     }
 * }
 */
