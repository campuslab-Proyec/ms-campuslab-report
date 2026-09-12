package campuslab.ms_campuslab_report.Kafka;

import campuslab.ms_campuslab_report.Kafka.BookingEvent;
import campuslab.ms_campuslab_report.Service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {

    private final ReportService reportService;

    @KafkaListener(topics = "${campuslab.kafka.topic-events}", groupId = "report-service-group")
    public void consume(BookingEvent event) {
        log.info("Evento recibido [{}]: {}", event.traceId(), event);
        reportService.processEvent(event);
    }
}