package com.sdmsproject.sdms.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.StudentRepository;
import com.sdmsproject.sdms.Service.StudentService;
import com.sdmsproject.sdms.model.StudentEntity;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public ResponseEntity<String> createStudent(StudentEntity stuEntity) {
		Long id = stuEntity.getId();
		if(id!=null) {
			return null;
		}else {
			studentRepository.save(stuEntity);
			return ResponseEntity.ok("Success");
		}
		
	}

	@Override
	public List<StudentEntity> readAllStudent() {
		List<StudentEntity> stuList = studentRepository.findAll();
		List<StudentEntity> student = new ArrayList<>();
		
		for(StudentEntity studentEntity : stuList) {
			
			StudentEntity getStudent = new StudentEntity();
			
			getStudent.setId(studentEntity.getId());
			getStudent.setStuFirstName(studentEntity.getStuFirstName());
			getStudent.setStuLastName(studentEntity.getStuLastName());
			getStudent.setStuContact(studentEntity.getStuContact());
			getStudent.setStuEmail(studentEntity.getStuEmail());
			getStudent.setStuClass(studentEntity.getStuClass());
			getStudent.setStuWhatsapp(studentEntity.getStuWhatsapp());
			getStudent.setRole(studentEntity.getRole());
			getStudent.setStatus(studentEntity.getStatus());
			getStudent.setStuPass(studentEntity.getStuPass());
			student.add(getStudent);
			
		}
		return student;
	}

	@Override
	public ResponseEntity<StudentEntity> readStuById(Long id) {
		StudentEntity getStudent = studentRepository.findById(id).get();
		return ResponseEntity.ok(getStudent);
	}

	
}
