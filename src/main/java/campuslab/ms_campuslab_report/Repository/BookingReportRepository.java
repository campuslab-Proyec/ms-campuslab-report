package campuslab.ms_campuslab_report.Repository;

import campuslab.ms_campuslab_report.Dto.TopResourceDTO;
import campuslab.ms_campuslab_report.Model.BookingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingReportRepository extends JpaRepository<BookingReport, String> {

    long countByTimestampAfter(LocalDateTime from);

    long countByStatus(String status);

    @Query("""
        SELECT AVG(
            (CAST(FUNCTION('to_number', FUNCTION('to_char', b.processedAt, 'J')) AS long)
             - CAST(FUNCTION('to_number', FUNCTION('to_char', b.timestamp, 'J')) AS long)) * 1440.0
        )
        FROM BookingReport b
        WHERE b.timestamp >= :from
        """)
    Double avgCicloMinutos(@Param("from") LocalDateTime from);

    @Query("""
        SELECT new campuslab.ms_campuslab_report.Dto.TopResourceDTO(b.resourceId, COUNT(b))
        FROM BookingReport b
        WHERE b.timestamp >= :from
        GROUP BY b.resourceId
        ORDER BY COUNT(b) DESC
        """)
    List<TopResourceDTO> topResourcesSince(@Param("from") LocalDateTime from);
}
