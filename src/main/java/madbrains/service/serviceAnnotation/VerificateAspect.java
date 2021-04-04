package madbrains.service.serviceAnnotation;

import madbrains.config.CustomUserDetails;
import madbrains.dao.CatalogueDAO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VerificateAspect {
    private final CatalogueDAO catalogueDAO;

    @Autowired
    public VerificateAspect(CatalogueDAO catalogueDAO) {
        this.catalogueDAO = catalogueDAO;
    }

    @Pointcut("@annotation(madbrains.service.serviceAnnotation.Verificate) && args(id,..)")
    public void callAtVerificate(int id) {    }

    @Around("callAtVerificate(id)")
    public Object aroundCallAt(ProceedingJoinPoint pjp, int id) throws Throwable {
        Object retVal = null;
        String catalogue = catalogueDAO.getCatalogue(id).getUser().getLogin();
        String login = CustomUserDetails.reverse((CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (catalogue.equals(login)) {
            retVal = pjp.proceed();
        }
        return retVal;
    }
}
