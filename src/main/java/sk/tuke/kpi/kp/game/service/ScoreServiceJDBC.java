package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWD = "davidko1004";
    public static final String SELECT_ST = "SELECT player, game, points, played_at FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";
    public static final String DELETE_ST = "DELETE FROM score";
    public static final String INSERT_ST = "INSERT INTO score (player, game, points, played_at) VALUES (?, ?, ?, ?)";

    @Override
    public void addScore(Score score) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(INSERT_ST);
        ) {
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedAt().getTime()));
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new ScoreException("Problem inserting score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException{
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ST);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()){ //try kvoli close
                var scores = new ArrayList<Score>();
                while(rs.next()){
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }

        }catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
    }

    @Override
    public void reset() {
        try ( Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
              Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_ST);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }

}
