package madbrains.service;

import lombok.extern.slf4j.Slf4j;
import madbrains.config.CustomUserDetails;
import madbrains.service.serviceAnnotation.Verificate;
import madbrains.dao.CatalogueDAO;
import madbrains.model.Element;
import madbrains.model.Catalogue;
import madbrains.dao.ComparatorDAO;
import madbrains.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import madbrains.component.ComparatorComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CatalogueService {
    private final CatalogueDAO catalogueDAO;
    private final ComparatorDAO comparatorDAO;

    @Autowired
    private SecurityService securityService;

    @Autowired
    public CatalogueService(CatalogueDAO catalogueDAO, ComparatorDAO comparatorDAO) {
        this.catalogueDAO = catalogueDAO;
        this.comparatorDAO = comparatorDAO;
    }

    @Autowired
    public ComparatorComponent myComparatorComponent;

    public void addCatalogue(Catalogue catalogue) {
        String login = CustomUserDetails.reverse((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = securityService.getByUsername(login);
        catalogue.setUser(securityService.getByUsername(user.getLogin()));
        catalogueDAO.addCatalogue(catalogue);
    }

    @Verificate
    public List<Catalogue> getCatalogues() {
        List<Catalogue> catalogue = catalogueDAO.getCatalogues();
        return catalogue;
    }

    @Verificate
    public String getCatalogueName(int id) {
        String catalogue = catalogueDAO.getCatalogue(id).getName();
        return catalogue;
    }

    @Verificate
    public Catalogue getCatalogue(int id) {
        Catalogue catalogue = catalogueDAO.getCatalogue(id);
        return catalogue;
    }

    @Verificate
    public void addElementSingle(Element element, int catalogue_id) {
        Catalogue catalogue = catalogueDAO.getCatalogue(catalogue_id);
        element.setCatalogue(catalogue);
        catalogueDAO.addElement(element);
    }

    @Verificate
    public void addElements(List<Element> elementList, int catalogue_id) {
        for (Element element : elementList) {
            element.setCatalogue(getCatalogue(catalogue_id));
            catalogueDAO.addElement(element);
        }
    }

    @Verificate
    public int getSize(int id) {
        return catalogueDAO.getSize(id);
    }

    @Verificate
    public Element getElement(int id, int element_id) {
        return catalogueDAO.getElement(id, element_id);
    }

    @Verificate
    public void deleteElement(int id, int element_id) {
        catalogueDAO.deleteElement(id, element_id);
    }

    @Verificate
    public int countElement(int id, String json_element) {
        return catalogueDAO.countElement(id, json_element);
    }

    @Verificate
    public List<Element> sortElements(int id) {
        List<Element> list = catalogueDAO.getElements(id);
        return list;
    }

    @Verificate
    public List<Element> sortElementsCustom(int id, int comp_id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        myComparatorComponent.load(comparatorDAO.getComparatorByID(comp_id));

        List<Element> list = catalogueDAO.getElements(id);
        Element array[] = new Element[list.size()];
        int i = 0;
        for (Element e : list) {
            array[i] = e;
            i++;
        }

        for (int j = array.length - 1; j > 0; j--) {
            for (int k = 0; k < j; k++) {
                if (myComparatorComponent.compare(array[k], array[k + 1]) > 0) {
                    Element tmp = array[k];
                    array[k] = array[k + 1];
                    array[k + 1] = tmp;
                }
            }
        }
        list = Arrays.asList(array);
        return list;
    }

    @Verificate
    public List<Element> shuffleElements(int id) {
        List<Element> list = catalogueDAO.getElements(id);
        Collections.shuffle(list);
        return list;
    }
}