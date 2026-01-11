import java.util.*;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Move chooseMove(GameState state) {
        List<Move> possibleMoves = state.generatePossibleMoves();

        if (possibleMoves.isEmpty()) {
            System.out.println("No possible moves available!");
            return null;
        }

        System.out.println("\nAvailable moves:");
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println(i + ": " + possibleMoves.get(i));
        }

        int choice = -1;
        boolean validMove = false;

        while (!validMove) {
            System.out.print("Choose move (0-" + (possibleMoves.size()-1) + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 0 && choice < possibleMoves.size()) {
                    Move selectedMove = possibleMoves.get(choice);

                    // Check if this move would capture the target piece
                    int targetPos = state.getPositions()[state.getTargetPiece() - 1];
                    int toPos = selectedMove.getToPosition();

                    // If target piece exists and move would capture it, invalid
                    if (targetPos != -1 && toPos == targetPos) {
                        System.out.println("Invalid move! You cannot capture the target piece (P" +
                                state.getTargetPiece() + " at position " + targetPos + ").");
                        System.out.println("Please choose another move.");
                    } else {
                        validMove = true;
                    }
                } else {
                    System.out.println("Please enter a number between 0 and " + (possibleMoves.size()-1));
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }

        return possibleMoves.get(choice);
    }
}