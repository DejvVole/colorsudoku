package sk.tuke.kpi.kp.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.game.entity.User;

public class UserServiceRestClient implements UserService{

    private String url = "http://localhost:8090/api/login";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addUser(User user) throws UserException {
        restTemplate.postForEntity(url, user, User.class);
    }

    @Override
    public User getUser(String user) throws UserException {
        return restTemplate.getForEntity(url + "/" + user, User.class).getBody();
    }
}
