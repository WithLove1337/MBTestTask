package madbrains.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comparators")
public class MyComparator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "code", nullable = false)
    private String code;
}
