package madbrains.service;

import lombok.extern.slf4j.Slf4j;
import madbrains.model.MyComparator;
import madbrains.component.ComparatorComponent;
import madbrains.dao.ComparatorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ComparatorService {
    private final ComparatorDAO comparatorDAO;

    @Autowired
    public ComparatorService(ComparatorDAO comparatorDAO) {
        this.comparatorDAO = comparatorDAO;
    }

    @Autowired
    public ComparatorComponent comp;

    @Transactional
    public void createComparator(MyComparator myComparator) {
        comparatorDAO.createComparator(myComparator);
    }

    public String getComparator(int id) {
        return comparatorDAO.getComparatorByID(id);
    }
}
