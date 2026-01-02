import java.util.*;

public class GameState {
    private int[] positions;
    private int diceNumber;
    private int targetPiece;

    public GameState(int[] positions, int diceNumber, int targetPiece) {
        this.positions = positions.clone();
        this.diceNumber = diceNumber;
        this.targetPiece = targetPiece;
    }

    public List<Move> generatePossibleMoves() {
        List<Move> moves = new ArrayList<>();

        int pieceToMove = findMovablePiece();
        if (pieceToMove == -1) return moves;

        int currentPos = positions[pieceToMove - 1];
        if (currentPos == -1) return moves;

        int row = currentPos / 10;
        int col = currentPos % 10;

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                int newPos = newRow * 10 + newCol;

                if (newPos != 22) {
                    moves.add(new Move(pieceToMove, currentPos, newPos));
                }
            }
        }

        return moves;
    }

    private int findMovablePiece() {
        if (positions[diceNumber - 1] != -1) {
            return diceNumber;
        }

        int smallestBigger = 7;
        int largestSmaller = 0;

        for (int i = 0; i < 6; i++) {
            if (positions[i] != -1) {
                int pieceNum = i + 1;

                if (pieceNum > diceNumber && pieceNum < smallestBigger) {
                    smallestBigger = pieceNum;
                }

                if (pieceNum < diceNumber && pieceNum > largestSmaller) {
                    largestSmaller = pieceNum;
                }
            }
        }

        if (smallestBigger != 7) return smallestBigger;
        if (largestSmaller != 0) return largestSmaller;
        return -1;
    }

    public void applyMove(Move move) {
        int pieceIndex = move.getPieceNumber() - 1;
        int toPos = move.getToPosition();

        boolean captured = false;
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == toPos && i != pieceIndex) {
                positions[i] = -1;
                captured = true;
                System.out.println("Captured piece " + (i+1) + " at " + toPos);
                break;
            }
        }

        positions[pieceIndex] = toPos;
    }

    public boolean isWinning() {
        return positions[targetPiece - 1] == 0;
    }

    public int[] getPositions() { return positions.clone(); }
    public int getDiceNumber() { return diceNumber; }
    public int getTargetPiece() { return targetPiece; }
}