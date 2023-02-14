package com.appsdeveloperblog.app.ws.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    public GenericSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Object> arguments = searchCriteria.getArguments();
        Object arg = arguments.get(0);

        return switch (searchCriteria.getSearchOperation()) {
            case EQUALITY -> cb.equal(root.get(searchCriteria.getKey()), arg);
            case GREATER_THAN -> cb.greaterThan(root.get(searchCriteria.getKey()), (Comparable) arg);
            case IN -> root.get(searchCriteria.getKey()).in(arguments);
            case NEGATION -> cb.not(root.get(searchCriteria.getKey()));
            case LESS_THAN -> cb.lessThan(root.get(searchCriteria.getKey()), (Comparable) arg);
            case LIKE ->
                    criteriaQuery.where(cb.like(root.<String>get(searchCriteria.getKey()), "%" + arg + "%")).getRestriction();
            case STARTS_WITH ->
                    criteriaQuery.where(cb.like(root.<String>get(searchCriteria.getKey()), arg + "%")).getRestriction();
        };
    }


    public enum SearchOperation {
        EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, IN
    }

    @Data
    @AllArgsConstructor
    public static class SearchCriteria {

        private String key;
        private SearchOperation searchOperation;
        private boolean isOrOperation;
        private List<Object> arguments;
    }
}
