package madbrains.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "myusers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "login", unique = true)
    public String login;

    @Column(name = "password")
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
