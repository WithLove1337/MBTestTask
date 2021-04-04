package madbrains.dto;

import lombok.Data;
import madbrains.model.Element;

@Data
public class ElementDTO {
    private int id;
    private String name;
    private int value;

    public static ElementDTO from(Element element) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setId(element.getId());
        elementDTO.setName(element.getName());
        elementDTO.setValue(element.getValue());
        return elementDTO;
    }

    public Element toElement() {
        Element element = new Element();
        element.setId(this.id);
        element.setName(this.name);
        element.setValue(this.value);
        return element;
    }
}
