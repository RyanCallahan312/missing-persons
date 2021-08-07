package com.azware.missingpersons.service;

import java.util.List;

import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.model.ReportEntity;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public List<ReportEntity> getReports(String filter, String sort, int page, int size) {
        return null;
    }

    public ReportEntity getReport(long reportId) {
        return null;
    }

    public ReportEntity createReport(CreateReportRequest createReportRequest) {
        return null;
    }

    public void updateReport(CreateReportRequest createReportRequest) {
    }
    
}
