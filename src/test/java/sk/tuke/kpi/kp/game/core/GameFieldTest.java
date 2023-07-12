package sk.tuke.kpi.kp.game.core;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



class GameFieldTest {
    @Test
    public void isSolvedShouldReturnTrueWhenGameIsSolved(){
        GameField gameField = new GameField(9,9);
        gameField.generate();
        gameField.solveSudoku(0,0);
        assertTrue(gameField.isSolved());
    }

    @Test
    public void tileShouldBeFilledWhenFillTileIsCalled(){
        GameField gameField = new GameField(9,9);
        gameField.generate();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                var tile = gameField.getTile(row, column);
                if(tile.getState() == TileStatus.TO_GUESS){
                    gameField.fillTile(row,column,5);
                    Assertions.assertEquals(5, tile.getValue());
                    break;
                }
            }
        }
    }

    @Test
    public void tileShouldBeEmptyWhenDeleteGuessIsCalled(){
        GameField gameField = new GameField(9,9);
        gameField.generate();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                var tile = gameField.getTile(row, column);
                if(tile.getState() == TileStatus.TO_GUESS){
                    gameField.fillTile(row, column,5);
                    gameField.deleteGuess(row, column);
                    Assertions.assertEquals(0,  tile.getValue());
                    break;
                }
            }
        }
    }
    @Test
    public void numberOfEmptyTilesShouldEqualsToChosenDiff(){
        GameField gameField = new GameField(9,9);
        gameField.setDifficultyAndGenerate(5);
        int counter = 0;

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                var tile = gameField.getTile(row, column);
                if(tile.getState() == TileStatus.TO_GUESS){
                   counter++;
                }
            }
        }

        Assertions.assertEquals(46,  counter);
    }

}