package campuslab.ms_campuslab_report.Service;

import campuslab.ms_campuslab_report.Dto.ReportKpisDTO;
import campuslab.ms_campuslab_report.Dto.TopResourceDTO;
import campuslab.ms_campuslab_report.Kafka.BookingEvent;
import campuslab.ms_campuslab_report.Model.BookingReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BookingReportRepository repository;

    public void processEvent(BookingEvent event) {
        repository.save(new BookingReport(
                event.bookingId(), event.studentId(), event.resourceId(),
                event.timestamp(), event.status(), LocalDateTime.now()
        ));
    }

    public ReportKpisDTO calculateKpis(String range) {
        LocalDateTime from = resolveRange(range);
        long reservasPorHora = repository.countByTimestampAfter(from);
        Double tiempoCiclo = repository.avgCicloMinutos(from);
        long ocupados = repository.countByStatus("EN_USO");
        return new ReportKpisDTO(reservasPorHora, tiempoCiclo != null ? tiempoCiclo : 0.0, ocupados);
    }

    public List<TopResourceDTO> topResources(String range) {
        return repository.topResourcesSince(resolveRange(range));
    }

    private LocalDateTime resolveRange(String range) {
        return switch (range) {
            case "last24h" -> LocalDateTime.now().minusHours(24);
            case "last7d"  -> LocalDateTime.now().minusDays(7);
            default -> LocalDateTime.now().minusHours(24);
        };
    }
}
