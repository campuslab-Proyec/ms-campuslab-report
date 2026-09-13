package com.campuslab.ms_campuslab_report.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopResourceDto {
    private String resourceId;
    private long totalBookings;
}
