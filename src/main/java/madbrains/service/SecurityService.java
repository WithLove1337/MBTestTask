package madbrains.service;

import madbrains.dao.SecurityDAO;
import madbrains.model.Role;
import madbrains.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private SecurityDAO securityDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        Role userRole = securityDAO.getByRole(2);
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return securityDAO.addUser(user);
    }

    public User getByUsername(String username) {
        return securityDAO.getByUsername(username);
    }

    public User getByUsernamePassword(String username, String password) {
        User user = securityDAO.getByUsernamePassword(username, password);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
