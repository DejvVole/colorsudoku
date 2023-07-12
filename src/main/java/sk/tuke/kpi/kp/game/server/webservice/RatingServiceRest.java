package sk.tuke.kpi.kp.game.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.game.entity.Rating;
import sk.tuke.kpi.kp.game.service.RatingException;
import sk.tuke.kpi.kp.game.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating){
        if(rating.getRating() < 1 || rating.getRating() > 5) throw new RatingException("Bad rating");
        ratingService.setRating(rating);
    }
    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game){
        return ratingService.getAverageRating(game);
    }
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player){
        return ratingService.getRating(game, player);
    }
}
