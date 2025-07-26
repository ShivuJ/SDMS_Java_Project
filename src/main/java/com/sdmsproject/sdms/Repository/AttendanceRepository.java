package com.sdmsproject.sdms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.AttendanceEntity;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
	
	@Query(value = "SELECT atn.attendance, atn.class_id, atn.date, atn.student_id, cls.stu_class, stu.stu_first_name, stu.stu_last_name " +
            "FROM attendance_db AS atn " +
            "LEFT JOIN class_db AS cls ON atn.class_id = cls.id " +
            "LEFT JOIN student_db AS stu ON atn.student_id = stu.id " +
            "WHERE atn.class_id = :class_id", nativeQuery = true)
	List<CustomAttendanceProjection> findByClass(@Param("class_id") Long classId);

}
