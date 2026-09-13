package com.campuslab.ms_campuslab_report.Controller;

import com.campuslab.ms_campuslab_report.Dto.KpiResponse;
import com.campuslab.ms_campuslab_report.Dto.TopResourceDto;
import com.campuslab.ms_campuslab_report.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/kpis")
    public ResponseEntity<KpiResponse> kpis(@RequestParam(defaultValue = "last24h") String range) {
        return ResponseEntity.ok(service.getKpis(range));
    }

    @GetMapping("/top-resources")
    public ResponseEntity<List<TopResourceDto>> topResources(@RequestParam(defaultValue = "last7d") String range) {
        return ResponseEntity.ok(service.getTopResources(range));
    }
}
