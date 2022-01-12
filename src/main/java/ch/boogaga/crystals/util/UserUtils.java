package ch.boogaga.crystals.util;

import ch.boogaga.crystals.model.User;
import ch.boogaga.crystals.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUtils {
    public static User fromTo(UserTo to) {
        return new User(null, to.getName(), to.getLogin(), to.getPassword(), to.getEmail());
    }

    public static UserTo fromUser(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLogin(), user.getEmail(), user.getPassword());
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
