package model;

// Gives extra defense and extra maximum HP while equipped.
public class Armor extends Item {
    public int defense;
    public int maxHp;

    public Armor(String name, int defense, int maxHp, int price) {
        super(name, price);
        this.defense = defense;
        this.maxHp = maxHp;
    }

    public String stats() {
        return "+" + defense + " DEF, +" + maxHp + " HP";
    }

    public String typeName() {
        return "ARMOR ";
    }

    // Better if it defends more, or defends the same but gives more HP.
    public boolean isUpgradeFor(Player player) {
        Armor current = player.armor;
        if (defense != current.defense) {
            return defense > current.defense;
        }
        return maxHp > current.maxHp;
    }
}
