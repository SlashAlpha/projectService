package slash.code.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import slash.code.game.service.UserService;

@SpringBootApplication
public class GameApplication {


    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {

        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
