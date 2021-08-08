package com.azware.missingpersons.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.dto.FilterDTO;
import com.azware.missingpersons.dto.SortDTO;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateReportRequest;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.specification.GenericSpecificationBuilder;
import com.azware.missingpersons.constant.SearchOperation;
import com.azware.missingpersons.repository.ReportRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ReportService {

    private final ModelMapper modelMapper;

    private final ReportRepository reportRepository;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ReportService(ModelMapper modelMapper, ReportRepository reportRepository, PlatformTransactionManager transactionManager) {
        this.modelMapper = modelMapper;
        this.reportRepository = reportRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public List<ReportEntity> getReports(SpecificationRequest specificationRequest) {

        GenericSpecificationBuilder<ReportEntity> specificationBuilder = new GenericSpecificationBuilder<>();

        for (FilterDTO filterDTO : specificationRequest.getFilters()) {

            SearchOperation searchOperation = SearchOperation
                    .forName(filterDTO.getOperator().toUpperCase(Locale.ENGLISH));
            if (searchOperation == null) {
                throw new InvalidSearchCriteriaException("Invalid Search Operation");
            }

            specificationBuilder.with(filterDTO.getFieldName(), searchOperation,
                    specificationRequest.isFiltersOrOperation(), filterDTO.getValues());
        }
        Specification<ReportEntity> specification = specificationBuilder.build();

        List<Order> sortOrders = new ArrayList<>();

        for (SortDTO sortDTO : specificationRequest.getSorts()) {
            Order sortOrder = new Order(Direction.valueOf(sortDTO.getDirection()), sortDTO.getFieldName());
            sortOrders.add(sortOrder);
        }
        Sort sort = Sort.by(sortOrders);

        Pageable page = PageRequest.of(specificationRequest.getPage().getPageNumber(),
                specificationRequest.getPage().getPageSize(), sort);

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
        reportEntity.setIsFound(false);
        return reportRepository.save(reportEntity);
    }

    public void updateReport(UpdateReportRequest createReportRequest) {
        ReportEntity reportEntity = modelMapper.map(createReportRequest, ReportEntity.class);
        transactionTemplate.execute(status -> reportRepository.save(reportEntity));
    }

}
