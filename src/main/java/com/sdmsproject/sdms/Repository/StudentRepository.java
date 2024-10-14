package com.sdmsproject.sdms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity , Long> {

}
