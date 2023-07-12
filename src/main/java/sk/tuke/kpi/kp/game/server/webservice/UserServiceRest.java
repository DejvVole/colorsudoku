package sk.tuke.kpi.kp.game.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.kpi.kp.game.entity.User;
import sk.tuke.kpi.kp.game.service.UserService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/login")
public class UserServiceRest {

    @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @GetMapping("/{user}")
    public User getUser(@PathVariable String user){
        User foundUser = userService.getUser(user);
        if(foundUser == null) return null;
        return foundUser;
    }


}
