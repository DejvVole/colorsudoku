package sk.tuke.kpi.kp.game.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.kpi.kp.game.core.*;
import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Rating;
import sk.tuke.kpi.kp.game.entity.Score;
import sk.tuke.kpi.kp.game.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final GameField gameField;
    final Pattern INPUT_PATTERN = Pattern.compile("([A-I])([1-9])-([1-9])");
    final Pattern DELETE_PATTERN = Pattern.compile("([D])([A-I])([1-9])");

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String ORANGE_BACKGROUND = "\033[48;2;255;165;41m"; // ORANGE
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m"; // BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m"; // RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m"; // GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m"; // YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m"; // BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE
    public static final String GREEN = "\033[0;32m";   //REGULAR GREEN
    public static final String RED = "\033[0;31m";     //REGULAR RED
    Scanner input = new Scanner(System.in);
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ScoreService scoreService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CommentService commentService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RatingService ratingService;
    public ConsoleUI(GameField gameField) {
        this.gameField = gameField;
    }

    public void play(){
        GameState gameState = GameState.PLAYING;

        while(gameState == GameState.PLAYING) {
            printSudoku(gameField);
            playerInputWhilePlaying();

            if(gameField.isSolved()){
                printSudoku(gameField);
                gameState = GameState.SOLVED;
            }
        }

        if(gameState == GameState.SOLVED){
            System.out.println(GREEN + "YOU WON!" + RESET);
            System.out.print("Enter your nickname: ");
            String player_nick = input.nextLine();
            scoreService.addScore(new Score(player_nick, "colorsudoku", gameField.getScore(), new Date()));

            afterGameInput(player_nick);
        }
    }

    private void afterGameInput(String player_nick){
        System.out.println("Press:\n c - for comment\n r - to rate the game\n x - for exit ");
        System.out.print("Enter your command: ");
        String gameWonInput = input.nextLine().toUpperCase();

        switch (gameWonInput) {
            case "C": {
                writeComment(player_nick);

                break;
            }
            case "R": {
                rateGame(player_nick);

                break;
            }
            case "X":
                System.exit(0);
            default:
                System.out.println(RED + "\nWrong input!" + RESET);
                afterGameInput(player_nick);
                break;
        }
    }

    private void rateGame(String player_nick) {
        System.out.print("Enter you rating (1-5): ");

        int rating = 0;
        if(input.hasNextInt()){
            rating = input.nextInt();

            if(rating < 1 || rating > 5 ) {
                System.out.println(RED + "\nWrong input!" + RESET);
                rateGame(player_nick);
            }
        } else{
            System.out.println(RED + "\nWrong input!" + RESET);
            input.nextLine();
            rateGame(player_nick);
        }

        ratingService.setRating(new Rating(player_nick, "colorsudoku", rating, new Date()));
        input.nextLine();
        System.out.print("\nThanks for your rating! Press b to return. ");
        String back = input.nextLine().toUpperCase();
        if (back.equals("B")) afterGameInput(player_nick);
        else System.out.println(RED + "\nWrong input!" + RESET);
    }

    private void writeComment(String player_nick) {
        System.out.print("Write a comment: ");
        String comment = input.nextLine();
        commentService.addComment(new Comment(player_nick, "colorsudoku", comment, new Date()));
        System.out.print("\nThanks for your comment! Press b to return. ");
        String back = input.nextLine().toUpperCase();
        if (back.equals("B")) afterGameInput(player_nick);
        else System.out.println(RED + "\nWrong input!" + RESET);
    }

    private void playerInputWhilePlaying(){
        System.out.print("Colors: ");
        for(int i = 1; i < 10; i++){
            System.out.print(TileValue.getColor(i) + "(" + i + ")");
            System.out.print(" ");
        }

        System.out.println("\nYour score: " + gameField.getScore());

        System.out.println();
        System.out.print("Enter command (X - exit, A1-value - to guess, , DA1 - to delete): ");

        String in = input.nextLine().toUpperCase();

        if(in.equals("X")){
            System.exit(0);
        }

        Matcher matcher = INPUT_PATTERN.matcher(in);
        Matcher matcher_delete = DELETE_PATTERN.matcher(in);

        playInputMatcher(in, matcher, matcher_delete);
    }

    private void playInputMatcher(String in, Matcher matcher, Matcher matcher_delete) {
        if (matcher.matches()) {
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            int value = Integer.parseInt(matcher.group(3));
            gameField.fillTile(row, column, value);

        } else if(matcher_delete.matches()){
            int row = matcher_delete.group(2).charAt(0) - 'A';
            int column = Integer.parseInt(matcher_delete.group(3)) - 1;
            if (in.startsWith("D")) {
                gameField.deleteGuess(row, column);
            }
        } else {
            System.out.println(RED + "\nWrong input!" + RESET);
        }
    }

    private void printSudoku(GameField gameField) {
        System.out.println();
        System.out.print(" ");
        for(int column = 0; column < gameField.getColumnCount(); column++){
            System.out.print(" ");
            if(column % 3 == 0){
                System.out.print("  ");
            }
            System.out.print(column+1);
        }

        System.out.println();

        renderField();
    }

    private void printColor(GameField gameField, int row, int column){
        Tile tile = gameField.getTile(row, column);
        switch (TileValue.getColor(tile.getValue())){ // zavola sa funkcia getColor kde parameter je cislo tilu(farba)
            case RED:
                System.out.print(RED_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case YELLOW:
                System.out.print(YELLOW_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case GREEN:
                System.out.print(GREEN_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case CYAN:
                System.out.print(CYAN_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case BLUE:
                System.out.print(BLUE_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case ORANGE:
                System.out.print(ORANGE_BACKGROUND + " " + RESET);
                break;
            case PURPLE:
                System.out.print(PURPLE_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case WHITE:
                System.out.print(WHITE_BACKGROUND_BRIGHT + " " + RESET);
                break;
            case BLACK:
                System.out.print(BLACK_BACKGROUND_BRIGHT + " " + RESET);
                break;
            default: throw new RuntimeException("Unexpected tile color.");
        }
    }

    private void renderField(){
        System.out.println("  +-----------------------+");
        for(int row = 0; row < gameField.getRowCount(); row++){
            System.out.print((char) (row + 'A'));
            System.out.print(" |");
            for(int column = 0; column < gameField.getColumnCount(); column++){
                Tile tile = gameField.getTile(row, column);
                System.out.print(" ");

                if(tile instanceof Solvable && tile.getState() == TileStatus.TO_GUESS){
                    System.out.print(" ");
                }else if(tile.getState() != TileStatus.TO_GUESS){
                    printColor(gameField ,row, column);
                }

                if((column + 1) % 3 == 0){
                    System.out.print(" |");
                }
            }
            System.out.println();
            if((row + 1) % 3 == 0) {
                System.out.println("  +-----------------------+");
            }
        }
    }

    public void gameMenu(){
        //System.out.println("Welcome in COLOR SUDOKU\n Press: ");
        System.out.println("\nPress:\n a - for average rating\n c - for list all comments\n t - for list top scores\n p - for play\n x - for exit");
        System.out.print("Enter your command: ");

        String menu = input.nextLine().toUpperCase();

        if(menu.equals("A")){
            System.out.println("\nAverage rating is: " + ratingService.getAverageRating("colorsudoku"));
            gameMenu();
        } else if(menu.equals("C")){
            System.out.println();
            listComments();
        } else if(menu.equals("T")){
            topScore();
        } else if(menu.equals("P")){
            gameSettings();
        } else if(menu.equals("X")){
            System.exit(0);
        } else {
            System.out.println(RED + "\nWrong input!" + RESET);
            gameMenu();
        }
    }

    private void topScore(){
        List<Score> scores;
        scores = scoreService.getTopScores("colorsudoku");

        System.out.println("\nList of top players:");

        for (Score score : scores) {
            System.out.printf("%s - %d\n", score.getPlayer(), score.getPoints());
        }

        gameMenu();
    }

    private void listComments(){
        List<Comment> comments;
        comments = commentService.getComments("colorsudoku");

        for (Comment comment : comments) {
            System.out.printf("%s - %s\n", comment.getPlayer(), comment.getComment_text());
        }

        gameMenu();
    }

    public void gameSettings(){
        int diff = 1;
        boolean wrong_input = true;

        System.out.println("\nChoose difficulty. Press E for easy, M for medium or H for hard.");

        while(wrong_input) {
            System.out.print("\nChoose difficulty: ");
            String settings = input.nextLine().toUpperCase();
            switch (settings) {
                case "E":
                    diff = 20;
                    wrong_input = false;
                    break;
                case "M":
                    diff = 5;
                    wrong_input = false;
                    break;
                case "H":
                    diff = 0;
                    wrong_input = false;
                    break;
                case "T":
                    diff = 50;
                    wrong_input = false;
                    break;
                default:
                    System.out.print(RED + "\nWrong input!" + RESET);
            }
        }

        gameField.setDifficultyAndGenerate(diff);
        play();
    }
}

