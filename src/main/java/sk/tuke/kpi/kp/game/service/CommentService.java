package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Score;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;

}
