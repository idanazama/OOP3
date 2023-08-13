package Project;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No folder path was given!");
        } else if (args.length != 1) {
            System.out.println("Too much parameters! only 1 for folder path.");
        } else {
            String path = args[0];
            GamePlayer g = new GamePlayer(path);
            g.playGame();
        }
    }
}