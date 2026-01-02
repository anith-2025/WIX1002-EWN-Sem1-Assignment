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
        while (choice < 0 || choice >= possibleMoves.size()) {
            System.out.print("Choose move (0-" + (possibleMoves.size()-1) + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
            }
        }

        return possibleMoves.get(choice);
    }
}