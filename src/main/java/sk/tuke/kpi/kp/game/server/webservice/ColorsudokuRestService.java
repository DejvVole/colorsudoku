package sk.tuke.kpi.kp.game.server.webservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.kpi.kp.game.core.GameField;

@RestController
@RequestMapping("api/colorsudoku/field")
public class ColorsudokuRestService {
    private GameField gameField = new GameField(9,9);

    @GetMapping()
    public GameField getField() {
        return gameField;
    }

    @GetMapping("/newgame")
    public GameField newGame(@RequestParam int diff) {
        this.gameField = new GameField(9, 9);
        gameField.setDifficultyAndGenerate(diff);
        return gameField;
    }

    @GetMapping("/filltile")
    public GameField openTile(@RequestParam int row, @RequestParam int col, @RequestParam int val) {
        gameField.fillTile(row, col, val);
        gameField.isSolved();
        return gameField;
    }

    @GetMapping("/deletetile")
    public GameField deleteTile(@RequestParam int row, @RequestParam int col) {
        gameField.deleteGuess(row, col);
        return gameField;
    }

    @GetMapping("/randomfill")
    public GameField randomFill() {
        gameField.randomFill();
        return gameField;
    }

}
