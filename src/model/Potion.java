package model;

// A one-use item that restores HP, mana or both.
public class Potion extends Item {
    public int healHp;
    public int mana;  // mana restored

    public Potion(String name, int healHp, int mana, int price) {
        super(name, price);
        this.healHp = healHp;
        this.mana = mana;
    }

    // The four potions of the game.
    public static Potion healing() { return new Potion("Healing Potion", 110, 0, 75); }
    public static Potion mana()    { return new Potion("Mana Potion", 0, 60, 70); }
    public static Potion mixed()   { return new Potion("Mixed Potion", 75, 30, 60); }
    public static Potion great()   { return new Potion("Great Potion", 140, 55, 135); }

    public String stats() {
        return healHp + " HP, " + mana + " MP";
    }

    public String typeName() {
        return "POTION";
    }
}
