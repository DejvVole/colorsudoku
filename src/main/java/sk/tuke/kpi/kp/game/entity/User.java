package sk.tuke.kpi.kp.game.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue
    private int Ident;
    private String username;
    private String password;
    private Date loggedAt;

    public User(String username, String password, Date loggedAt) {
        this.username = username;
        this.password = password;
        this.loggedAt = loggedAt;
    }

    public User() {}

    public int getIdent() {
        return Ident;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getLoggedAt() {
        return loggedAt;
    }

    public void setIdent(int id) {
        this.Ident = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoggedAt(Date loggedAt) {
        this.loggedAt = loggedAt;
    }
}
