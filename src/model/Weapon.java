package model;

// Gives extra attack and extra critical hit chance while equipped.
public class Weapon extends Item {
    public int attack;
    public int crit;  // extra critical hit chance in percent

    public Weapon(String name, int attack, int crit, int price) {
        super(name, price);
        this.attack = attack;
        this.crit = crit;
    }

    public String stats() {
        return "+" + attack + " ATK, " + crit + "% crit";
    }

    public String typeName() {
        return "WEAPON";
    }

    // Better if it hits harder, or hits the same but crits more.
    public boolean isUpgradeFor(Player player) {
        Weapon current = player.weapon;
        if (attack != current.attack) {
            return attack > current.attack;
        }
        return crit > current.crit;
    }
}
