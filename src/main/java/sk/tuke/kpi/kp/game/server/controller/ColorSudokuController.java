package sk.tuke.kpi.kp.game.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.game.core.*;


@Controller
@RequestMapping("/colorsudoku")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ColorSudokuController {
    private GameField gameField;
    private int row, column;

    private boolean difficultySet = false;
    @RequestMapping
    public String colorSudoku(@RequestParam(value = "column", required = false) Integer column,
                              @RequestParam(value = "row", required = false) Integer row){

        if(row != null && column != null) {
            this.row = row;
            this.column = column;
        }
        return "colorsudoku";
    }

    @RequestMapping("/value")
    public String setTileValue(@RequestParam(value = "value", required = false) Integer value){
        if(value == null) throw new RuntimeException("Value is null");
        gameField.fillTile(row, column, value);

        return "colorsudoku";
    }

    @RequestMapping("/difficulty")
    public String setDifficulty(@RequestParam(value = "diff", required = false) Integer diff){
        if(diff == null) throw new RuntimeException("Defficulty is null");

        gameField = new GameField(9,9);
        gameField.setDifficultyAndGenerate(diff);

        difficultySet = true;
        return "colorsudoku";
    }

    @RequestMapping("/new")
    public String newGame() {
        difficultySet = false;

        return "colorsudoku";
    }


    public String getHtmlField(){
        var sb = new StringBuilder();
        //gameField.setDifficultyAndGenerate(0);

        sb.append("<table class='gamefield'>\n");
        for(int row = 0; row < gameField.getRowCount(); row++){
            sb.append("<tr>\n");
            for(int column = 0; column < gameField.getColumnCount(); column++){
                var tile = gameField.getTile(row, column);
                sb.append("<td>\n");
                if(tile instanceof Solvable && tile.getState() == TileStatus.TO_GUESS){
                    sb.append("<a href='/colorsudoku?row=" + row + "&column=" + column + "' class='empty' onclick='showDiv(event)'></a>\n");
                }else if(tile.getState() != TileStatus.TO_GUESS){
                    sb.append("<span class='" + tile.getValue() + "'></span>\n");
                }
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");

        return sb.toString();
    }

    public boolean isDifficultySet() {
        return difficultySet;
    }
}
