package madbrains.config;

import madbrains.model.Catalogue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@ComponentScan("madbrains.model")
public class CatalogueConfig {
    @Bean
    @Primary
    public Catalogue configureCatalogue() {
        Catalogue catalogue = new Catalogue();
        return catalogue;
    }
}
