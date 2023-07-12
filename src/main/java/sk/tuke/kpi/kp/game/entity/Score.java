package sk.tuke.kpi.kp.game.entity;

import jdk.jfr.Enabled;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Score {
    @Id
    @GeneratedValue
    private int Ident;
    private String player;
    private String game;
    private int points;
    private Date playedAt;

    public Score(String player, String game, int points, Date playedAt) {
        this.player = player;
        this.game = game;
        this.points = points;
        this.playedAt = playedAt;
    }

    public Score() {}

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setIdent(int ident) {
        Ident = ident;
    }

    public int getIdent() {
        return Ident;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public int getPoints() {
        return points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }
}
