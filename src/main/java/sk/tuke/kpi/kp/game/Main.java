package sk.tuke.kpi.kp.game;

import sk.tuke.kpi.kp.game.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.game.core.GameField;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameField gameField = new GameField(9, 9);
        ConsoleUI consoleUI = new ConsoleUI(gameField);
        consoleUI.gameMenu();
    }
}
