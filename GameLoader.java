import java.io.*;
import java.util.*;

public class GameLoader {
    private int targetPiece;
    private int[] initialPositions;
    private int[] diceSequence;

    public GameLoader(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            targetPiece = scanner.nextInt();

            initialPositions = new int[6];
            for (int i = 0; i < 6; i++) {
                initialPositions[i] = scanner.nextInt();
            }

            diceSequence = new int[30];
            for (int i = 0; i < 30; i++) {
                diceSequence[i] = scanner.nextInt();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + filename + " not found!");
        }
    }

    public void printGameDetails(String playerName) {
        try {
            PrintWriter writer = new PrintWriter("moves.txt");

            writer.println(playerName);

            for (int dice : diceSequence) {
                writer.print(dice);
            }
            writer.println();

            writer.println(targetPiece);

            for (int i = 0; i < 6; i++) {
                writer.print(initialPositions[i]);
                if (i < 5) writer.print(" ");
            }
            writer.println();

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to moves.txt");
        }
    }

    public int getTargetPiece() { return targetPiece; }
    public int[] getInitialPositions() { return initialPositions.clone(); }
    public int[] getDiceSequence() { return diceSequence.clone(); }
}