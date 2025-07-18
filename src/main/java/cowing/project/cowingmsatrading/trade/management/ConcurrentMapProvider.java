package cowing.project.cowingmsatrading.trade.management;

import cowing.project.cowingmsatrading.trade.dto.PendingOrderDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ConcurrentMapProvider {

    @Bean
    public ConcurrentHashMap<String, PendingOrderDto> getConcurrentMap() {
        return new ConcurrentHashMap<>();
    }
}
