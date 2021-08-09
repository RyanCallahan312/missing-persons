package com.azware.missingpersons.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azware.missingpersons.dto.CreateSightingRequest;
import com.azware.missingpersons.dto.PageDTO;
import com.azware.missingpersons.dto.SortDTO;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateSightingRequest;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.model.SightingEntity;
import com.azware.missingpersons.repository.SightingRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.azware.missingpersons.specification.SpecificationHelper;

@Service
public class SightingService {
    private final ModelMapper modelMapper;

    private final SightingRepository sightingRepository;

    private final TransactionTemplate transactionTemplate;

    private final ReportService reportService;

    @Autowired
    public SightingService(ModelMapper modelMapper, SightingRepository sightingRepository,
            PlatformTransactionManager transactionManager, ReportService reportService) {
        this.modelMapper = modelMapper;
        this.sightingRepository = sightingRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.reportService = reportService;
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

        return sightingRepository.save(sightingEntity);
    }

    public void updateSighting(long sightingId, UpdateSightingRequest updateSightingRequest) {
        SightingEntity currentSightingEntity = sightingRepository.findOneById(sightingId);

        SightingEntity updatedSightingEntity = modelMapper.map(updateSightingRequest, SightingEntity.class);
        updatedSightingEntity.setId(sightingId);
        updatedSightingEntity.setReport(currentSightingEntity.getReport());

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
