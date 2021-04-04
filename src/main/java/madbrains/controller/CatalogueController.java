package madbrains.controller;

import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import madbrains.model.Catalogue;
import madbrains.model.Element;
import madbrains.dto.CatalogueDTO;
import madbrains.dto.ElementDTO;
import madbrains.service.CatalogueService;
import madbrains.service.SecurityService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class CatalogueController {
    private final CatalogueService catalogueService;

    private final SecurityService securityService;

    @Autowired
    public CatalogueController(CatalogueService catalogueService, SecurityService securityService) {
        this.catalogueService = catalogueService;
        this.securityService = securityService;
    }

    @PostMapping("/lists")
    public ResponseEntity<?> addCatalogue(@RequestBody CatalogueDTO catalogueDTO) {
        try {
            catalogueService.addCatalogue(catalogueDTO.toCatalogue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error at creating list with" + catalogueDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists")
    public ResponseEntity<?> getCatalogues() {
        try {
            List<CatalogueDTO> catalogueList = catalogueService.getCatalogues().stream()
                    .map(CatalogueDTO::from)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(catalogueList);
        } catch (Exception e) {
            log.info("Error at getting lists with certain user");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //RESOLVE THE PROBLEM OF RECURSE OUTPUT OF CATALOGUE BY BUILDING ITS DATA INTO HASHMAP SEPARATELY
    @GetMapping("/lists/{id}")
    public ResponseEntity<?> getCatalogue(@PathVariable int id) {
        HashMap<String, List<ElementDTO>> catalogue = new HashMap<>();
        try {
            List<ElementDTO> elementDTOList = catalogueService.getCatalogue(id).getElements().stream()
                    .map(ElementDTO::from)
                    .collect(Collectors.toList());
            catalogue.put(catalogueService.getCatalogueName(id), elementDTOList);
            return ResponseEntity.ok(catalogue);
        }
        catch (Exception e) {
            log.info("Error at getting catalogue");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("lists/{id}/elements")
    public ResponseEntity addElement(@RequestBody Element element, @PathVariable(name = "id") int id) {
        try {
            catalogueService.addElementSingle(element, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error at creating element with " + element);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/lists/{id}/elements")
    public ResponseEntity addElements(@RequestBody List<Element> elementList, @PathVariable int id) {
        try {
            catalogueService.addElements(elementList, id);
            return ResponseEntity.ok().build();
        }   catch (Exception e) {
            log.info("Error at creating element with " + elementList);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/{id}/size")
    public ResponseEntity<?> getSize(@PathVariable int id) {
        try {
            int count = catalogueService.getSize(id);
            if (count >= 0) {
                return ResponseEntity.ok(count + 1);
            } else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Error at getting list size");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity<?> getElement(@PathVariable int id, @PathVariable int id_element) {
        try {
            return ResponseEntity.ok(ElementDTO.from(catalogueService.getElement(id, id_element)));
        } catch (Exception e) {
            log.info("Error at getting element with " + id_element + " by catalogue " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/lists/{id}/elements/{id_element}")
    public ResponseEntity<?> deleteElement(@PathVariable int id, @PathVariable int id_element){
        try {
            catalogueService.deleteElement(id, id_element);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error at deleting element with" + id_element + " by catalogue " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/{id}/find")
    public ResponseEntity<?> countElement(@RequestParam(name = "name") String json, @PathVariable int id) {
        try {
            int count = catalogueService.countElement(id, json);
            if (count >= 0) {
                return ResponseEntity.ok(count);
            } else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Error at counting element " + json + " by catalogue " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/{id}/sort")
    public ResponseEntity<?> sortElements(@RequestParam(defaultValue = "0") int myComparator_id, @PathVariable int id) {
        try {
            List<ElementDTO> list;
            if (myComparator_id == 0) {
                list = catalogueService.sortElements(id).stream()
                        .map(ElementDTO::from)
                        .collect(Collectors.toList());
            }
            else  {
                list = catalogueService.sortElementsCustom(id, myComparator_id).stream()
                        .map(ElementDTO::from)
                        .collect(Collectors.toList());
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.info("Error at sorting list with " + id + " by comparator " + myComparator_id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists/{id}/shuffle")
    public ResponseEntity<?> shuffleElements(@PathVariable int id) {
        try {
            List<ElementDTO> list = catalogueService.shuffleElements(id).stream()
                    .map(ElementDTO::from)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.info("Error at shuffling elements with " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
