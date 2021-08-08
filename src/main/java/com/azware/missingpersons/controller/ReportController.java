package com.azware.missingpersons.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Arrays;

import javax.validation.Valid;

import com.azware.missingpersons.dto.BadRequestResponse;
import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.dto.GetReportResponse;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.service.ReportService;
import com.azware.missingpersons.util.ModelMapperExt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/v1/report")
public class ReportController {

    private ModelMapper modelMapper;
    private ReportService reportService;

    @Autowired
    public ReportController(ModelMapper modelMapper, ReportService reportService) {
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    @ResponseStatus(HttpStatus.OK)
    public List<GetReportResponse> getReports(
            @RequestBody(required = false) @Valid SpecificationRequest specificationRequest) {
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
        ReportEntity reportEntity = reportService.getReport(reportId);
        GetReportResponse reportDTO = modelMapper.map(reportEntity, GetReportResponse.class);
        return reportDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetReportResponse createReport(@RequestBody CreateReportRequest createReportRequest) {
        ReportEntity reportEntity = reportService.createReport(createReportRequest);
        GetReportResponse reportDTO = modelMapper.map(reportEntity, GetReportResponse.class);
        return reportDTO;
    }

    @PutMapping("{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReport(@PathVariable CreateReportRequest createReportRequest) {
        reportService.updateReport(createReportRequest);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, InvalidSearchCriteriaException.class, InvalidDataAccessApiUsageException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BadRequestResponse handleInvalidArgumentsExceptions(RuntimeException  e) {
        String message = "Bad Request";
        return new BadRequestResponse(message, Arrays.toString(e.getStackTrace()));
    }

}
