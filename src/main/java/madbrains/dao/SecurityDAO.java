package madbrains.dao;

import madbrains.model.Element;
import madbrains.model.MyComparator;
import madbrains.model.Role;
import madbrains.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class SecurityDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Role getByRole(int id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    public User getByUsername(String login) {
        User user = entityManager.createQuery(
                "select e from User e where e.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;

        /*User user = entityManager.find(User.class, username);
        return user;*/
    }

    public User getByUsernamePassword(String login, String password) {
        /*User user = entityManager.createQuery(
                "select e from User e where e.username = :username and e.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return user;*/

        /*User user = entityManager.createQuery(
                "select e from User e where e.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        if (passwordEncoder.encode(password).matches(user.getPassword())) {
            return user;
        }
        else
            return null;*/
        User user = entityManager.createQuery(
                "select e from User e where e.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }
}
