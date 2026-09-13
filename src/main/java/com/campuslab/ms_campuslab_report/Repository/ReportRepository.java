package com.campuslab.ms_campuslab_report.Repository;

import com.campuslab.ms_campuslab_report.Model.BookingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<BookingView, Long> {

    @Query("SELECT COUNT(b) FROM BookingView b WHERE b.createdAt >= :since")
    long countBookingsSince(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(b) FROM BookingView b WHERE b.status IN ('EN_PREPARACION','EN_USO') AND b.createdAt >= :since")
    long countActiveSince(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(b) FROM BookingView b WHERE b.status = 'DEVUELTA' AND b.createdAt >= :since")
    long countCompletedSince(@Param("since") LocalDateTime since);

    @Query("SELECT AVG(TIMESTAMPDIFF(MINUTE, b.from, b.to)) FROM BookingView b " +
            "WHERE b.status = 'DEVUELTA' AND b.createdAt >= :since")
    Double avgCycleTimeMinutes(@Param("since") LocalDateTime since);

    @Query("SELECT b.resourceId, COUNT(b) as total FROM BookingView b " +
            "WHERE b.createdAt >= :since GROUP BY b.resourceId ORDER BY total DESC")
    List<Object[]> topResources(@Param("since") LocalDateTime since);
}
