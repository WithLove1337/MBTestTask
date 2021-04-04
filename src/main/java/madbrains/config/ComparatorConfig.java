package madbrains.config;

import madbrains.model.MyComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@ComponentScan("madbrains.model")
public class ComparatorConfig {
    @Bean
    @Primary
    public MyComparator configureMyComparator() {
        MyComparator myComparator = new MyComparator();
        return myComparator;
    }
}
