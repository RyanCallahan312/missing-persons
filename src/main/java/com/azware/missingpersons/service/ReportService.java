package com.azware.missingpersons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.azware.missingpersons.dto.CreateReportRequest;
import com.azware.missingpersons.dto.FilterDTO;
import com.azware.missingpersons.dto.SortDTO;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;
import com.azware.missingpersons.model.ReportEntity;
import com.azware.missingpersons.specification.GenericSpecificationBuilder;
import com.azware.missingpersons.constant.SearchOperation;
import com.azware.missingpersons.repository.ReportRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<ReportEntity> getReports(SpecificationRequest specificationRequest) {

        GenericSpecificationBuilder<ReportEntity> specificationBuilder = new GenericSpecificationBuilder<>();

        for (FilterDTO filterDTO : specificationRequest.getFilters()) {

            SearchOperation searchOperation = SearchOperation.forName(filterDTO.getOperator().toUpperCase(Locale.ENGLISH));
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
        return null;
    }

    public ReportEntity createReport(CreateReportRequest createReportRequest) {
        return null;
    }

    public void updateReport(CreateReportRequest createReportRequest) {
    }

}
