package com.campuslab.ms_campuslab_report.Model;

import com.nimbusds.jose.shaded.jcip.Immutable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Immutable
public class BookingView {

    @Id
    private Long id;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "student_id")
    private String studentId;

    private String status;

    @Column(name = "booking_from")
    private LocalDateTime from;

    @Column(name = "booking_to")
    private LocalDateTime to;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // getters (sin setters, es de solo lectura)
    public Long getId() { return id; }
    public String getResourceId() { return resourceId; }
    public String getStudentId() { return studentId; }
    public String getStatus() { return status; }
    public LocalDateTime getFrom() { return from; }
    public LocalDateTime getTo() { return to; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
