package com.azware.missingpersons.repository;

import java.util.List;

import com.azware.missingpersons.model.ReportEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface ReportRepository extends JpaRepository<ReportEntity, Long>, JpaSpecificationExecutor<ReportEntity> {

    List<ReportEntity> findAll(Specification<ReportEntity> spec);
    Page<ReportEntity> findAll(Specification<ReportEntity> spec, Pageable pageable);
    ReportEntity findOneById(long id);

}
