package madbrains.dao;

import madbrains.model.MyComparator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ComparatorDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createComparator(MyComparator myComparator) {
        entityManager.persist(myComparator);
        entityManager.flush();
    }

    public String getComparatorByID(int id) {
        MyComparator myComparator = entityManager.find(MyComparator.class, id);
        return myComparator.getCode();
    }
}
