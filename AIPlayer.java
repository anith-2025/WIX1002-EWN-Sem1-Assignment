import java.util.*;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public Move chooseMove(GameState currentState) {
        System.out.println("[AI] Thinking...");

        int targetPiece = currentState.getTargetPiece();
        List<Move> possibleMoves = currentState.generatePossibleMoves();

        if (possibleMoves.isEmpty()) {
            return null;
        }

        System.out.println("[AI] Target is piece " + targetPiece);

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : possibleMoves) {
            int score = evaluateMove(move, currentState);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        if (bestMove != null) {
            System.out.println("[AI] Selected: " + bestMove + " (score: " + bestScore + ")");
        }

        return bestMove;
    }

    private int evaluateMove(Move move, GameState state) {
        int score = 0;
        int targetPiece = state.getTargetPiece();
        int[] positions = state.getPositions();

        int movingPiece = move.getPieceNumber();
        int toPos = move.getToPosition();

        // CRITICAL FIX: Prioritize moving the TARGET piece
        if (movingPiece == targetPiece) {
            score += 200;  // Big bonus for moving target

            int currentPos = positions[targetPiece - 1];
            int currentDist = distanceToZero(currentPos);
            int newDist = distanceToZero(toPos);

            if (newDist < currentDist) {
                score += 100;  // Bonus for moving closer to 0
            }

            if (toPos == 0) {
                score += 10000;  // HUGE bonus for winning!
            }
        } else {
            score -= 50;  // Penalty for moving non-target
        }

        // Check if this move captures another piece
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == toPos && (i + 1) != movingPiece) {
                int capturedPiece = i + 1;

                if (capturedPiece == targetPiece) {
                    score -= 1000;  // VERY BAD to capture target!
                } else {
                    score += 30;  // Good to capture non-target
                }
                break;
            }
        }

        // Bonus for clearing path to 0
        if (clearsPathForTarget(move, positions, targetPiece)) {
            score += 40;
        }

        return score;
    }

    private boolean clearsPathForTarget(Move move, int[] positions, int targetPiece) {
        int targetPos = positions[targetPiece - 1];
        if (targetPos == -1) return false;

        int movedFrom = move.getFromPosition();

        // Check if moved piece was between target and square 0
        return isBetween(targetPos, movedFrom, 0);
    }

    private boolean isBetween(int a, int b, int c) {
        int aRow = a / 10, aCol = a % 10;
        int bRow = b / 10, bCol = b % 10;
        int cRow = c / 10, cCol = c % 10;

        if (aRow == cRow && bRow == aRow) {
            return (aCol < bCol && bCol < cCol) || (cCol < bCol && bCol < aCol);
        }
        if (aCol == cCol && bCol == aCol) {
            return (aRow < bRow && bRow < cRow) || (cRow < bRow && bRow < aRow);
        }
        return false;
    }

    private int distanceToZero(int position) {
        if (position == -1) return 999;
        int row = position / 10;
        int col = position % 10;
        return row + col;
    }
}