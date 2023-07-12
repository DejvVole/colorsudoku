package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Rating;
import sk.tuke.kpi.kp.game.entity.Score;

import java.sql.*;
import java.util.ArrayList;

public class RatingServiceJDBC implements RatingService{

    private boolean playerAlreadyRated = false;
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWD = "davidko1004";
    public static final String SELECT_ST = "SELECT player, game, rating, rated_on FROM rating WHERE game = ?";
    public static final String DELETE_ST = "DELETE FROM rating";
    public static final String INSERT_ST = "INSERT INTO rating (player, game, rating, rated_on) VALUES (?, ?, ?, ?)";
    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement pStatement1 = connection.prepareStatement(INSERT_ST);
             PreparedStatement pStatement2 = connection.prepareStatement(SELECT_ST);
             Statement statement3 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ) {
            pStatement2.setString(1, rating.getGame());
            try(ResultSet rs = statement3.executeQuery(String.valueOf(pStatement2))){
                while(rs.next()){
                    if(rs.getString(1).equals(rating.getPlayer())){
                        rs.updateInt(3, rating.getRating());
                        rs.updateRow();
                        playerAlreadyRated = true;
                    }
                }
                if(!playerAlreadyRated){
                    pStatement1.setString(1, rating.getPlayer());
                    pStatement1.setString(2, rating.getGame());
                    pStatement1.setInt(3, rating.getRating());
                    pStatement1.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                    pStatement1.executeUpdate();
                }
            }
        }catch (SQLException e) {
            throw new ScoreException("Problem setting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int average = 0;
        int numberOfRows = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ST);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()){ //try kvoli close

                while(rs.next()){
                    average += rs.getInt(3);
                    numberOfRows++;
                }
            }

        }catch (SQLException e) {
            throw new ScoreException("Problem selecting rating", e);
        }
        return average/numberOfRows;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ST);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()){ //try kvoli close
                while(rs.next()){
                    if(rs.getString(1).equals(player)){
                        return rs.getInt(3);
                    }
                }
            }
        }catch (SQLException e) {
            throw new ScoreException("Problem getting rating", e);
        }
        return 0;
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_ST);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
