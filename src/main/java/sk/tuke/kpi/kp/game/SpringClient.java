package sk.tuke.kpi.kp.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.game.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.game.core.GameField;
import sk.tuke.kpi.kp.game.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.kpi.kp.game.server.*"))

public class SpringClient {
    public static void main(String[] args) {
        //SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI){
        return s -> consoleUI.gameMenu();
    }

    @Bean
    public ConsoleUI consoleUI(){
        return new ConsoleUI(new GameField(9, 9));
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceRestClient();
    }

    @Bean
    public UserService userService(){
        return new UserServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
