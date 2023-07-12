package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWD = "davidko1004";
    public static final String SELECT_ST = "SELECT player, game, comment_text, written_at FROM comment WHERE game = ?";
    public static final String DELETE_ST = "DELETE FROM comment";
    public static final String INSERT_ST = "INSERT INTO comment (player, game, comment_text, written_at) VALUES (?, ?, ?, ?)";

    @Override
    public void addComment(Comment comment) throws CommentException{
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(INSERT_ST);
        ) {
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment_text());
            statement.setTimestamp(4, new Timestamp(comment.getWrittenAt().getTime()));
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new ScoreException("Problem inserting comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ST);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()){ //try kvoli close
                var comments = new ArrayList<Comment>();
                while(rs.next()){
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return comments;
            }

        }catch (SQLException e) {
            throw new ScoreException("Problem selecting comments", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_ST);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting comments", e);
        }
    }
}
