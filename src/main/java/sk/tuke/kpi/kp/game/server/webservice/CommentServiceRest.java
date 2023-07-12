package sk.tuke.kpi.kp.game.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.game.entity.Comment;
import sk.tuke.kpi.kp.game.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment){
        if(comment.getComment_text() == null) throw new ClassCastException("Bad comment");
        commentService.addComment(comment);
    }
}
