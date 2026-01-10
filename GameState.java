import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameState {
    private int[] positions;
    private int diceNumber;
    private int targetPiece;

    public GameState(int[] positions, int diceNumber, int targetPiece) {
        this.positions = (int[])positions.clone();
        this.diceNumber = diceNumber;
        this.targetPiece = targetPiece;
    }

    public List<Move> generatePossibleMoves() {
        // Added <Move> to remove the warning
        List<Move> moves = new ArrayList<>();
        List<Integer> movablePieces = this.findMovablePieces();
        Iterator<Integer> iterator = movablePieces.iterator();

        while(iterator.hasNext()) {
            int pieceNum = iterator.next();
            int currentPos = this.positions[pieceNum - 1];

            if (currentPos == -1) continue;

            // Special rule: If at position 11, can move to 0 (Victory)
            if (currentPos == 11) {
                moves.add(new Move(pieceNum, currentPos, 0));
            }

            int row = currentPos / 10;
            int col = currentPos % 10;

            for(int dr = -1; dr <= 1; ++dr) {
                for(int dc = -1; dc <= 1; ++dc) {
                    if (dr != 0 || dc != 0) {
                        int nr = row + dr;
                        int nc = col + dc;

                        if (nr >= 0 && nr < 10 && nc >= 0 && nc < 10) {
                            int nextPos = nr * 10 + nc;
                            // Constraint for invalid square
                            if (nextPos != 22) {
                                moves.add(new Move(pieceNum, currentPos, nextPos));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    private List<Integer> findMovablePieces() {
        // Added <Integer> to remove the warning
        List<Integer> pieces = new ArrayList<>();

        if (this.positions[this.diceNumber - 1] != -1) {
            pieces.add(this.diceNumber);
            return pieces;
        } else {
            int nextHigher = -1;
            for(int i = this.diceNumber; i < 6; ++i) {
                if (this.positions[i] != -1) {
                    nextHigher = i + 1;
                    break;
                }
            }

            int nextLower = -1;
            for(int i = this.diceNumber - 2; i >= 0; --i) {
                if (this.positions[i] != -1) {
                    nextLower = i + 1;
                    break;
                }
            }

            if (nextHigher != -1) pieces.add(nextHigher);
            if (nextLower != -1) pieces.add(nextLower);

            return pieces;
        }
    }

    public void applyMove(Move move) {
        int toPos = move.getToPosition();
        int pieceIdx = move.getPieceNumber() - 1;

        for(int i = 0; i < this.positions.length; ++i) {
            if (this.positions[i] == toPos) {
                this.positions[i] = -1; // Piece captured
            }
        }

        this.positions[pieceIdx] = toPos;
    }

    public boolean isWinning() {
        int targetPos = this.positions[this.targetPiece - 1];
        return targetPos == 0;
    }

    public int[] getPositions() {
        return this.positions;
    }

    public int getTargetPiece() {
        return this.targetPiece;
    }
}