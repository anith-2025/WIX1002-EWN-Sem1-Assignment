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

        int index = random.nextInt(possibleMoves.size());
        return possibleMoves.get(index);
    }
}