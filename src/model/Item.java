/*
 * Team Project: Labyrinth of Asterion
 * Member: Member 2 (Items, Skills & Economy)
 * 
 * 
 * File: Item.java
 *
 * Task Description:
 * Data model for equipment (Weapons, Armor) and consumables (Potions).
 * Implements potion factory methods, stack-comparison logic (samePotion), and stats formatting.
 *
 * What to do:
 * 1. Declare constants WEAPON = 0, ARMOR = 1, POTION = 2.
 * 2. Implement constructor Item(name, type, bonus, extra, price).
 * 3. Implement factory methods for all 4 potion variants:
 *    - potion(): +75 HP, +30 MP, price 60.
 *    - healingPotion(): +110 HP, 0 MP, price 75.
 *    - manaPotion(): 0 HP, +60 MP, price 70.
 *    - greaterPotion(): +140 HP, +55 MP, price 135.
 * 4. Implement randomPotion(Random rnd) with distribution:
 *    - roll < 40 -> potion()
 *    - roll < 67 -> healingPotion()
 *    - roll < 92 -> manaPotion()
 *    - else -> greaterPotion()
 * 5. Implement samePotion(Item other) for inventory stack merging.
 * 6. Implement stats() returning human-readable equipment bonuses.
 *
 * Expected Output:
 * === Testing Item.java (Member 2) ===
 * [PASS] Potion factory values match balance specifications
 * [PASS] randomPotion generates all 4 varieties
 * [PASS] samePotion returns true for matching potions, false for distinct items
 * [PASS] stats() formats weapon, armor, and potion strings accurately
 * [SUCCESS] Item self-test passed successfully!
 *
 * Hints:
 * - bonus represents +ATK for weapons, +DEF for armor, and HP healed for potions.
 * - extra represents +crit % for weapons, +max HP for armor, and MP restored for potions.
 * - samePotion must verify both items are POTION type, share the same name, bonus, and extra.
 */

package model;

import java.util.Random; 

public class Item { 
    public static final int WEAPON = 0; 
    public static final int ARMOR = 1; 
    public static final int POTION = 2; 
    
    public String name; 
    public int type; 
    public int bonus; 
    public int extra; 
    public int price; 
    public int quantity = 1; 
    
    public Item(String name, int type, int bonus, int extra, int price) { 
        this.name = name; 
        this.type = type; 
        this.bonus = bonus; 
        this.extra = extra; 
        this.price = price; 
    }
    
    public static Item potion() { 
        // TODO: Member 2
        // Return standard potion: "Potion", POTION, 75 HP bonus, 30 MP extra, price 60.
        return new Item("Potion", POTION, 75, 30, 60); 
    }
    
    public static Item healingPotion() { 
        // TODO: Member 2
        // Return healing potion: "Healing Potion", POTION, 110 HP bonus, 0 MP extra, price 75.
        return new Item("Healing Potion", POTION, 110, 0, 75); 
    }
    
    public static Item manaPotion() { 
        // TODO: Member 2
        // Return mana potion: "Mana Potion", POTION, 0 HP bonus, 60 MP extra, price 70.
        return new Item("Mana Potion", POTION, 0, 60, 70); 
    }
    
    public static Item greaterPotion() { 
        // TODO: Member 2
        // Return greater potion: "Greater Potion", POTION, 140 HP bonus, 55 MP extra, price 135.
        return new Item("Greater Potion", POTION, 140, 55, 135); 
    }
    
    public static Item randomPotion(Random rnd) { 
        // TODO: Member 2
        // Roll rnd.nextInt(100):
        // - roll < 40 -> potion()
        // - roll < 67 -> healingPotion()
        // - roll < 92 -> manaPotion()
        // - else -> greaterPotion()
        return null; 
    }
    
    public boolean samePotion(Item other) { 
        // TODO: Member 2
        // Return true if other != null, both are POTION, and have identical name, bonus, and extra.
        return false; 
    }
    
    public String stats() { 
        // TODO: Member 2
        // If type == WEAPON: return "+<bonus> ATK, <extra>% crit"
        // If type == ARMOR: return "+<bonus> DEF, +<extra> HP"
        // If type == POTION: return "<bonus> HP, <extra> MP"
        return ""; 
    }
    
    public static void main(String[] args) { 
        System.out.println("=== Testing Item.java (Member 2) ==="); 
        
        Item p = potion(); 
        Item hp = healingPotion(); 
        Item mp = manaPotion(); 
        Item gp = greaterPotion(); 
        if (p.bonus != 75 || p.extra != 30 || hp.bonus != 110 || mp.extra != 60 || gp.bonus != 140) { 
            throw new AssertionError("Potion stat initialization error"); 
        }
        System.out.println("[PASS] Potion factory values match balance specifications"); 
        
        Random rnd = new Random(42); 
        boolean hasNormal = false, hasHeal = false, hasMana = false, hasGreat = false; 
        for (int i = 0; i < 200; i++) { 
            Item it = randomPotion(rnd); 
            if (it == null) throw new AssertionError("randomPotion returned null"); 
            if (it.name.equals("Potion")) hasNormal = true; 
            else if (it.name.equals("Healing Potion")) hasHeal = true; 
            else if (it.name.equals("Mana Potion")) hasMana = true; 
            else if (it.name.equals("Greater Potion")) hasGreat = true; 
        }
        if (!hasNormal || !hasHeal || !hasMana || !hasGreat) { 
            throw new AssertionError("randomPotion failed to generate all potion varieties"); 
        }
        System.out.println("[PASS] randomPotion generates all 4 varieties"); 
        
        Item p2 = potion(); 
        Item w = new Item("Sword", WEAPON, 10, 5, 50); 
        if (!p.samePotion(p2) || p.samePotion(hp) || p.samePotion(w)) { 
            throw new AssertionError("samePotion identity comparison error"); 
        }
        System.out.println("[PASS] samePotion returns true for matching potions, false for distinct items"); 
        
        if (!w.stats().equals("+10 ATK, 5% crit") || !p.stats().equals("75 HP, 30 MP")) { 
            throw new AssertionError("stats formatting mismatch: " + w.stats()); 
        }
        System.out.println("[PASS] stats() formats weapon, armor, and potion strings accurately"); 
        
        System.out.println("[SUCCESS] Item self-test passed successfully!"); 
    }
}

/*
 * ========================================================
 * SOLUTION / REFERENCE IMPLEMENTATION:
 * ========================================================
 *
 * package model;
 *
 * import java.util.Random;
 *
 * public class Item {
 *     public static final int WEAPON = 0;
 *     public static final int ARMOR = 1;
 *     public static final int POTION = 2;
 *
 *     public String name;
 *     public int type;
 *     public int bonus;
 *     public int extra;
 *     public int price;
 *     public int quantity = 1;
 *
 *     public Item(String name, int type, int bonus, int extra, int price) {
 *         this.name = name;
 *         this.type = type;
 *         this.bonus = bonus;
 *         this.extra = extra;
 *         this.price = price;
 *     }
 *
 *     public static Item potion() {
 *         return new Item("Potion", POTION, 75, 30, 60);
 *     }
 *
 *     public static Item healingPotion() {
 *         return new Item("Healing Potion", POTION, 110, 0, 75);
 *     }
 *
 *     public static Item manaPotion() {
 *         return new Item("Mana Potion", POTION, 0, 60, 70);
 *     }
 *
 *     public static Item greaterPotion() {
 *         return new Item("Greater Potion", POTION, 140, 55, 135);
 *     }
 *
 *     public static Item randomPotion(Random rnd) {
 *         int roll = rnd.nextInt(100);
 *         if (roll < 40) return potion();
 *         if (roll < 67) return healingPotion();
 *         if (roll < 92) return manaPotion();
 *         return greaterPotion();
 *     }
 *
 *     public boolean samePotion(Item other) {
 *         return other != null && type == POTION && other.type == POTION
 *                 && name.equals(other.name) && bonus == other.bonus && extra == other.extra;
 *     }
 *
 *     public String stats() {
 *         if (type == WEAPON) return "+" + bonus + " ATK, " + extra + "% crit";
 *         if (type == ARMOR) return "+" + bonus + " DEF, +" + extra + " HP";
 *         return bonus + " HP, " + extra + " MP";
 *     }
 * }
 */
