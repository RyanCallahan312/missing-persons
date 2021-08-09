package com.azware.missingpersons.repository;

import java.util.List;

import com.azware.missingpersons.model.SightingEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SightingRepository
        extends JpaRepository<SightingEntity, Long>, JpaSpecificationExecutor<SightingEntity> {

    List<SightingEntity> findAll();

    List<SightingEntity> findAll(Specification<SightingEntity> spec);

    Page<SightingEntity> findAll(Specification<SightingEntity> spec, Pageable pageable);

    Page<SightingEntity> findByReportId(long reportId, Pageable pageable);

    SightingEntity findOneById(long id);
}
