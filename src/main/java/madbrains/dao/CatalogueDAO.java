package madbrains.dao;

import lombok.extern.slf4j.Slf4j;
import madbrains.config.CustomUserDetails;
import madbrains.model.Element;
import madbrains.model.Catalogue;
import madbrains.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class CatalogueDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SecurityDAO securityDAO;

    @Transactional
    public void addCatalogue(Catalogue catalogue) {
        entityManager.persist(catalogue);
        entityManager.flush();
    }

    public Catalogue getCatalogue(int id) {
        Catalogue catalogues = entityManager.find(Catalogue.class, id);
        return catalogues;
    }

    public List<Catalogue> getCatalogues() {
        String login = CustomUserDetails.reverse((CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = securityDAO.getByUsername(login);
        List<Catalogue> catalogues = entityManager.createQuery(
                "select e from Catalogue e where e.user = :login", Catalogue.class)
                .setParameter("login", user.getId())
                .getResultList();
        return catalogues;
    }

    @Transactional
    public void addElement(Element element) {
        entityManager.persist(element);
        entityManager.flush();
    }

    public int getSize(int catalogue_id) {
        int count = 0;
        List<Element> elements = entityManager.createQuery(
                "select e from Element e where e.catalogue = :catalogue", Element.class)
                .setParameter("catalogue", getCatalogue(catalogue_id))
                .getResultList();
        for (Element item: elements)
            count++;
        return count - 1;
    }

    public Element getElement(int catalogue_id, int id) {
        Element element = (Element) entityManager.createQuery(
                "select e from Element e where e.id = :id and e.catalogue = :catalogue", Element.class)
                .setParameter("id", id)
                .setParameter("catalogue", getCatalogue(catalogue_id))
                .getSingleResult();
        return element;
    }


    public List<Element> getElements(int catalogue_id) {
        List<Element> elements = entityManager.createQuery(
                "select e from Element e where e.catalogue = :catalogue", Element.class)
                .setParameter("catalogue", getCatalogue(catalogue_id))
                .getResultList();
        return elements;
    }

    @Transactional
    public void deleteElement(int catalogue_id, int id) {
        Element element = getElement(catalogue_id, id);
        entityManager.remove(element);
    }

    public int countElement(int catalogue_id, String name) {
        List<Element> elements = entityManager.createQuery(
                "select e from Element e where e.catalogue = :catalogue and e.name = :name", Element.class)
                .setParameter("catalogue", getCatalogue(catalogue_id))
                .setParameter("name", name)
                .getResultList();
        int count = elements.size() - 1;
        return count;
    }
}
