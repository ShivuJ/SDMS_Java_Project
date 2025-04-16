package com.sdmsproject.sdms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity , Long> {

	@Query(value="SELECT COUNT(CASE WHEN stu_class= :stuClass THEN 1 END) AS total_student FROM student_db;", nativeQuery = true)
	String countByClass(@Param("stuClass") String stuClass);
	
	@Query(value="select s.id as id, s.stu_first_name, s.stu_last_name, s.stu_class, s.roll_number from student_db s LEFT JOIN class_db cls on CAST(s.stu_class As BIGINT) = cls.id where CAST(s.stu_class As BIGINT) = :classId", nativeQuery = true)
	List<StudentEntity> findStudentByClass(@Param("classId") Long classId);
}
