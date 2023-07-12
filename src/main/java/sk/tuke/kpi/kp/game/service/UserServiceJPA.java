package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) throws UserException {
        entityManager.persist(user);
    }

    @Override
    public User getUser(String username) throws UserException {
        try {
            var getUser = entityManager.createQuery("select u from User u where u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            return (User) getUser;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
