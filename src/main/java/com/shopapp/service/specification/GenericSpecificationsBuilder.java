package com.shopapp.service.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.shopapp.service.specification.GenericSpecification.SearchCriteria;
import static com.shopapp.service.specification.GenericSpecification.SearchOperation;

public class GenericSpecificationsBuilder<T> {

    private final List<GenericSpecification.SearchCriteria> params;
    private final List<Specification<T>> specifications;

    public GenericSpecificationsBuilder() {
        this.params = new ArrayList<>();
        this.specifications = new ArrayList<>();
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if (!params.isEmpty()) {
            result = new GenericSpecification<>(params.get(0));
            for (int index = 1; index < params.size(); ++index) {
                GenericSpecification.SearchCriteria searchCriteria = params.get(index);
                result = searchCriteria.isOrOperation() ? Specification.where(result).or(new GenericSpecification<>(searchCriteria))
                        : Specification.where(result).and(new GenericSpecification<>(searchCriteria));
            }
        }
        if (!specifications.isEmpty()) {
            int index = 0;
            if (Objects.isNull(result)) {
                result = specifications.get(index++);
            }
            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).and(specifications.get(index));
            }
        }
        return result;
    }

    public final GenericSpecificationsBuilder<T> with(final String key,
                                                      final SearchOperation searchOperation,
                                                      final boolean isOrOperation,
                                                      final List<Object> arguments) {
        params.add(new SearchCriteria(key, searchOperation, isOrOperation, arguments));
        return this;
    }

    public final GenericSpecificationsBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public final GenericSpecificationsBuilder<T> with(final String key,
                                                      final SearchOperation searchOperation,
                                                      final List<Object> arguments) {

        return with(key, searchOperation, false, arguments);
    }
}
