package Lesson4;

public class Field {
    private final int width;
    private final int height;
    private final String[][] field;

    public Field(int sizeField) {
        this.width = sizeField;
        this.height = sizeField;
        this.field = new String[sizeField][sizeField];
        fieldFilling();
    }

    public void fieldFilling() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = "*";
                System.out.print(field[i][j]);
            }

            System.out.println();
        }
    }

    public void fieldDrawing() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(field[i][j]);
            }

            System.out.println();
        }

        System.out.println("---");
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String[][] getField() {
        return this.field;
    }
}
