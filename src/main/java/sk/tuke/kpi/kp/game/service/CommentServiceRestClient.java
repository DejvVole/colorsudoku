package sk.tuke.kpi.kp.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.entity.Score;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{
    private String url = "http://localhost:8090/api/comment";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url , comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset() throws CommentException {
        throw new UnsupportedOperationException("Reset is not supported");
    }
}
