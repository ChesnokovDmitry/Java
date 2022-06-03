package Lesson4;

public class ProgressCheck {
    private final Field field;

    public ProgressCheck(Field field) {
        this.field = field;
    }

    public boolean isLimitValue(int line, int column) {
        return  line <= 0 || column <= 0 || line > field.getWidth() || column > field.getHeight();
    }

    public boolean isFree(int line, int column) {
        return field.getField()[line - 1][column - 1].equals("*");
    }

    public void symbolReplacement(int line, int column, String symbol) {
        field.getField()[line - 1][column - 1] = symbol;
        field.fieldDrawing();
    }

    public boolean isWinToLine(int line, String symbol) {
        StringBuilder builder = new StringBuilder();
        String[] lineArray = field.getField()[line - 1];

        for (String s : lineArray) {
            builder.append(s);
        }

        return  builder.toString().equals(gameOver(symbol));
    }

    public boolean isWinToColumn(int column, String symbol) {
        StringBuilder builder = new StringBuilder();
        String[][] columnArray = field.getField();

        for (String[] strings : columnArray) {
            for (int j = 0; j < columnArray.length; j++) {
                if (j == column - 1)
                    builder.append(strings[j]);
            }
        }

        return  builder.toString().equals(gameOver(symbol));
    }

    public boolean isWinToDiagonalRight(String symbol) {
        StringBuilder builder = new StringBuilder();
        String[][] columnArray = field.getField();

        for (int i = 0; i < columnArray.length; i++) {
            for (int j = 0; j < columnArray.length; j++) {
                if (i == j) {
                    builder.append(columnArray[i][j]);
                }
            }
        }

        return  builder.toString().equals(gameOver(symbol));
    }

    public boolean isWinToDiagonalLeft(String symbol) {
        StringBuilder builder = new StringBuilder();
        String[][] columnArray = field.getField();

        for (int i = 0; i < columnArray.length; i++) {
            for (int j = 0; j < columnArray.length; j++) {
                if (i == columnArray.length - 1 - j && j == columnArray.length - 1 - i) {
                    builder.append(columnArray[i][j]);
                }
            }
        }

        return  builder.toString().equals(gameOver(symbol));
    }

    public boolean isStandoff() {
        int count = 0;

        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField().length; j++) {
                if (!field.getField()[i][j].equals("*")) {
                    count++;
                }
            }
        }

        return count == field.getHeight() * field.getWidth();
    }

    public String gameOver(String symbol) {
        StringBuilder builder = new StringBuilder();
        int count = field.getField().length;

        while (count > 0) {
            builder.append(symbol);
            count--;
        }

        return builder.toString();
    }

    public boolean isGameOver() {
        return true;
    }

    public boolean isMove() {
        return true;
    }

    public Field getField() {
        return field;
    }
}
