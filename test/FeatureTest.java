import model.*;
import java.util.Random;

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
        Game game = new Game(new Random(9), hero);
        Player player = game.player;
        player.hp -= 25;
        player.mana -= 25;
        game.fighting = new Enemy("Guard Test Dummy", 10000, 0, 0, 0, 0);

        game.combatDefend();

        require(game.healed == hpBonus, hero.name + " Guard HP bonus should be " + hpBonus);
        require(game.manaGained == manaBonus, hero.name + " Guard MP bonus should be " + manaBonus);
        require(!player.guarding, "Guard should be consumed by the next incoming hit");
    }

    public static void main(String[] args) {
        Player player = new Player(Hero.WARRIOR);
        require(player.inventory.size() == 4, "starting gear and two potions should occupy four rows");
        require(player.potionCount() == 2, "player should start with two potions");

        Weapon weapon = new Weapon("Test Sword", 20, 10, 100);
        player.inventory.add(weapon);
        require(player.weapon != weapon, "pickup must not auto-equip a weapon");
        require(player.equipItem(player.inventory.size() - 1), "manual weapon equip should work");
        require(player.weapon == weapon, "selected weapon should become equipped");

        Armor armor = new Armor("Test Armor", 10, 50, 100);
        int previousMaxHp = player.maxHp;
        player.hp = 40;
        player.inventory.add(armor);
        require(player.armor != armor && player.maxHp == previousMaxHp, "pickup must not auto-equip armor");
        player.equipItem(player.inventory.size() - 1);
        require(player.maxHp == previousMaxHp + 25, "equipped armor should change maximum HP");
        require(player.hp == 40, "equipping armor must not heal the player");

        player.hp = 10;
        player.mana = 0;
        int oldPotionCount = player.potionCount();
        int potionIndex = itemIndex(player, "Mixed Potion");
        require(player.usePotion(potionIndex), "selected potion should be usable");
        require(player.potionCount() == oldPotionCount - 1, "using a potion should consume exactly one potion");
        require(player.hp > 10 && player.mana > 0, "standard potion should restore HP and MP");

        Player varieties = new Player(Hero.MAGE);
        varieties.inventory.add(Potion.healing());
        varieties.inventory.add(Potion.mana());
        varieties.inventory.add(Potion.great());
        require(varieties.potionCount() == 5, "all potion varieties should be counted as individual consumables");
        require(itemIndex(varieties, "Healing Potion") >= 0, "healing potion should have its own inventory row");
        require(itemIndex(varieties, "Mana Potion") >= 0, "mana potion should have its own inventory row");
        require(itemIndex(varieties, "Great Potion") >= 0, "great potion should have its own inventory row");

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

        require(Hero.MAGE.hp == 100 && Hero.MAGE.defense == 6,
                "Mage base HP and defense should reflect the survivability buff");
        require(Skill.FIREBALL.mana == 18 && Skill.FROST_NOVA.mana == 16,
                "Mage spells should have the reduced mana costs");

        Game game = new Game(new Random(3), Hero.WARRIOR);
        game.fighting = new Enemy("Potion Test Dummy", 10000, 0, 0, 0, 0);
        game.player.hp = 20;
        oldPotionCount = game.player.potionCount();
        game.combatPotion(itemIndex(game.player, "Mixed Potion"));
        require(game.player.potionCount() == oldPotionCount - 1, "combat potion action should consume one potion");
        require(game.fighting != null, "dummy should survive the potion turn");

        Random rnd = new Random(42);
        int potions = 0, weapons = 0, armorPieces = 0, goldOnly = 0;
        int normal = 0, healing = 0, mana = 0, greater = 0;
        int rolls = 10000;
        for (int i = 0; i < rolls; i++) {
            Item item = Loot.rollChest(100, rnd).item;
            if (item == null) goldOnly++;
            else if (item instanceof Potion) {
                potions++;
                if (item.name.equals("Mixed Potion")) normal++;
                else if (item.name.equals("Healing Potion")) healing++;
                else if (item.name.equals("Mana Potion")) mana++;
                else if (item.name.equals("Great Potion")) greater++;
            } else if (item instanceof Weapon) {
                weapons++;
                require(((Weapon) item).attack <= 24, "chest weapon tier must stay below shop tier 4");
            } else if (item instanceof Armor) {
                armorPieces++;
                require(((Armor) item).defense <= 16, "chest armor tier must stay below shop tier 4");
            }
        }
        require(Math.abs(potions * 100.0 / rolls - 28) < 2, "chest potion drop rate should be close to 28%");
        require(Math.abs(weapons * 100.0 / rolls - 8) < 1, "chest weapon drop rate should be close to 8%");
        require(Math.abs(armorPieces * 100.0 / rolls - 8) < 1, "chest armor drop rate should be close to 8%");
        require(Math.abs(goldOnly * 100.0 / rolls - 56) < 2, "gold-only chest rate should be close to 56%");
        require(normal > 0 && healing > 0 && mana > 0 && greater > 0,
                "chest potion drops should include all four varieties");

        Player realm = new Player(Hero.WARRIOR);
        int oldMaxHp = realm.maxHp, oldMaxMana = realm.maxMana;
        realm.hp = 1;
        realm.enterLostRealm();
        require(realm.maxHp == oldMaxHp + 30 && realm.maxMana == oldMaxMana + 10, "Lost Realm should add +30 max HP and +10 max MP");
        require(realm.hp == realm.maxHp && realm.mana == realm.maxMana, "Lost Realm should refill HP and MP");

        Random enemyRnd = new Random(1);
        require(Enemy.create(0, enemyRnd).name.equals("Skeleton"), "enemies next to the start should be Skeletons");
        require(Enemy.create(6, enemyRnd).kind >= Enemy.BLAZE, "far enemies should be Blaze or Void Guardian");
        require(new Boss().name.equals("Void Dragon"), "the boss should be the Void Dragon");

        System.out.println("Feature checks passed: potions, potion varieties, Guard bonuses, Mage tuning and loot rates.");
    }
}
