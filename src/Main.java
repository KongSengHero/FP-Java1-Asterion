import model.*;
import ui.*;
import java.util.Random;

// Entry point of the Void-Dungeon game.
public class Main {
    // Starts the game: reads the options, then shows the menu and runs games until the player quits.
    public static void main(String[] args) {
        boolean plain = false, lines = false;
        Long seed = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--plain")) plain = true;
            else if (args[i].equals("--lines")) lines = true;
            else if (args[i].equals("--seed") && i + 1 < args.length) seed = Long.parseLong(args[++i]);
        }
        Term.setup(plain, lines);

        while (Menu.title()) {
            Hero hero = Menu.chooseHero();
            if (hero == null) continue;
            Game game = new Game(seed == null ? new Random() : new Random(seed), hero);
            if (new RoomScreen(game).play()) Menu.ending(game);
        }
    }
}
