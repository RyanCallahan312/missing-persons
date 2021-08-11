package com.azware.missingpersons.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;

import com.azware.missingpersons.context.RequestContext;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.repository.ReportRepository;
import com.azware.missingpersons.specification.SpecificationHelper;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

class ReportServiceTest {

    ReportEntity reportEntity;

    @Mock
    RequestContext requestContext;
    
    @Mock
    ReportRepository reportRepository;

    MockedStatic<SpecificationHelper> specificationHelper;

    @InjectMocks
    ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);



        specificationHelper = Mockito.mockStatic(SpecificationHelper.class);
        specificationHelper.when(() -> SpecificationHelper.buildSpecificationFromSpecificationRequest(any(SpecificationRequest.class))).thenReturn(null);
        specificationHelper.when(() -> SpecificationHelper.buildPageableFromSpecificationRequest(any(SpecificationRequest.class))).thenReturn(null);
        
        reportEntity = new ReportEntity();
        reportEntity.setId(1L);
        reportEntity.setMissingPersonName("azware");
        reportEntity.setReporterName("azware reporter");
        reportEntity.setDescription("software company");
        reportEntity.setReportTime(Instant.now());
        reportEntity.setLastSeenTime(Instant.now());
        reportEntity.setLastKnownLocation("NY office");
        reportEntity.setAdditionalInfo("They be missing");
        reportEntity.setImageUri("https://i.imgur.com/ccXh2VQ.jpeg");
        reportEntity.setFound(false);

        when(reportRepository.findAll()).thenReturn(Arrays.asList(reportEntity));
        when(reportRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<ReportEntity>(Arrays.asList(reportEntity)));
        when(reportRepository.findOneById(anyLong())).thenReturn(reportEntity);
        when(reportRepository.save(any(ReportEntity.class))).thenReturn(reportEntity);
    
    }
}
