package com.campuslab.ms_campuslab_report.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KpiResponse {
    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    private double avgCycleTimeMinutes;
}