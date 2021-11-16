package slash.code.dealer.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {

    String email;
    String password;

    Collection<Role> roles = new ArrayList<>();

}
