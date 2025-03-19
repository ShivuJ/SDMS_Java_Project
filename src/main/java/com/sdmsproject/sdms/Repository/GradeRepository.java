package com.sdmsproject.sdms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.GradeEntity;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

}
