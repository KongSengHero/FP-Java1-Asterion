package ui;

import model.Hero;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

// Shared by all screens: colors, bars, drawing a screen, and reading single key presses.
//   Screens are built line by line with println() and appear when the game waits for a key.
//   key() returns the key at once, without Enter. If that is not possible on this computer
//   (or with --lines), the player types keys and presses Enter instead. Everything else works the same.
public class Term {
    public static final String RED = "91", GREEN = "92", YELLOW = "93", BLUE = "94", MAGENTA = "95",
            CYAN = "96", WHITE = "97", GRAY = "90", BROWN = "33", ORANGE = "38;5;208";
    public static final char ENTER = '\n', ESC = 27;

    public static boolean colors = true;   // false: no colors and no cursor tricks
    public static boolean ascii = false;   // true: only plain ASCII characters (no blocks, no lines)

    private static final StringBuilder frame = new StringBuilder();   // the screen being built
    private static final ArrayDeque<Character> pending = new ArrayDeque<Character>();   // typed keys not used yet (Enter mode)
    private static final Scanner IN = new Scanner(System.in);
    private static boolean instant = false;   // true: keys arrive without Enter
    private static BufferedReader keyReader;  // Windows: the keys, sent by a small PowerShell helper

    // Call once at the start.
    public static void setup(boolean plain, boolean enterMode) {
        colors = !plain && System.getenv("NO_COLOR") == null;
        // The blocks and lines only work if the console can show them: use the console's own encoding, never force one.
        try {
            String encoding = System.getProperty("stdout.encoding", System.getProperty("sun.stdout.encoding"));
            Charset out = encoding == null ? Charset.defaultCharset() : Charset.forName(encoding);
            ascii = plain || !out.newEncoder().canEncode('█');
        } catch (Exception e) {
            ascii = true;
        }
        boolean windows = System.getProperty("os.name").startsWith("Windows");
        if (colors && windows) {
            try {
                new ProcessBuilder("cmd", "/c", "").inheritIO().start().waitFor();   // switches colors on in old Windows consoles
            } catch (Exception e) {
                colors = false;
            }
        }
        if (!enterMode) {
            System.out.print("Starting...");
            instant = windows ? startWindowsKeys() : startUnixKeys();
        }
    }

    // ---------- drawing ----------

    // Starts a new screen.
    public static void clear() {
        frame.setLength(0);
        frame.append(colors ? "\033[H" : "\n" + rule(40) + "\n\n");   // cursor to the top left: no flicker
    }

    public static void println(String line) {
        frame.append(line).append(colors ? "\033[K" : "").append('\n');   // \033[K erases what was left of the old screen
    }

    public static void println() {
        println("");
    }

    private static void show() {
        if (frame.length() == 0) return;
        System.out.print(frame + (colors ? "\033[J" : ""));
        System.out.flush();
        frame.setLength(0);
    }

    // Colors the text (no effect without colors). Use the color constants above.
    public static String paint(String color, String text) {
        return colors ? "\033[" + color + "m" + text + "\033[0m" : text;
    }

    public static String dim(String text) {
        return paint(GRAY, text);
    }

    public static String heroColor(Hero h) {
        return new String[]{RED, CYAN, YELLOW}[h.ordinal()];   // Warrior, Mage, Rogue
    }

    public static String hpColor(int hp, int max) {
        return hp * 10 > max * 3 ? GREEN : RED;
    }

    // A bar like ████████░░░░ (or ########---- in ASCII)
    public static String bar(int value, int max, int width, String color) {
        int filled = max == 0 ? 0 : (int) ((long) width * value / max);
        if (value > 0 && filled == 0) filled = 1;
        return paint(color, (ascii ? "#" : "█").repeat(filled)) + dim((ascii ? "-" : "░").repeat(width - filled));
    }

    public static String rule(int width) {
        return (ascii ? "-" : "─").repeat(width);
    }

    // ---------- keys ----------

    // Shows the screen and waits for one key. Lower case. Enter is ENTER, arrow keys come back as w a s d,
    // keys we do not know come back as the character 0.
    public static char key() {
        show();
        try {
            if (!instant) return typedKey();
            if (keyReader != null) {
                String s = keyReader.readLine();
                if (s == null) {   // the helper stopped: go on in Enter mode
                    instant = false;
                    return typedKey();
                }
                return windowsKey(s.trim());
            }
            int c = System.in.read();
            if (c < 0) System.exit(0);
            if (c == ESC && System.in.available() >= 2) {   // arrow keys arrive as ESC [ A / B / C / D
                System.in.read();
                int dir = "ABCD".indexOf(System.in.read());
                return dir < 0 ? 0 : "wsda".charAt(dir);
            }
            return c == '\r' ? ENTER : Character.toLowerCase((char) c);
        } catch (IOException e) {
            System.exit(0);
            return 0;
        }
    }

    // Waits for any key.
    public static void pause(String message) {
        println(dim("  " + message));
        drain();
        key();
        drain();
    }

    // Forgets keys that were typed ahead (so a held key does not skip the next screen).
    public static void drain() {
        pending.clear();
        try {
            if (keyReader != null) while (keyReader.ready()) keyReader.readLine();
            else if (instant) while (System.in.available() > 0) System.in.read();
        } catch (IOException e) {
            // nothing to do
        }
    }

    // Enter mode: the player types keys and presses Enter. Every typed key is one key press.
    private static char typedKey() {
        while (pending.isEmpty()) {
            if (!IN.hasNextLine()) System.exit(0);
            for (char c : IN.nextLine().toLowerCase().toCharArray()) if (c != ' ') pending.add(c);
            pending.add(ENTER);
        }
        return pending.poll();
    }

    // ---------- instant keys: Mac / Linux ----------

    // Puts the terminal in "do not wait for Enter, do not show the typed key" mode, and back when the game ends.
    private static boolean startUnixKeys() {
        String saved = shell("stty -g < /dev/tty");
        if (saved == null || shell("stty -icanon -echo min 1 < /dev/tty") == null) return false;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shell("stty " + saved + " < /dev/tty")));
        return true;
    }

    // Runs a shell command. Returns its output, or null if it failed.
    private static String shell(String command) {
        try {
            Process p = new ProcessBuilder("sh", "-c", command).redirectErrorStream(true).start();
            String out = new String(p.getInputStream().readAllBytes()).trim();
            return p.waitFor() == 0 ? out : null;
        } catch (Exception e) {
            return null;
        }
    }

    // ---------- instant keys: Windows ----------

    // Java can not read single keys on Windows, so a small PowerShell program reads them and sends each key
    // as one line (the key's number, or its name for arrow keys). It stops by itself when the game is gone.
    private static boolean startWindowsKeys() {
        String script = "$p=" + ProcessHandle.current().pid() + ";"
                + "if([Console]::IsInputRedirected){[Console]::Out.WriteLine('NOKEYS');exit};"
                + "[Console]::Out.WriteLine('READY');$n=0;"
                + "while($true){if([Console]::KeyAvailable){$k=[Console]::ReadKey($true);"
                + "if([int]$k.KeyChar -ne 0){[Console]::Out.WriteLine([int]$k.KeyChar)}else{[Console]::Out.WriteLine($k.Key.ToString())}}"
                + "else{Start-Sleep -Milliseconds 15;$n++;if($n%100 -eq 0 -and -not(Get-Process -Id $p -ErrorAction SilentlyContinue)){exit}}}";
        try {
            Process helper = new ProcessBuilder("powershell", "-NoLogo", "-NoProfile", "-Command", script)
                    .redirectInput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.DISCARD).start();
            Runtime.getRuntime().addShutdownHook(new Thread(helper::destroyForcibly));
            BufferedReader reader = new BufferedReader(new InputStreamReader(helper.getInputStream()));
            for (int i = 0; i < 400 && !reader.ready() && helper.isAlive(); i++) Thread.sleep(25);   // wait up to 10 seconds
            if (reader.ready() && "READY".equals(reader.readLine())) {
                keyReader = reader;
                return true;
            }
            helper.destroyForcibly();
        } catch (Exception e) {
            // no PowerShell: Enter mode
        }
        return false;
    }

    private static char windowsKey(String s) {
        switch (s) {
            case "UpArrow": return 'w';
            case "DownArrow": return 's';
            case "RightArrow": return 'd';
            case "LeftArrow": return 'a';
        }
        try {
            int c = Integer.parseInt(s);
            return c == '\r' ? ENTER : Character.toLowerCase((char) c);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
