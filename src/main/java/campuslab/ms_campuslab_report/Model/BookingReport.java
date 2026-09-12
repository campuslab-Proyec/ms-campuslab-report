package campuslab.ms_campuslab_report.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKING_REPORT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingReport {

    @Id
    private String bookingId;
    private String studentId;
    private String resourceId;
    private LocalDateTime timestamp;
    private String status;
    private LocalDateTime processedAt;
}