package Lesson4;

import java.util.Random;

public class Ai {
    private final ProgressCheck progressCheck;
    private boolean gameOver = false;
    private boolean move = false;

    public Ai(ProgressCheck progressCheck) {
        this.progressCheck = progressCheck;
    }

    public void move() {
        Random random = new Random();
        String symbol = "0";

        while (!move) {
            int line = random.nextInt(progressCheck.getField().getWidth()) + 1;
            int column = random.nextInt(progressCheck.getField().getHeight()) + 1;

            if (progressCheck.isFree(line, column)) {
                progressCheck.symbolReplacement(line, column, symbol);
                move = progressCheck.isMove();
                System.out.println("ИИ походил.");

                if (progressCheck.isWinToLine(line, symbol) || progressCheck.isWinToColumn(column, symbol) ||
                        progressCheck.isWinToDiagonalRight(symbol) || progressCheck.isWinToDiagonalLeft(symbol)) {
                    System.out.println("ИИ победил. Судный день начался!");
                    gameOver = progressCheck.isGameOver();
                }
            } else if (progressCheck.isStandoff()) {
                System.out.println("Ничья");
                gameOver = progressCheck.isGameOver();
            }
        }
    }

    public void minimax() {

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
}
