package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.merge(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        double average = entityManager.createQuery("select avg(r.rating) from Rating r where r.game = :game", Double.class)
                .setParameter("game", game)
                .getSingleResult();

        return (int) average;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating = entityManager.createQuery("select rating from Rating where game = :game and player = :player", Integer.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

        return rating;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
