package madbrains.component;

import madbrains.model.Element;
import org.springframework.stereotype.Component;
import groovy.lang.GroovyClassLoader;
import java.util.Comparator;
import java.lang.reflect.InvocationTargetException;

@Component
public class ComparatorComponent {
    private final GroovyClassLoader loader = new GroovyClassLoader();
    private Comparator<Element> comparatorRealization;

    public void load(String groovy) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object comparator = loader.parseClass(groovy).getDeclaredConstructor().newInstance();
        if (comparator instanceof Comparator) {
            Comparator<Element> comparatorRealization = (Comparator<Element>) comparator;
            this.comparatorRealization = comparatorRealization;
        }
    }

    public int compare(Element o1, Element o2) {
        return comparatorRealization.compare(o1, o2);
    }
}
