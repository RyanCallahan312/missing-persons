package com.azware.missingpersons.service;

import java.time.Instant;
import java.util.List;

import com.azware.missingpersons.context.RequestContext;
import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateReportRequest;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.specification.SpecificationHelper;
import com.azware.missingpersons.repository.ReportRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final RequestContext requestContext;

    private final ModelMapper modelMapper;

    private final ReportRepository reportRepository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ReportService(ModelMapper modelMapper, ReportRepository reportRepository,
            PlatformTransactionManager transactionManager, RequestContext requestContext) {
        this.modelMapper = modelMapper;
        this.reportRepository = reportRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.requestContext = requestContext;
    }

    public List<ReportEntity> getReports(SpecificationRequest specificationRequest) {

        Specification<ReportEntity> specification = SpecificationHelper
                .buildSpecificationFromSpecificationRequest(specificationRequest);

        Pageable page = SpecificationHelper.buildPageableFromSpecificationRequest(specificationRequest);

        Page<ReportEntity> reports = reportRepository.findAll(specification, page);

        return reports.getContent();
    }

    public List<ReportEntity> getReports() {
        return reportRepository.findAll();
    }

    public ReportEntity getReport(long reportId) {
        return reportRepository.findOneById(reportId);
    }

    public ReportEntity createReport(CreateReportRequest createReportRequest) {
        ReportEntity reportEntity = modelMapper.map(createReportRequest, ReportEntity.class);
        reportEntity.setReportTime(Instant.now());
        reportEntity.setFound(false);
        logger.info("Saving new report entity; Correlation ID: {}", this.requestContext.getCorrelationId());
        return reportRepository.save(reportEntity);
    }

    public void updateReport(long reportId, UpdateReportRequest updateReportRequest) {
        if (this.getReport(reportId) == null) {
            throw new IllegalArgumentException("Report with id " + reportId + " does not exist");
        }
        ReportEntity reportEntity = modelMapper.map(updateReportRequest, ReportEntity.class);
        reportEntity.setId(reportId);
        logger.info("Updating report entity with id: {}; Correlation ID: {}",reportId, this.requestContext.getCorrelationId());
        transactionTemplate.execute(status -> reportRepository.save(reportEntity));
    }

}
