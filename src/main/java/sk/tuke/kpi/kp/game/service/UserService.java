package sk.tuke.kpi.kp.game.service;

import sk.tuke.kpi.kp.game.entity.User;

public interface UserService {
    void addUser(User user) throws UserException;

    User getUser(String user) throws UserException;
}
