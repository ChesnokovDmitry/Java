package task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;   //ширина игрового поля
    public Tile[][] gameTiles;
    public int score;                           //текущий счет
    public int maxTile;                         //максимальный вес плитки на игровом поле
    private int method = 0;
    private Model model;
    private View view;
    private Stack<Tile[][]> previousStates = new Stack<>();     //предыдущие состояния игрового поля
    private Stack<Integer> previousScores = new Stack<>();      //предыдущие счета
    private boolean isSaveNeeded = true;

    public Model() {
        this.score = 0;
        this.maxTile = 0;
        resetGameTiles();
    }


    //выбор лучшего из возможных ходов
    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency(this::right));
        priorityQueue.offer(getMoveEfficiency(this::up));
        priorityQueue.offer(getMoveEfficiency(this::down));

        priorityQueue.peek().getMove().move();
    }


    //сравнение веса плиток
    public boolean hasBoardChanged() {
        boolean tileValue = false;

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value != previousStates.peek()[i][j].value)
                    tileValue = true;
            }
        }

        return tileValue;
    }


    //эффективность переданного хода
    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);

        if (!hasBoardChanged())
            return new MoveEfficiency(-1, 0, move);

        rollback();

        return moveEfficiency;
    }


    //случайный ход (клавиша R)
    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;

        switch (n) {
            case 0: left(); break;
            case 1: right(); break;
            case 2: up(); break;
            case 3: down(); break;
        }
    }


    //сохранение текущего игрового состояния
    private void saveState(Tile[][] gameTiles) {
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                copyGameTiles[i][j] = new Tile(gameTiles[i][j].value);
            }
        }

        previousStates.push(copyGameTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }


    //устанавливаем текущее игровое состояние равным последнему находящемуся в стеках (клавиша Z)
    public void rollback() {
        if (!previousScores.isEmpty() && !previousStates.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }


    //перемещение элементов массива gameTiles влево и добавление плитки с помощью метода addTile, если это необходимо
    public void left() {
        if (isSaveNeeded)
            saveState(gameTiles);

        method = 0;
        boolean b = false;

        for (Tile[] gameTile : gameTiles) {
            if (compressTiles(gameTile) | mergeTiles(gameTile)) {
                b = true;
            }
        }

        if (b) {
            addTile();
        }

        isSaveNeeded = true;
    }


    //направо
    public void right() {
        saveState(gameTiles);
        method = 1;
        boolean b = false;

        for (Tile[] gameTile : gameTiles) {
            if (compressTiles(gameTile) | mergeTiles(gameTile)) {
                b = true;
            }
        }

        if (b) {
            addTile();
        }
    }


    //вниз
    public void down() {
        saveState(gameTiles);
        method = 0;
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            copyGameTiles[i] = Arrays.copyOf(gameTiles[i], FIELD_WIDTH);
        }

        compressTilesDownUp();
        mergeTilesDownUp();

        if (!Arrays.deepEquals(copyGameTiles, gameTiles)) {
            addTile();
        }
    }


    //вверх
    public void up() {
        saveState(gameTiles);
        method = 1;
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            copyGameTiles[i] = Arrays.copyOf(gameTiles[i], FIELD_WIDTH);
        }

        compressTilesDownUp();
        mergeTilesDownUp();

        if (!Arrays.deepEquals(copyGameTiles, gameTiles)) {
            addTile();
        }
    }


    //сжатие плиток вниз / вверх
    private boolean compressTilesDownUp() {
        boolean b = false;
        int count = 3;
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            copyGameTiles[i] = Arrays.copyOf(gameTiles[i], FIELD_WIDTH);
        }

        gameTiles = turnClockwise();

        for (Tile[] tiles : gameTiles) {
            compressTiles(tiles);
        }

        while (count > 0) {
            gameTiles = turnClockwise();
            count--;
        }

        if (!Arrays.equals(copyGameTiles, gameTiles)) {
            b = true;
        }

        return b;
    }


    //поворот матрицы по часовой стрелке
    private Tile[][] turnClockwise() {
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < copyGameTiles.length; i++) {
            for (int j = 0; j < copyGameTiles.length; j++) {
                copyGameTiles[i][j] = gameTiles[gameTiles.length - j - 1][i];
            }
        }

        gameTiles = copyGameTiles;

        return gameTiles;
    }


    //слияние плиток одного номинала вниз / вверх
    private boolean mergeTilesDownUp() {
        boolean b = false;
        Tile[][] copyGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            copyGameTiles[i] = Arrays.copyOf(gameTiles[i], FIELD_WIDTH);
        }

        if (this.method == 0) {
            for (int i = gameTiles.length - 1; i > -1; i--) {
                for (int j = 0; j < gameTiles.length; j++) {
                    if (!gameTiles[i][j].isEmpty()) {
                        if (i - 1 > -1) {
                            if (gameTiles[i][j].value == gameTiles[i - 1][j].value) {
                                gameTiles[i][j] = new Tile(gameTiles[i][j].value + gameTiles[i - 1][j].value);
                                gameTiles[i - 1][j] = new Tile();

                                if (gameTiles[i][j].value > maxTile)
                                    maxTile = gameTiles[i][j].value;

                                score += gameTiles[i][j].value;
                            }
                        }
                    }
                }

                compressTilesDownUp();
            }
        } else if (method == 1) {
            for (int i = 0; i < gameTiles.length; i++) {
                for (int j = 0; j < gameTiles.length; j++) {
                    if (!gameTiles[i][j].isEmpty()) {
                        if (i + 1 < gameTiles.length) {
                            if (gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                                gameTiles[i][j] = new Tile(gameTiles[i][j].value + gameTiles[i + 1][j].value);
                                gameTiles[i + 1][j] = new Tile();

                                if (gameTiles[i][j].value > maxTile)
                                    maxTile = gameTiles[i][j].value;

                                score += gameTiles[i][j].value;
                            }
                        }
                    }
                }
            }

            compressTilesDownUp();
        }

        if (!Arrays.equals(copyGameTiles, gameTiles)) {
            b = true;
        }

        return b;
    }


    //сжатие плиток влево / вправо
    private boolean compressTiles(Tile[] tiles) {
        boolean b = false;

        if (method == 0) {
            for (int j = 0; j < tiles.length; j++) {
                int count = 0;
                for (int i = j; i < tiles.length; i++) {
                    if (tiles[i].isEmpty()) {
                        count++;
                    } else {
                        if (count == 0) break;
                        tiles[i - count] = tiles[i];
                        tiles[i] = new Tile();
                        b = true;
                        break;
                    }
                }
            }
        } else if (method == 1) {
            for (int j = tiles.length - 1; j > -1; j--) {
                int count = 0;
                for (int i = j; i > -1; i--) {
                    if (tiles[i].isEmpty()) {
                        count++;
                    } else {
                        if (count == 0) break;
                        tiles[i + count] = tiles[i];
                        tiles[i] = new Tile();
                        b = true;
                        break;
                    }
                }
            }
        }

        return b;
    }


    //слияние плиток одного номинала вправо / влево
    private boolean mergeTiles(Tile[] tiles) {
        boolean b = false;

        if (method == 0) {
            for (int i = 0; i < tiles.length; i += 1) {
                if (i + 1 < tiles.length) {
                    if (!tiles[i].isEmpty()) {
                        if (tiles[i].value == tiles[i + 1].value) {
                            tiles[i] = new Tile(tiles[i].value + tiles[i + 1].value);
                            tiles[i + 1] = new Tile();

                            if (tiles[i].value > maxTile)
                                maxTile = tiles[i].value;

                            score += tiles[i].value;
                            b = true;
                        }
                    }
                }

                compressTiles(tiles);
            }
        } else if (method == 1) {
            for (int i = tiles.length - 1; i > -1; i -= 1) {
                if (i - 1 > -1) {
                    if (!tiles[i].isEmpty()) {
                        if (tiles[i].value == tiles[i - 1].value) {
                            tiles[i] = new Tile(tiles[i].value + tiles[i - 1].value);
                            tiles[i - 1] = new Tile();

                            if (tiles[i].value > maxTile)
                                maxTile = tiles[i].value;

                            score += tiles[i].value;
                            b = true;
                        }
                    }
                }

                compressTiles(tiles);
            }
        }

        return b;
    }


    //изменяем значение случайной пустой плитки в массиве gameTiles на 2 или 4
    private void addTile() {
        if (!getEmptyTiles().isEmpty()) {
            List<Tile> emptyTile = getEmptyTiles();

            //получаем случайный объект из списка (размерСписка * случайноеЧислоОтНуляДоЕдиницы)
            Tile tile = emptyTile.get((int) (emptyTile.size() * Math.random()));

            //вычисляем вес новой плитки
            tile.setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }


    //получение свободных плиток
    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTile = new ArrayList<>();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    emptyTile.add(gameTiles[i][j]);
                }
            }
        }

        return emptyTile;
    }


    //заполняем массив gameTiles новыми плитками
    public void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }

        addTile();
        addTile();
    }


    //проверка текущего состояния игрового поля
    public boolean canMove() {
        boolean b = false;

        //проверка на наличие пустых плиток или одинаковых значений
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                if (!getEmptyTiles().isEmpty()) {
                    b = true;
                    break;
                } else if (i + 1 < gameTiles.length && gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                        b = true;
                        break;
                } else if (j + 1 < gameTiles.length && gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                        b = true;
                        break;
                }
            }

            if (b) {
                break;
            }
        }

        return b;
    }


    public Tile[][] getGameTiles() {
        return gameTiles;
    }
}
