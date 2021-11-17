package slash.code.game.service;

import slash.code.game.user.Role;
import slash.code.game.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    User getUser(String email);

    List<User> getUsers();

    void securityDealer(String pass);

}
