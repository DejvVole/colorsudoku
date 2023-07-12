package sk.tuke.kpi.kp.game.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.kpi.kp.game.entity.Score;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ScoreJDBCTest {
    @Autowired
    ScoreService scoreService;
    @Test
    public void reset() {
        scoreService.reset();

        assertEquals(0, scoreService.getTopScores("colorsudoku").size());
    }

    @Test
    public void addScore() {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("David", "colorsudoku", 100, date));

        var scores = scoreService.getTopScores("colorsudoku");

        assertEquals(1, scores.size());
        assertEquals("colorsudoku", scores.get(0).getGame());
        assertEquals("David", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());
    }

    @Test
    public void getTopScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("David", "colorsudoku", 130, date));
        scoreService.addScore(new Score("Peto", "colorsudoku", 108, date));
        scoreService.addScore(new Score("Denis", "colorsudoku", 200, date));
        scoreService.addScore(new Score("Stano", "colorsudoku", 20, date));

        var scores = scoreService.getTopScores("colorsudoku");

        assertEquals(4, scores.size());

        assertEquals("colorsudoku", scores.get(0).getGame());
        assertEquals("Denis", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("colorsudoku", scores.get(1).getGame());
        assertEquals("David", scores.get(1).getPlayer());
        assertEquals(130, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("colorsudoku", scores.get(2).getGame());
        assertEquals("Peto", scores.get(2).getPlayer());
        assertEquals(108, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedAt());
    }
}
