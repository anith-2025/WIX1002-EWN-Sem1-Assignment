public class Move {
    private int pieceNumber;
    private int fromPosition;
    private int toPosition;

    public Move(int piece, int from, int to) {
        this.pieceNumber = piece;
        this.fromPosition = from;
        this.toPosition = to;
    }

    public int getPieceNumber() { return pieceNumber; }
    public int getFromPosition() { return fromPosition; }
    public int getToPosition() { return toPosition; }

    @Override
    public String toString() {
        return "P" + pieceNumber + " " + fromPosition + "->" + toPosition;
    }
}
