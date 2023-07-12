package sk.tuke.kpi.kp.game.entity;

import org.hibernate.annotations.NamedQuery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Rating {
    @Id
    private String player;
    private String game;
    private int rating;
    private Date ratedOn;

    public Rating(String player, String game, int rating, Date ratedOn) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public Rating() {}

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public int getRating() {
        return rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }
}
