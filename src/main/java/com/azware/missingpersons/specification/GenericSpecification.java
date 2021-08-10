package com.azware.missingpersons.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.azware.missingpersons.exception.InvalidSearchCriteriaException;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

    private transient SearchCriteria searchCriteria;

    public GenericSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Object> arguments = searchCriteria.getArguments();
        Object arg = arguments.get(0);

        switch (searchCriteria.getSearchOperation()) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), arg);
            case LIKE:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()), arg.toString() + "%");
            case IN:
                return root.get(searchCriteria.getKey()).in(arguments);
            default:
                throw new InvalidSearchCriteriaException(
                        "Invalid search operation of: %s".formatted(searchCriteria.getSearchOperation()));
        }
    }
}