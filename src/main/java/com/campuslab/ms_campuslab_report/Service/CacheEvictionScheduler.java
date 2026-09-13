package com.campuslab.ms_campuslab_report.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictionScheduler {

    @CacheEvict(value = {"kpis", "topResources"}, allEntries = true)
    @Scheduled(fixedRate = 30000)
    public void evictCaches() {
        // Fuerza recálculo de KPIs cada 30 segundos
    }
}
