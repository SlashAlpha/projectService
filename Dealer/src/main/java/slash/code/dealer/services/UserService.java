package slash.code.dealer.services;

import org.springframework.security.core.userdetails.UserDetails;
import slash.code.dealer.security.User;


public interface UserService {
    UserDetails loadUserByUsername(String email);

    User saveUser(User user);

    void securedPokerApi(String code);

    User getUserDealer();

    User getUserPoker();
}
