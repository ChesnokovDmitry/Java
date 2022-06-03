package Lesson4;

public class HomeWorkLesson4 {
    public static void main(String[] args) {
        Field field = new Field(3);
        ProgressCheck progressCheck = new ProgressCheck(field);
        Player player = new Player(progressCheck);
        Ai ai = new Ai(progressCheck);

        while (true) {
            player.move();

            if (player.isGameOver())
                break;

            if (player.isMove()) {
                ai.move();
                player.setMove(false);
                ai.setMove(false);
            }

            if (ai.isGameOver())
                break;
        }
    }
}