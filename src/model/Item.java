package model;

// Anything you can carry in the inventory. Weapon, Armor and Potion extend it.
public abstract class Item {
    public String name;
    public int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Short text with the numbers of this item, shown in lists.
    public abstract String stats();

    // Label shown in the inventory ("WEAPON", "ARMOR ", "POTION").
    public abstract String typeName();

    // True if this item is better than what the player wears now. Only gear can be an upgrade.
    public boolean isUpgradeFor(Player player) {
        return false;
    }
}
