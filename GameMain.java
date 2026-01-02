import java.io.*;
import java.util.*;

public class GameMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== EWN Puzzle Solver ===");

        System.out.println("\nChoose player type:");
        System.out.println("1. Human Player");
        System.out.println("2. Random Player");
        System.out.println("3. AI Player");
        System.out.print("Choice (1-3): ");

        int playerType = scanner.nextInt();
        scanner.nextLine();

        String playerName;
        Player player;

        switch (playerType) {
            case 1:
                System.out.print("Enter your name: ");
                playerName = scanner.nextLine();
                player = new HumanPlayer(playerName);
                break;
            case 2:
                playerName = "Random Player";
                player = new RandomPlayer(playerName);
                break;
            case 3:
                playerName = "AI Player";
                player = new AIPlayer(playerName);
                break;
            default:
                System.out.println("Invalid choice, using Random Player");
                playerName = "Random Player";
                player = new RandomPlayer(playerName);
        }

        System.out.print("\nChoose level (1-4): ");
        int level = scanner.nextInt();
        String filename = "TestCases/level" + level + ".txt";

        GameLoader loader = new GameLoader(filename);

        try {
            PrintWriter clear = new PrintWriter("moves.txt");
            clear.close();
        } catch (Exception e) {}

        loader.printGameDetails(playerName);

        int[] positions = loader.getInitialPositions().clone();
        int targetPiece = loader.getTargetPiece();
        int[] diceSequence = loader.getDiceSequence();

        System.out.println("\n=== GAME START ===");
        System.out.println("Target: Piece " + targetPiece + " must reach square 0");
        System.out.println("Max turns: " + diceSequence.length);
        System.out.println("Initial positions:");
        for (int i = 0; i < positions.length; i++) {
            System.out.println("  P" + (i+1) + ": " + positions[i]);
        }

        boolean won = false;
        for (int turn = 0; turn < diceSequence.length; turn++) {
            System.out.println("\n=== Turn " + (turn + 1) + " ===");
            System.out.println("Dice: " + diceSequence[turn]);

            GameState state = new GameState(positions, diceSequence[turn], targetPiece);

            Move move = player.chooseMove(state);
            if (move == null) {
                System.out.println("No valid move available!");
                break;
            }

            state.applyMove(move);
            positions = state.getPositions();

            player.printMove(positions);

            if (state.isWinning()) {
                System.out.println("\n*** VICTORY! ***");
                System.out.println("Solved in " + (turn + 1) + " moves!");
                won = true;
                break;
            }

            if (turn == 29) {
                System.out.println("Last turn reached!");
            }
        }

        if (!won) {
            System.out.println("\n*** GAME OVER - Target not reached ***");
        }

        System.out.println("\nGame saved to moves.txt");
        System.out.println("Run: java -jar EWN_GUI.jar to visualize");

        scanner.close();
    }
}