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
            File file = new File("moves.txt");
            System.out.println("[DEBUG] Creating moves file at: " + file.getAbsolutePath());
            PrintWriter writer = new PrintWriter(file);

            writer.println(playerName);

            // FIXED: Add spaces between dice numbers
            for (int i = 0; i < diceSequence.length; i++) {
                writer.print(diceSequence[i]);
                if (i < diceSequence.length - 1) {
                    writer.print(" ");
                }
            }
            writer.println();

            writer.println(targetPiece);

            // FIXED: Add spaces between positions
            for (int i = 0; i < 6; i++) {
                writer.print(initialPositions[i]);
                if (i < 5)
                    writer.print(" ");
            }
            writer.println();

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to moves.txt");
            e.printStackTrace();
        }
    }

    public int getTargetPiece() {
        return targetPiece;
    }

    public int[] getInitialPositions() {
        return initialPositions.clone();
    }

    public int[] getDiceSequence() {
        return diceSequence.clone();
    }
}