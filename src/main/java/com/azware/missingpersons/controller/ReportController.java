package com.azware.missingpersons.controller;

import java.util.List;

import javax.validation.Valid;

import com.azware.missingpersons.context.RequestContext;
import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.dto.GetReportResponse;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateReportRequest;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.service.ReportService;
import com.azware.missingpersons.util.ModelMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/v1/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final RequestContext requestContext;

    private final ModelMapper modelMapper;
    private final ReportService reportService;

    @Autowired
    public ReportController(ModelMapper modelMapper, ReportService reportService, RequestContext requestContext) {
        this.modelMapper = modelMapper;
        this.reportService = reportService;
        this.requestContext = requestContext;
    }

    @GetMapping("/reports")
    @ResponseStatus(HttpStatus.OK)
    public List<GetReportResponse> getReports(
            @RequestBody(required = false) @Valid SpecificationRequest specificationRequest) {
        logger.info("Getting reports by specification; Correlation ID {} ", this.requestContext.getCorrelationId());
        List<ReportEntity> reportEntities;
        if (specificationRequest == null) {
            reportEntities = reportService.getReports();
        } else {
            reportEntities = reportService.getReports(specificationRequest);
        }
        List<GetReportResponse> reportDTOs = ModelMapperExt.mapList(modelMapper, reportEntities,
                GetReportResponse.class);
        return reportDTOs;
    }

    @GetMapping("{reportId}")
    @ResponseStatus(HttpStatus.OK)
    public GetReportResponse getReport(@PathVariable long reportId) {
        logger.info("Getting all reports; Correlation ID {} ", this.requestContext.getCorrelationId());
        ReportEntity reportEntity = reportService.getReport(reportId);
        GetReportResponse reportDTO = modelMapper.map(reportEntity, GetReportResponse.class);
        return reportDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetReportResponse createReport(@RequestBody @Valid CreateReportRequest createReportRequest) {
        logger.info("Creating report; Correlation ID {} ", this.requestContext.getCorrelationId());
        ReportEntity reportEntity = reportService.createReport(createReportRequest);
        GetReportResponse reportDTO = modelMapper.map(reportEntity, GetReportResponse.class);
        return reportDTO;
    }

    @PutMapping("{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReport(@PathVariable long reportId, @RequestBody @Valid UpdateReportRequest updateReportRequest) {
        logger.info("Updating report with id {}; Correlation ID {} ", reportId, this.requestContext.getCorrelationId());
        reportService.updateReport(reportId, updateReportRequest);
    }

}
