package campuslab.ms_campuslab_report.Controller;

import campuslab.ms_campuslab_report.Dto.ReportKpisDTO;
import campuslab.ms_campuslab_report.Dto.TopResourceDTO;
import campuslab.ms_campuslab_report.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/kpis")
    public ReportKpisDTO getKpis(@RequestParam(defaultValue = "last24h") String range) {
        return reportService.calculateKpis(range);
    }

    @GetMapping("/top-resources")
    public List<TopResourceDTO> getTopResources(@RequestParam(defaultValue = "last7d") String range) {
        return reportService.topResources(range);
    }
}
