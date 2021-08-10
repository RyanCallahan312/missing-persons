package com.azware.missingpersons.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.azware.missingpersons.constant.SearchOperation;
import com.azware.missingpersons.dto.FilterDTO;
import com.azware.missingpersons.dto.SortDTO;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationHelper {

    private SpecificationHelper() {
    }

    public static <T> Specification<T> buildSpecificationFromSpecificationRequest(
            SpecificationRequest specificationRequest) {
        GenericSpecificationBuilder<T> specificationBuilder = new GenericSpecificationBuilder<>();

        for (FilterDTO filterDTO : specificationRequest.getFilters()) {

            SearchOperation searchOperation = SearchOperation
                    .forName(filterDTO.getOperator().toUpperCase(Locale.ENGLISH));
            if (searchOperation == null) {
                throw new InvalidSearchCriteriaException("Invalid Search Operation");
            }

            specificationBuilder.with(filterDTO.getFieldName(), searchOperation,
                    specificationRequest.isFiltersOrOperation(), filterDTO.getValues());
        }
        Specification<T> specification = specificationBuilder.build();
        return specification;
    }

    public static Pageable buildPageableFromSpecificationRequest(SpecificationRequest specificationRequest) {

        List<Order> sortOrders = new ArrayList<>();

        for (SortDTO sortDTO : specificationRequest.getSorts()) {
            Order sortOrder = new Order(Direction.valueOf(sortDTO.getDirection().toUpperCase(Locale.ENGLISH)),
                    sortDTO.getFieldName());
            sortOrders.add(sortOrder);
        }
        Sort sort = Sort.by(sortOrders);

        Pageable page = PageRequest.of(specificationRequest.getPage().getPageNumber(),
                specificationRequest.getPage().getPageSize(), sort);

        return page;
    }
}