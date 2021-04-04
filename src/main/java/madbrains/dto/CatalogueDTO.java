package madbrains.dto;

import lombok.Data;
import madbrains.model.Catalogue;
import madbrains.model.Element;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CatalogueDTO {
    private int id;
    private String name;
    private List<ElementDTO> elements;

    public static CatalogueDTO from(Catalogue catalogue) {
        CatalogueDTO CatalogueDTO = new CatalogueDTO();
        CatalogueDTO.setName(catalogue.getName());

        List<ElementDTO> ElementDTOList = catalogue.getElements().stream()
                .map(ElementDTO::from)
                .collect(Collectors.toList());
        CatalogueDTO.setElements(ElementDTOList);
        return CatalogueDTO;
    }

    public Catalogue toCatalogue() {
        Catalogue catalogue = new Catalogue();
        catalogue.setId(id);
        catalogue.setName(name);

        if (!CollectionUtils.isEmpty(this.elements)) {
            List<Element> elements = this.elements.stream()
                    .map(ElementDTO::toElement)
                    .peek(e -> e.setCatalogue(catalogue))
                    .collect(Collectors.toList());
            catalogue.getElements().addAll(elements);
        }

        return catalogue;
    }
}
