import java.io.*;

public abstract class Player {
    protected String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void printMove(int[] piecePositions) {
        try {
            FileWriter fw = new FileWriter("moves.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < 6; i++) {
                pw.print(piecePositions[i]);
                if (i < 5)
                    pw.print(" ");
            }
            pw.println();

            pw.close();
        } catch (IOException ex) {
            System.out.println("Error writing to moves.txt");
            ex.printStackTrace();
        }
    }

    public abstract Move chooseMove(GameState state);
}