package sk.tuke.kpi.kp.game.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.kpi.kp.game.entity.Comment;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentJDBCTest {
    @Autowired
    CommentService commentService;
    @Test
    public void reset() {
        commentService.addComment(new Comment("David", "colorsudoku", "nice game", new Date()));
        commentService.reset();

        assertEquals(0, commentService.getComments("colorsudoku").size());
    }

    @Test
    public void addComment() {
        var date = new Date();

        commentService.reset();

        commentService.addComment(new Comment("David", "colorsudoku", "nice game", date));

        var comments = commentService.getComments("colorsudoku");

        assertEquals(1, comments.size());
        assertEquals("colorsudoku", comments.get(0).getGame());
        assertEquals("David", comments.get(0).getPlayer());
        assertEquals("nice game", comments.get(0).getComment_text());
        assertEquals(date, comments.get(0).getWrittenAt());
    }

    @Test
    public void getComments() {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("David", "colorsudoku", "nice game", date));
        commentService.addComment(new Comment("Denis", "colorsudoku", "bad game", date));
        commentService.addComment(new Comment("Peto", "colorsudoku", "very nice game", date));

        var comments = commentService.getComments("colorsudoku");

        assertEquals(3, comments.size());

        assertEquals("colorsudoku", comments.get(0).getGame());
        assertEquals("David", comments.get(0).getPlayer());
        assertEquals("nice game", comments.get(0).getComment_text());
        assertEquals(date, comments.get(0).getWrittenAt());

        assertEquals("colorsudoku", comments.get(1).getGame());
        assertEquals("Denis", comments.get(1).getPlayer());
        assertEquals("bad game", comments.get(1).getComment_text());
        assertEquals(date, comments.get(1).getWrittenAt());

        assertEquals("colorsudoku", comments.get(2).getGame());
        assertEquals("Peto", comments.get(2).getPlayer());
        assertEquals("very nice game", comments.get(2).getComment_text());
        assertEquals(date, comments.get(2).getWrittenAt());
    }
}
