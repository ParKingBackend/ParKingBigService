package com.example.parkingbigservice.service;

import com.example.parkingbigservice.model.Report;
import com.example.parkingbigservice.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    private static final Logger LOGGER = Logger.getLogger(ReportService.class.getName());
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }
    public Report findById(Long clientId) {
        return reportRepository.findById(clientId).orElse(null);
    }
}