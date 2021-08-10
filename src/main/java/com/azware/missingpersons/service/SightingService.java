package com.azware.missingpersons.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azware.missingpersons.context.RequestContext;
import com.azware.missingpersons.dto.CreateSightingRequest;
import com.azware.missingpersons.dto.PageDTO;
import com.azware.missingpersons.dto.SortDTO;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateSightingRequest;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.model.SightingEntity;
import com.azware.missingpersons.repository.SightingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.azware.missingpersons.specification.SpecificationHelper;

@Service
public class SightingService {

    private static final Logger logger = LoggerFactory.getLogger(SightingService.class);
    private final RequestContext requestContext;

    private final ModelMapper modelMapper;

    private final SightingRepository sightingRepository;

    private final TransactionTemplate transactionTemplate;

    private final ReportService reportService;

    @Autowired
    public SightingService(ModelMapper modelMapper, SightingRepository sightingRepository,
            PlatformTransactionManager transactionManager, ReportService reportService, RequestContext requestContext) {
        this.modelMapper = modelMapper;
        this.sightingRepository = sightingRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.reportService = reportService;
        this.requestContext = requestContext;
    }

    public List<SightingEntity> getSightings(SpecificationRequest specificationRequest) {

        Specification<SightingEntity> specification = SpecificationHelper
                .buildSpecificationFromSpecificationRequest(specificationRequest);

        Pageable page = SpecificationHelper.buildPageableFromSpecificationRequest(specificationRequest);

        Page<SightingEntity> sightings = sightingRepository.findAll(specification, page);

        return sightings.getContent();
    }

    public List<SightingEntity> getSightings(long reportId) {

        Pageable page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Direction.DESC, "sightingTime"));

        Page<SightingEntity> sightings = sightingRepository.findByReportId(reportId, page);

        return sightings.getContent();
    }

    public SightingEntity getSighting(long sightingId) {
        return sightingRepository.findOneById(sightingId);
    }

    public SightingEntity createSighting(CreateSightingRequest createSightingRequest) {
        ReportEntity report = reportService.getReport(createSightingRequest.getReportId());
        if (report == null) {
            throw new IllegalArgumentException("Invalid Report id");
        }

        SightingEntity sightingEntity = modelMapper.map(createSightingRequest, SightingEntity.class);
        sightingEntity.setReport(report);

        logger.info("Saving new sighting entity; Correlation ID: {}", this.requestContext.getCorrelationId());
        return sightingRepository.save(sightingEntity);
    }

    public void updateSighting(long sightingId, UpdateSightingRequest updateSightingRequest) {
        SightingEntity currentSightingEntity = sightingRepository.findOneById(sightingId);

        if (currentSightingEntity == null) {
            throw new IllegalArgumentException("Invalid Sighting id");
        }

        SightingEntity updatedSightingEntity = modelMapper.map(updateSightingRequest, SightingEntity.class);
        updatedSightingEntity.setId(sightingId);
        updatedSightingEntity.setReport(currentSightingEntity.getReport());

        logger.info("Updating sighting entity with sighting id: {}; Correlation ID: {}",sightingId,  this.requestContext.getCorrelationId());
        transactionTemplate.execute(status -> sightingRepository.save(updatedSightingEntity));
    }

    public List<SightingEntity> getMostRecentSightings() {
        PageDTO page = new PageDTO(0, Integer.MAX_VALUE);
        SortDTO sort = new SortDTO("sightingTime", Direction.DESC.toString());

        SpecificationRequest specificationRequest = new SpecificationRequest();
        specificationRequest.setFilters(new ArrayList<>());
        specificationRequest.setSorts(Arrays.asList(sort));
        specificationRequest.setFiltersOrOperation(true);
        specificationRequest.setPage(page);

        return getSightings(specificationRequest);
    }

}
