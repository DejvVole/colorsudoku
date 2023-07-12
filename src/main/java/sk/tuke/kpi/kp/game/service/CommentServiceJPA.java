package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        var comments = entityManager.createQuery("select s from Comment s where s.game = :game order by s.writtenAt desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
        return comments;
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
