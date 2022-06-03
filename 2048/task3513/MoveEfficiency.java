package task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;     //количество пустых плиток
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        int n = Integer.compare(numberOfEmptyTiles, o.numberOfEmptyTiles);
        int s = Integer.compare(score, o.score);

        if (n > 0) return 1;
        else if (n < 0) return -1;
        else {
            if (s > 0) return 1;
            else if (s < 0) return -1;
            else return 0;
        }
    }
}
