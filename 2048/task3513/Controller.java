package task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private static final int WINNING_TILE = 2048;
    private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            resetGame();
        } else if (!model.canMove()) {
            view.isGameLost = true;                             //если ход невозможен
        } else if (!view.isGameLost && !view.isGameWon) {
            if (KeyEvent.VK_LEFT == e.getKeyCode()) {
                model.left();
            } else if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
                model.right();
            } else if (KeyEvent.VK_UP == e.getKeyCode()) {
                model.up();
            } else if (KeyEvent.VK_DOWN == e.getKeyCode()) {
                model.down();
            }
        }
        if (KeyEvent.VK_Z == e.getKeyCode())
            model.rollback();
        if (KeyEvent.VK_R == e.getKeyCode())
            model.randomMove();
        if (model.maxTile == WINNING_TILE)
            view.isGameWon = true;
        if (KeyEvent.VK_A == e.getKeyCode())
            model.autoMove();

        view.repaint();
    }

    public void resetGame() {
        model.score = 0;
        model.maxTile = 0;
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return this.model.getGameTiles();
    }

    public int getScore() {
        return this.model.score;
    }

    public View getView() {
        return view;
    }
}
