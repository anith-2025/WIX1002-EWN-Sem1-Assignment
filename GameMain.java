import java.util.Scanner;

public class GameMain {
    private static int[] staticDiceSequence;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== EWN Puzzle Solver ===");

        // 1. Prompt for Mode and create Player object
        System.out.println("\nChoose player type:\n1. Human\n2. Random\n3. AI");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left over from nextInt()

        Player player;
        String name;

        switch (choice) {
            case 1:
                // 2. Prompt for Human Name
                System.out.print("Enter Human Player name: ");
                name = scanner.nextLine();
                player = new HumanPlayer(name);
                break;
            case 2:
                // Default Name for Random
                player = new RandomPlayer("Random Player");
                break;
            case 3:
                // Default Name for AI
                player = new AIPlayer("AI Player");
                break;
            default:
                throw new IllegalArgumentException("Invalid player option: " + choice);
        }

        // 3. Prompt for Level
        System.out.print("Choose level (1-4): ");
        int level = scanner.nextInt();
        String filename = "TestCases/level" + level + ".txt";

        // Set move limits based on level
        int moveLimit = 30;
        switch (level) {
            case 1: moveLimit = 6; break;
            case 2: moveLimit = 10; break;
            case 3: moveLimit = 10; break;
            case 4: moveLimit = 15; break;
        }

        if (player instanceof AIPlayer) {
            ((AIPlayer) player).setMaxDepth(moveLimit);
        }

        // Load game data
        GameLoader loader = new GameLoader(filename);
        staticDiceSequence = loader.getDiceSequence();
        int[] positions = loader.getInitialPositions();
        int targetPiece = loader.getTargetPiece();

        loader.printGameDetails(player.getName());

        System.out.println("\n=== GAME START ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Move Limit: " + moveLimit);

        boolean solved = false;
        int turnsTaken = 0;

        for (int turn = 0; turn < staticDiceSequence.length; turn++) {
            turnsTaken = turn + 1;
            GameState state = new GameState(positions, staticDiceSequence[turn], targetPiece);

            // 4. Call chooseMove function
            Move move = player.chooseMove(state);

            if (move == null) {
                System.out.println("No valid moves available.");
                break;
            }

            state.applyMove(move);
            positions = state.getPositions();
            player.printMove(positions);

            if (state.isWinning()) {
                solved = true;
                break;
            }
        }

        // 5. Show result of the game
        System.out.println("\n=== GAME OVER ===");
        if (solved) {
            System.out.println("RESULT: Puzzle SOLVED in " + turnsTaken + " moves!");
        } else {
            System.out.println("RESULT: Puzzle NOT solved. Better luck next time!");
        }

        scanner.close();
    }

    public static int getDiceAtTurn(int turn) {
        return staticDiceSequence != null && turn < staticDiceSequence.length ? staticDiceSequence[turn] : -1;
    }
}