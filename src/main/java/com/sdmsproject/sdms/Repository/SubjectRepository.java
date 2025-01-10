package com.sdmsproject.sdms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.SubjectEntity;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

	SubjectEntity findById(SubjectEntity subject);

}
