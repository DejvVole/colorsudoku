package sk.tuke.kpi.kp.game;

import sk.tuke.kpi.kp.game.entity.Score;
import sk.tuke.kpi.kp.game.service.ScoreService;
import sk.tuke.kpi.kp.game.service.ScoreServiceJDBC;

import java.sql.DriverManager;
import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception{
        /*
        //try preto lebo na konci zavola close na vsetko vnutri
        try ( var connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "davidko1004");
            var statement = connection.createStatement(); //vytvorenie objketu na posielanie prikazov na databazu
            var rs = statement.executeQuery("SELECT player, game, points, played_at FROM score WHERE game = 'mines' ORDER BY points DESC LIMIT 10");
        ) {
            // query = SELECT
            // update = ZMENA
            //statement.executeUpdate("INSERT INTO score (player, game, points, played_at) values ('david', 'mines', 105, '2023-03-11');");

            while(rs.next()){
                System.out.printf("%s %s %d %s\n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4));
            }
        }
        System.out.println("-------------")

         */
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        //service.addScore(new Score("david", "colorsudoku", 201, new Date()));
        //service.addScore(new Score("dejvido", "colorsudoku", 205, new Date()));
        //service.getTopScore("colorsudoku");
    }
}
