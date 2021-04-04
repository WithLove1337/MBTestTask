package madbrains.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "elements")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private int value;

    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "catalogue_id")
    private Catalogue catalogue;

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Element() {
    }

    public Element(String name, int value) {
        this.name = name;
        this.value = value;
    }*/
}
