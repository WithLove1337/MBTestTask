package madbrains.component;

import madbrains.config.CustomUserDetails;
import madbrains.dao.SecurityDAO;
import madbrains.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SecurityDAO securityDAO;

    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = securityDAO.getByUsername(s);
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
