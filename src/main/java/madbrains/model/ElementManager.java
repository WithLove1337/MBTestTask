package madbrains.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedSuperclass
public class ElementManager<T extends madbrains.model.Element> {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "catalogue_id")
    private List<T> elements;

    /*public ElementManager() {
        this.elements = new ArrayList<>();
    }

    public List<T> getElements() {
        return elements;
    }*/
}
