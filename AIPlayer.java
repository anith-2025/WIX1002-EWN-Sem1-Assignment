import java.util.*;

public class AIPlayer extends Player {
    private List<Move> precalculatedMoves = null;
    private int currentTurn = 0;
    private int maxDepth = 30;

    public AIPlayer(String name) {
        super(name);
    }

    public void setMaxDepth(int depth) {
        this.maxDepth = depth;
    }

    // Clears the memory so the next level doesn't use old moves
    public void reset() {
        this.precalculatedMoves = null;
        this.currentTurn = 0;
    }

    @Override
    public Move chooseMove(GameState currentState) {
        if (precalculatedMoves == null) {
            precalculatedMoves = solveAStar(currentState);
        }

        if (precalculatedMoves != null && currentTurn < precalculatedMoves.size()) {
            return precalculatedMoves.get(currentTurn++);
        }
        return null;
    }

    private List<Move> solveAStar(GameState startState) {
        // PriorityQueue sorts by: (Moves Taken + Distance Remaining)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> (n.moves.size() + n.h)));

        pq.add(new Node(startState, new ArrayList<>(), calculateH(startState)));
        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.state.isWinning()) return current.moves;

            int turnIndex = current.moves.size();
            if (turnIndex >= maxDepth) continue;

            int nextDice = GameMain.getDiceAtTurn(turnIndex);
            if (nextDice == -1) continue;

            GameState simState = new GameState(current.state.getPositions(), nextDice, current.state.getTargetPiece());

            for (Move m : simState.generatePossibleMoves()) {
                GameState nextState = new GameState(current.state.getPositions(), nextDice, current.state.getTargetPiece());
                nextState.applyMove(m);

                // Include turn index in key to handle changing dice sequence
                String key = Arrays.toString(nextState.getPositions()) + "T" + turnIndex;

                if (visited.add(key)) {
                    List<Move> nextMoves = new ArrayList<>(current.moves);
                    nextMoves.add(m);
                    pq.add(new Node(nextState, nextMoves, calculateH(nextState)));
                }
            }
        }
        return null;
    }

    private int calculateH(GameState state) {
        int targetPos = state.getPositions()[state.getTargetPiece() - 1];
        if (targetPos == -1) return 999;
        if (targetPos == 0) return 0;

        // Chebyshev distance (Diagonal steps to 0)
        return Math.max(targetPos / 10, targetPos % 10);
    }

    private static class Node {
        GameState state;
        List<Move> moves;
        int h;

        Node(GameState s, List<Move> m, int h) {
            this.state = s;
            this.moves = m;
            this.h = h;
        }
    }
}