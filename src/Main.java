import model.*;
import ui.*;
import java.util.Random;

// Starts the game:   ./run.sh     (or run.bat on Windows)
// Options:  --plain  no colors, plain characters        --lines  type keys + Enter instead of instant keys
//           --random  random labyrinth layout (default: one of 4 fixed maps)      --seed N  same seed = same labyrinth
public class Main {
    public static void main(String[] args) {
        boolean plain = false, lines = false, random = false;
        Long seed = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--plain")) plain = true;
            else if (args[i].equals("--lines")) lines = true;
            else if (args[i].equals("--random")) random = true;
            else if (args[i].equals("--seed") && i + 1 < args.length) seed = Long.parseLong(args[++i]);
        }
        Term.setup(plain, lines);

        while (Menu.title()) {
            Hero hero = Menu.chooseHero();
            if (hero == null) continue;
            Game game = new Game(random, seed == null ? new Random() : new Random(seed), hero);
            if (new RoomScreen(game).play()) Menu.ending(game);   // false = the player quit
        }
    }
}
