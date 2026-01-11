import java.util.*;

public class RandomPlayer extends Player {
    private Random random;

    public RandomPlayer(String name) {
        super(name);
        this.random = new Random();
    }

    @Override
    public Move chooseMove(GameState state) {
        List<Move> possibleMoves = state.generatePossibleMoves();

        if (possibleMoves.isEmpty()) {
            return null;
        }

        List<Move> validMoves = new ArrayList<>();
        int targetPos = state.getPositions()[state.getTargetPiece() - 1];

        for (Move move : possibleMoves) {
            if (targetPos == -1 || move.getToPosition() != targetPos) {
                validMoves.add(move);
            }
        }

        if (validMoves.isEmpty()) {
            System.out.println("No valid moves available (all would capture target).");
            return null;
        }

        int index = random.nextInt(validMoves.size());
        return validMoves.get(index);
    }
}