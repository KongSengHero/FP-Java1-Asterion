import model.*;
import java.util.Random;

// Regression checks for inventory stacks, potion effects, class Guard bonuses and loot tuning.
public class FeatureTest {
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static int itemIndex(Player player, String name) {
        for (int i = 0; i < player.inventory.size(); i++) {
            if (player.inventory.get(i).name.equals(name)) return i;
        }
        return -1;
    }

    private static void checkDefend(Hero hero, int hpBonus, int manaBonus) {
        Game game = new Game(false, new Random(9), hero);
        Player player = game.player;
        player.hp -= 25;
        player.mana -= 25;
        game.fighting = new Enemy("Guard Test Dummy", 10000, 0, 0, 0, 0);

        game.combatDefend();

        require(game.healed == hpBonus, hero.label + " Guard HP bonus should be " + hpBonus);
        require(game.manaGained == manaBonus, hero.label + " Guard MP bonus should be " + manaBonus);
        require(!player.guarding, "Guard should be consumed by the next incoming hit");
    }

    public static void main(String[] args) {
        Player player = new Player(Hero.WARRIOR);
        require(player.inventory.size() == 3, "starting gear and two matching potions should occupy three rows");
        require(player.potionCount() == 2, "player should start with two potions");

        Item startingPotion = player.inventory.get(2);
        require(startingPotion.name.equals("Potion") && startingPotion.quantity == 2,
                "identical starting potions should be stacked");
        player.pickUp(Item.potion());
        require(player.inventory.size() == 3 && startingPotion.quantity == 3,
                "picking up an identical potion should increase the stack, not add a row");

        Item weapon = new Item("Test Sword", Item.WEAPON, 20, 10, 100);
        player.pickUp(weapon);
        require(player.weapon != weapon, "pickup must not auto-equip a weapon");
        require(player.equipItem(player.inventory.size() - 1), "manual weapon equip should work");
        require(player.weapon == weapon, "selected weapon should become equipped");

        Item armor = new Item("Test Armor", Item.ARMOR, 10, 50, 100);
        int previousMaxHp = player.maxHp;
        player.hp = 40;
        player.pickUp(armor);
        require(player.armor != armor && player.maxHp == previousMaxHp, "pickup must not auto-equip armor");
        player.equipItem(player.inventory.size() - 1);
        require(player.maxHp == previousMaxHp + 25, "equipped armor should change maximum HP");
        require(player.hp == 40, "equipping armor must not heal the player");

        player.hp = 10;
        player.mana = 0;
        int oldPotionCount = player.potionCount();
        int potionIndex = itemIndex(player, "Potion");
        require(player.usePotion(potionIndex), "selected potion should be usable");
        require(player.potionCount() == oldPotionCount - 1, "using a potion should consume exactly one from its stack");
        require(startingPotion.quantity == 2, "using a stacked potion should decrement its quantity by one");
        require(player.hp > 10 && player.mana > 0, "standard potion should restore HP and MP");

        Player varieties = new Player(Hero.MAGE);
        varieties.pickUp(Item.healingPotion());
        varieties.pickUp(Item.manaPotion());
        varieties.pickUp(Item.greaterPotion());
        require(varieties.potionCount() == 5, "all potion varieties should be counted as individual consumables");
        require(itemIndex(varieties, "Healing Potion") >= 0, "healing potion should have its own inventory stack");
        require(itemIndex(varieties, "Mana Potion") >= 0, "mana potion should have its own inventory stack");
        require(itemIndex(varieties, "Greater Potion") >= 0, "greater potion should have its own inventory stack");

        varieties.hp = varieties.maxHp;
        varieties.mana = 0;
        int hpBeforeManaPotion = varieties.hp;
        require(varieties.usePotion(itemIndex(varieties, "Mana Potion")), "mana potion should work when HP is full");
        require(varieties.hp == hpBeforeManaPotion && varieties.mana == 60,
                "mana potion should only restore mana");
        varieties.hp -= 20;
        int manaBeforeHealingPotion = varieties.mana;
        require(varieties.usePotion(itemIndex(varieties, "Healing Potion")), "healing potion should work when mana is not full");
        require(varieties.hp > varieties.maxHp - 20 && varieties.mana == manaBeforeHealingPotion,
                "healing potion should only restore HP");

        Fighter guardDummy = new Fighter("Guard Formula Dummy", 100, 0, 10);
        require(guardDummy.takeDamage(30) == 25, "normal defense should absorb half its value");
        guardDummy.hp = 100;
        guardDummy.guarding = true;
        require(guardDummy.takeDamage(30) == 20, "Guard should double defense without halving remaining damage");
        require(!guardDummy.guarding, "Guard should last for one incoming hit");

        checkDefend(Hero.WARRIOR, 12, 0);
        checkDefend(Hero.MAGE, 0, 15);
        checkDefend(Hero.ROGUE, 5, 5);

        require(Hero.MAGE.hp == 100 && Hero.MAGE.def == 6,
                "Mage base HP and defense should reflect the survivability buff");
        require(Skill.FIREBALL.mana == 18 && Skill.FROST_NOVA.mana == 16,
                "Mage spells should have the reduced mana costs");

        Game game = new Game(false, new Random(3), Hero.WARRIOR);
        game.fighting = new Enemy("Potion Test Dummy", 10000, 0, 0, 0, 0);
        game.player.hp = 20;
        oldPotionCount = game.player.potionCount();
        game.combatPotion(game.player.firstPotionIndex());
        require(game.player.potionCount() == oldPotionCount - 1, "combat potion action should consume one potion from the selected stack");
        require(game.fighting != null, "dummy should survive the potion turn");

        Random rnd = new Random(42);
        int potions = 0, weapons = 0, armorPieces = 0, goldOnly = 0;
        int normal = 0, healing = 0, mana = 0, greater = 0;
        int rolls = 10000;
        for (int i = 0; i < rolls; i++) {
            Item item = Treasure.roll(100, rnd).item;
            if (item == null) goldOnly++;
            else if (item.type == Item.POTION) {
                potions++;
                if (item.name.equals("Potion")) normal++;
                else if (item.name.equals("Healing Potion")) healing++;
                else if (item.name.equals("Mana Potion")) mana++;
                else if (item.name.equals("Greater Potion")) greater++;
            } else if (item.type == Item.WEAPON) {
                weapons++;
                require(item.bonus <= 24, "chest weapon tier must stay below shop tier 4");
            } else if (item.type == Item.ARMOR) {
                armorPieces++;
                require(item.bonus <= 16, "chest armor tier must stay below shop tier 4");
            }
        }
        require(Math.abs(potions * 100.0 / rolls - 28) < 2, "chest potion drop rate should be close to 28%");
        require(Math.abs(weapons * 100.0 / rolls - 8) < 1, "chest weapon drop rate should be close to 8%");
        require(Math.abs(armorPieces * 100.0 / rolls - 8) < 1, "chest armor drop rate should be close to 8%");
        require(Math.abs(goldOnly * 100.0 / rolls - 56) < 2, "gold-only chest rate should be close to 56%");
        require(normal > 0 && healing > 0 && mana > 0 && greater > 0,
                "chest potion drops should include all four varieties");

        System.out.println("Feature checks passed: stacked potions, potion varieties, Guard bonuses, Mage tuning and loot rates.");
    }
}
