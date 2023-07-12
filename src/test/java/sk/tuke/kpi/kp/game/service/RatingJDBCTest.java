package sk.tuke.kpi.kp.game.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Rating;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RatingJDBCTest {
    @Autowired
    RatingService ratingService;
    /*@Test
    public void reset() {
        ratingService.setRating(new Rating("David", "colorsudoku", 5, new Date()));
        ratingService.reset();

        assertEquals(5, ratingService.getRating("colorsudoku", "David"));
    }*/

    @Test
    public void setRating() {
        var date = new Date();

        ratingService.reset();

        ratingService.setRating(new Rating("David", "colorsudoku", 5, date));

        int rating = ratingService.getRating("colorsudoku", "David");

        assertEquals(5, rating);

    }

    @Test
    public void setRatingToExistingPlayer() {
        var date = new Date();

        ratingService.reset();

        ratingService.setRating(new Rating("David", "colorsudoku", 5, date));
        ratingService.setRating(new Rating("David", "colorsudoku", 3, date));

        int rating = ratingService.getRating("colorsudoku", "David");

        assertEquals(3, rating);

    }

    @Test
    public void getAverageRating() {
        var date = new Date();

        ratingService.reset();
        ratingService.setRating(new Rating("David", "colorsudoku", 5, date));
        ratingService.setRating(new Rating("Peto", "colorsudoku", 3, date));
        ratingService.setRating(new Rating("Denis", "colorsudoku", 1, date));

        int averageRating = ratingService.getAverageRating("colorsudoku");
        assertEquals(3, averageRating);
    }
}
