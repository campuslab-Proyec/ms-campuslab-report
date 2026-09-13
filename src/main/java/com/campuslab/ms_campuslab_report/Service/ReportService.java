package com.campuslab.ms_campuslab_report.Service;

import com.campuslab.ms_campuslab_report.Dto.KpiResponse;
import com.campuslab.ms_campuslab_report.Dto.TopResourceDto;
import com.campuslab.ms_campuslab_report.Repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repository;

    @Cacheable(value = "kpis", key = "#range")
    public KpiResponse getKpis(String range) {
        LocalDateTime since = resolveSince(range);

        long total = repository.countBookingsSince(since);
        long active = repository.countActiveSince(since);
        long completed = repository.countCompletedSince(since);
        Double avgCycle = repository.avgCycleTimeMinutes(since);

        return new KpiResponse(total, active, completed, avgCycle != null ? avgCycle : 0.0);
    }

    @Cacheable(value = "topResources", key = "#range")
    public List<TopResourceDto> getTopResources(String range) {
        LocalDateTime since = resolveSince(range);

        return repository.topResources(since).stream()
                .map(row -> new TopResourceDto((String) row[0], (Long) row[1]))
                .toList();
    }

    private LocalDateTime resolveSince(String range) {
        LocalDateTime now = LocalDateTime.now();
        return switch (range) {
            case "last24h" -> now.minusHours(24);
            case "last7d" -> now.minusDays(7);
            case "last30d" -> now.minusDays(30);
            default -> now.minusHours(24);
        };
    }
}
