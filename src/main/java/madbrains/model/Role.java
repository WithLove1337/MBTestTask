package madbrains.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    //MANUAL UPLOAD ROLE DATA TO DB (STARTS WITH ROLE_)
    @Column(name = "name")
    String name;
}