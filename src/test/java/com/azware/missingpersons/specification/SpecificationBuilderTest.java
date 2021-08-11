package com.azware.missingpersons.specification;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

class SpecificationBuilderTest {
    
    GenericSpecificationBuilder<TestEntity> builder;

    @BeforeEach
    void setUp() {
        builder = new GenericSpecificationBuilder<TestEntity>();
    }

    @Test
    void build_ShouldMakeNewSpecification () {
        Specification<TestEntity> spec = assertDoesNotThrow(() -> builder.build());
        assertNull(spec);
    }

    private class TestEntity {}
}
