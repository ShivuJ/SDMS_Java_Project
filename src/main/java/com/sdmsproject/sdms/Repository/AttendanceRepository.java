package com.sdmsproject.sdms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.AttendanceEntity;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
	
	@Query(value = "SELECT atn.id, atn.attendance, atn.class_id, atn.date, atn.student_id, " +
            "atn.created_by, atn.created_on, atn.updated_on, atn.updated_by, " +
            "cls.id AS cls_id, cls.stu_class, " +
            "stu.id AS stu_id, stu.stu_first_name, stu.stu_last_name, stu.roll_number " +
            "FROM attendance_db AS atn " +
            "LEFT JOIN class_db AS cls ON atn.class_id = cls.id " +
            "LEFT JOIN student_db AS stu ON atn.student_id = stu.id " +
            "WHERE atn.class_id = :class_id", nativeQuery = true)
	List<AttendanceEntity> findByClass(@Param("class_id") Long classId);

	// Query to get all attendance records with complete student and class information
	@Query(value = "SELECT atn.id, atn.attendance, atn.class_id, atn.date, atn.student_id, " +
            "atn.created_by, atn.created_on, atn.updated_on, atn.updated_by, " +
            "cls.id AS cls_id, cls.stu_class, " +
            "stu.id AS stu_id, stu.stu_first_name, stu.stu_last_name, stu.roll_number " +
            "FROM attendance_db AS atn " +
            "LEFT JOIN class_db AS cls ON atn.class_id = cls.id " +
            "LEFT JOIN student_db AS stu ON atn.student_id = stu.id", nativeQuery = true)
	List<AttendanceEntity> findAllAttendanceWithDetails();

	// Query to get attendance by class with complete student details including roll number
	@Query(value = "SELECT atn.id, atn.attendance, atn.class_id, atn.date, atn.student_id, " +
            "atn.created_by, atn.created_on, atn.updated_on, atn.updated_by, " +
            "cls.id AS cls_id, cls.stu_class, " +
            "stu.id AS stu_id, stu.stu_first_name, stu.stu_last_name, stu.roll_number " +
            "FROM attendance_db AS atn " +
            "INNER JOIN class_db AS cls ON atn.class_id = cls.id " +
            "INNER JOIN student_db AS stu ON atn.student_id = stu.id " +
            "WHERE atn.class_id = :class_id " +
            "ORDER BY atn.date DESC", nativeQuery = true)
	List<AttendanceEntity> findByClassWithStudentDetails(@Param("class_id") Long classId);

	// Query to get attendance records by date
	@Query(value = "SELECT atn.id, atn.attendance, atn.class_id, atn.date, atn.student_id, " +
            "atn.created_by, atn.created_on, atn.updated_on, atn.updated_by, " +
            "cls.id AS cls_id, cls.stu_class, " +
            "stu.id AS stu_id, stu.stu_first_name, stu.stu_last_name, stu.roll_number " +
            "FROM attendance_db AS atn " +
            "INNER JOIN class_db AS cls ON atn.class_id = cls.id " +
            "INNER JOIN student_db AS stu ON atn.student_id = stu.id " +
            "WHERE atn.date = :date AND atn.class_id = :class_id", nativeQuery = true)
	List<AttendanceEntity> findByDateAndClass(@Param("date") String date, @Param("class_id") Long classId);

	// Query to get attendance by student
	@Query(value = "SELECT atn.id, atn.attendance, atn.class_id, atn.date, atn.student_id, " +
            "atn.created_by, atn.created_on, atn.updated_on, atn.updated_by, " +
            "cls.id AS cls_id, cls.stu_class, " +
            "stu.id AS stu_id, stu.stu_first_name, stu.stu_last_name, stu.roll_number " +
            "FROM attendance_db AS atn " +
            "INNER JOIN class_db AS cls ON atn.class_id = cls.id " +
            "INNER JOIN student_db AS stu ON atn.student_id = stu.id " +
            "WHERE atn.student_id = :student_id", nativeQuery = true)
	List<AttendanceEntity> findByStudent(@Param("student_id") Long studentId);

}