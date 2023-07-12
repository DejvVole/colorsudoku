package sk.tuke.kpi.kp.game.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameField {
    @JsonIgnore
    private final int rowCount;
    @JsonIgnore
    private final int columnCount;
    private int difficulty;
    private int maxUnsolved = 51;

    private int score = 100;
    private int correctGuesses = 0;
    private final Tile [][] tiles;
    private int wrongRow, wrongColumn;
    private final int gridSize = 9;

    GameState gameState = GameState.PLAYING;

    public GameField(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        tiles = new Tile[rowCount][columnCount];
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setDifficultyAndGenerate(int difficulty) {
        this.difficulty = difficulty;
        this.maxUnsolved -= difficulty;

        generate();
    }

    public void generate(){
        /*
        * Naplnenie prveho riadku aby hra bola viac random
        * */

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < columnCount; i++) list.add(i);
        Collections.shuffle(list);

        for (int column = 0; column < columnCount; column++) {
            tiles[0][column] = new Solved(list.get(column), TileStatus.CORRECT_GUESS);
        }

        /*
        * Zvysok doplnim s 0 (aj kvoli nullpointer)
        * */

        for (int row = 1; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
               tiles[row][column] = new Solved(0, TileStatus.CORRECT_GUESS);
            }
        }
        solveSudoku(0, 0);
        addToGuessTiles();
    }

    public void addToGuessTiles(){
        Random random = new Random();

        while(maxUnsolved != 0) {
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    int randomNumber = random.nextInt(2);
                    var tile = getTile(row, column);
                    if (randomNumber == 1 && tile.getState() != TileStatus.TO_GUESS) {
                        if(maxUnsolved == 0) break;
                        maxUnsolved--;
                        tiles[row][column] = new Solvable(0, TileStatus.TO_GUESS);
                    }
                }
            }
        }
    }

    public boolean solveSudoku(int row, int col){

        /*
        * Ak sme dosiahli 8. riadok a 9 stlpec, vraciame true aby sme sa vyhli dalsiemu backtrackingu
        * */

        if (row == gridSize - 1 && col == gridSize) return true;

        /*
        * Kontrolujem ci hodnota stlpca je 9, ak ano ideme na dalsi riadok a stlpec znova 0
        * */

        if (col == gridSize) {
            row++;
            col = 0;
        }

        /*
        * Kontrolujem ci aktualna pozicia v poli ma hodnotu >0, ak ano idem(iterujem) na dalsi stlpec
        * */

        if (tiles[row][col].getValue() != 0) return solveSudoku(row, col + 1);

        for (int num = 1; num < 10; num++) {

            /*
            * Kontrolujem ci je bezpecne vlozit cislo, ak ano, vlozim ho a idem na dalsi stlpec
            * */

            if (isSafe(tiles, row, col, num)) {
                tiles[row][col].setValue(num);
                tiles[row][col].setState(TileStatus.CORRECT_GUESS);

                /*
                * Kontrola dalsej moznosti s dalsim stlpcom
                * */

                if (solveSudoku(row, col + 1)) return true;
            }

            /*
            * Ak som cislo vlozil zle tak ho odstranim (dam 0) a skusim dalsie
            * */

            tiles[row][col].setValue(0);
        }
        return false;
    }

    private boolean isSafe(Tile[][] grid, int row, int col, int num) {
        /*
        * Kotrola ci som nasiel rovnake cislo v riadku, stlpci a 3x3 stvorci, ak ano return false
        * */

        if (isSafeRow(grid, row, num)) return false;
        if (isSafeColumn(grid, col, num)) return false;
        int startRow = row - row % 3, startCol = col - col % 3;
        if (isSafeSquare(grid, num, startRow, startCol)) return false;

        return true;
    }

    private boolean isSafeSquare(Tile[][] grid, int num, int startRow, int startCol) {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol].getValue() == num) {
                    wrongRow = i + startRow;
                    wrongColumn = j + startCol;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSafeColumn(Tile[][] grid, int col, int num) {
        for (int rowCheck = 0; rowCheck <= 8; rowCheck++)
            if (grid[rowCheck][col].getValue() == num) {
                wrongRow = rowCheck;
                wrongColumn = col;
                return true;
            }
        return false;
    }

    private boolean isSafeRow(Tile[][] grid, int row, int num) {
        for (int columnCheck = 0; columnCheck <= 8; columnCheck++)
            if (grid[row][columnCheck].getValue() == num) {
                wrongRow = row;
                wrongColumn = columnCheck;
                return true;
            }
        return false;
    }

    public int getRowCount() {
        return rowCount;
    }
    public int getColumnCount() {
        return columnCount;
    }
    public Tile getTile(int row, int column){ return tiles[row][column]; }

    public Tile[][] getTiles() {
        return tiles;
    }
    public int getScore() { return score; }

    public void fillTile(int row, int column, int value){

        var tile = getTile(row, column);
        if(tile.getState() == TileStatus.TO_GUESS){
            if(isSafe(tiles, row, column, value)) {
                tile.setState(TileStatus.CORRECT_GUESS);
                //isSolved();
            }else {
                tile.setState(TileStatus.WRONG_GUESS);
                checkCollisions(tiles, row, column, value);
            }
            tile.setValue(value);
        }else System.err.println("This tile is not empty! Choose another one.");
    }

    public void randomFill(){
        Random random = new Random();

        int randomRow = random.nextInt(9);
        int randomColumn = random.nextInt(9);
        int randomValue = random.nextInt(9);

        var tile = getTile(randomRow, randomColumn);

        while(tile.getState() == TileStatus.CORRECT_GUESS){
                randomRow = random.nextInt(9);
                randomColumn = random.nextInt(9);
                tile = getTile(randomRow, randomColumn);
        }

        while(!isSafe(tiles, randomRow, randomColumn, randomValue)) {
            randomValue = random.nextInt(9);
        }

        tile.setState(TileStatus.CORRECT_GUESS);
        tile.setValue(randomValue);
    }

    private void checkCollisions(Tile[][] tiles, int row, int column, int value) {
        int startRow = row - row % 3, startCol = column - column % 3;


        if(isSafeRow(tiles, row, value)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.WRONG_GUESS);
        }

        if(isSafeColumn(tiles, column, value)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.WRONG_GUESS);
        }

        if(isSafeSquare(tiles, value, startRow, startCol)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.WRONG_GUESS);
        }
    }

    private void checkAfterDeleteCollisions(Tile[][] tiles, int row, int column, int value) {
        int startRow = row - row % 3, startCol = column - column % 3;


        if(isSafeRow(tiles, row, value)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.CORRECT_GUESS);
        }

        if(isSafeColumn(tiles, column, value)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.CORRECT_GUESS);
        }

        if(isSafeSquare(tiles, value, startRow, startCol)){
            var tile = getTile(wrongRow, wrongColumn);
            tile.setState(TileStatus.CORRECT_GUESS);
        }
    }

    public void deleteGuess(int row, int column){
        var tile = getTile(row, column);

        var previousValue = tile.getValue();

        if(tile instanceof Solvable){
            if(tile.getValue() < 1) System.err.println("You cannot delete empty tile!");
            tile.setState(TileStatus.TO_GUESS);
            score -= 5;
            tile.setValue(0);
            checkAfterDeleteCollisions(tiles, row, column, previousValue);

        } else System.err.println("You cannot delete this tile!");
    }



    public boolean isSolved(){
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var tile = getTile(row, column);
                if(tile.getState() == TileStatus.CORRECT_GUESS){
                    correctGuesses++;
                }
            }
        }
        if(correctGuesses == 81){
            gameState = GameState.SOLVED;
            return true;
        }else{
            correctGuesses = 0;
            return false;
        }
    }
}
