package Lesson4;

import java.util.Scanner;

public class Player {
    private final ProgressCheck progressCheck;
    private boolean gameOver = false;
    private boolean move = false;

    public Player(ProgressCheck progressCheck) {
        this.progressCheck = progressCheck;
    }

    public void move() {
        String symbol = "X";
        System.out.println("Ваш ход. Выберите ячейку:");
        Scanner scanner = new Scanner(System.in);
        int line = Integer.parseInt(scanner.nextLine());
        int column = Integer.parseInt(scanner.nextLine());

        if (progressCheck.isLimitValue(line, column)) {
            System.out.println("Вы ввели не правильное значение.");
        } else {
            if (progressCheck.isFree(line, column)) {
                progressCheck.symbolReplacement(line, column, symbol);
                move = progressCheck.isMove();

                if (progressCheck.isWinToLine(line, symbol) || progressCheck.isWinToColumn(column, symbol) ||
                        progressCheck.isWinToDiagonalRight(symbol) || progressCheck.isWinToDiagonalLeft(symbol)) {
                    System.out.println("Кожанный победил:)");
                    gameOver = progressCheck.isGameOver();
                } else if (progressCheck.isStandoff()) {
                    System.out.println("Ничья");
                    gameOver = progressCheck.isGameOver();
                }
            } else {
                System.out.println("Вы ввели не правильное значение.");
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
}
