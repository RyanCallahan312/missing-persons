package com.azware.missingpersons.specification;

import com.azware.missingpersons.constant.SearchOperation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class SpecificationFactory<T> {

    public Specification<T> isEqual(String key, Object arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.EQUALS, Collections.singletonList(arg)).build();
    }

    public Specification<T> isLike(String key, Object arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.LIKE, Collections.singletonList(arg)).build();
    }

    public Specification<T> isIn(String key, List<Object> args) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.IN, args).build();
    }
}