package sk.tuke.kpi.kp.game.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int Ident;
    private String player;
    private String comment_text;
    private String game;
    private Date writtenAt;

    public void setIdent(int ident) {
        Ident = ident;
    }

    public int getIdent() {
        return Ident;
    }

    public Comment(String player, String game, String comment_text, Date writtenAt) {
        this.player = player;
        this.comment_text = comment_text;
        this.game = game;
        this.writtenAt = writtenAt;
    }

    public Comment() {}

    public String getPlayer() {
        return player;
    }

    public String getComment_text() {
        return comment_text;
    }

    public String getGame() {
        return game;
    }

    public Date getWrittenAt() {
        return writtenAt;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setWrittenAt(Date writtenAt) {
        this.writtenAt = writtenAt;
    }
}
