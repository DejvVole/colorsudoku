package sk.tuke.kpi.kp.game.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.game.entity.Score;
import sk.tuke.kpi.kp.game.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    //GET -> http://localhost:8090/api/score/colorsudoku
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    //POST -> http://localhost:8090/api/score
    @PostMapping
    public void addScore(@RequestBody Score score) {
        System.out.println("yep");
        scoreService.addScore(score);
    }

}
