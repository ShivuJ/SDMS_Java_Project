package com.sdmsproject.sdms.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.ClassService;
import com.sdmsproject.sdms.model.ClassEntity;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository classRepository;
	
	@Override
	public ResponseEntity<String> createClass(ClassEntity classEntity){
		Long id = classEntity.getId();
		
		if(id != null) {
			ClassEntity existingClass = classRepository.findById(id).get();
			
			existingClass.setStuClass(classEntity.getStuClass());
			
			classRepository.save(existingClass);
			
			return ResponseEntity.ok("Success");
		}else {
			classRepository.save(classEntity);
			return ResponseEntity.ok("Success");
		}

	}

	@Override
	public List<ClassEntity> readAllClasses() {
		List<ClassEntity> classList = classRepository.findAll();
		List<ClassEntity> classes = new ArrayList<>();
		
		for(ClassEntity classEntity : classList) {
			ClassEntity addClass = new ClassEntity();
			addClass.setId(classEntity.getId());
			addClass.setStuClass(classEntity.getStuClass());
			addClass.setStatus(classEntity.getStatus());
			classes.add(addClass);
		}
		return classes;
	}

	@Override
	public ResponseEntity<ClassEntity> readAllClassById(Long id) {
		ClassEntity editClass = classRepository.findById(id).get();

		return ResponseEntity.ok(editClass);
	}

	@Override
	public ResponseEntity<String> inactivateClass(Long id) {
		ClassEntity editClass = classRepository.findById(id).get();
		
		editClass.setStatus("N");
		classRepository.save(editClass);
		return ResponseEntity.ok("Success");
	}
	
	
	
}
