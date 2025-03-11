package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Repository.EmailTemplateRepository;
import com.sdmsproject.sdms.Repository.SubjectRepository;
import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.EmailService;
import com.sdmsproject.sdms.Service.UserService;
//import com.sdmsproject.sdms.model.ClassEntity;
import com.sdmsproject.sdms.model.EmailTemplate;
//import com.sdmsproject.sdms.model.SubjectEntity;
import com.sdmsproject.sdms.model.UserEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;
	
	@Autowired
	ClassRepository classRepository;	
	
	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	private EmailTemplateRepository emailTempRepo;

	@Override
	public ResponseEntity<String> createUser(UserEntity user) {
		Long id = user.getId();
		String fullName = user.getFirstName().concat(" ").concat(user.getLastName());
		
		// cu (Created and Update) user name
		String cuName = getFullNameByCookie();
		LocalDate currentDate = LocalDate.now();
		String type = "Registration";
		if (id != null) {

			UserEntity existingUser = userRepository.findById(id).get();

			existingUser.setId(user.getId());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
			existingUser.setRole(user.getRole());
			existingUser.setPhone(user.getPhone());
			existingUser.setTeacherClass(user.getTeacherClass());
			existingUser.setSubject(user.getSubject());
			existingUser.setPassword(user.getPassword());
			existingUser.setStatus(user.getStatus());
			existingUser.setDateOfJoining(user.getDateOfJoining());
			existingUser.setEmploymentStatus(user.getEmploymentStatus());
			existingUser.setQualification(user.getQualification());
			existingUser.setUpdatedBy(cuName);
			existingUser.setUpdatedOn(currentDate);

			userRepository.save(existingUser);
			return ResponseEntity.ok("Success");
		} else {
			user.setId(user.getId());
			user.setFirstName(user.getFirstName());
			user.setLastName(user.getLastName());
			user.setEmail(user.getEmail());
			user.setRole(user.getRole());
			user.setPhone(user.getPhone());
			
//			ClassEntity classEntity = classRepository.findById(user.getTeacherClass());
			user.setTeacherClass(user.getTeacherClass());
			
//			SubjectEntity subjectEntity = subjectRepository.findById(user.getSubject());
			user.setSubject(user.getSubject());
			
			user.setPassword(user.getPassword());
			user.setStatus(user.getStatus());
			user.setDateOfJoining(user.getDateOfJoining());
			user.setEmploymentStatus(user.getEmploymentStatus());
			user.setQualification(user.getQualification());
			user.setCreatedBy(cuName);
			user.setCreatedOn(currentDate);
			user.setUpdatedBy(cuName);
			user.setUpdatedOn(currentDate);
			userRepository.save(user);

			List<EmailTemplate> emailTemplates = emailTempRepo.findAll();
			for (EmailTemplate emailTemplate : emailTemplates) {
				emailService.SendSimpleMail(user.getEmail(), emailTemplate.getSubject(), emailTemplate.getTemplate(),
						fullName, user.getEmail(), user.getPassword(), type);
			}
			return ResponseEntity.ok("Success");
		}

	}

	@Override
	public List<UserEntity> readAllUsers() {

		List<UserEntity> userList = userRepository.findAll();
		List<UserEntity> users = new ArrayList<>();

		for (UserEntity userEntity : userList) {
			UserEntity user = new UserEntity();

			user.setId(userEntity.getId());
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setEmail(userEntity.getEmail());
			user.setRole(userEntity.getRole());
			user.setPhone(userEntity.getPhone());
			user.setTeacherClass(userEntity.getTeacherClass());
			user.setSubject(userEntity.getSubject());
			user.setPassword(userEntity.getPassword());
			user.setStatus(userEntity.getStatus());
			users.add(user);
		}

		return users;

	}

	@Override
	public ResponseEntity<String> inactivateUser(Long id) {
		UserEntity user = userRepository.findById(id).get();
		user.setStatus("N");

		userRepository.save(user);

		return ResponseEntity.ok("Success");
	}

	@Override
	public ResponseEntity<UserEntity> readUserById(Long id) {
		UserEntity user = userRepository.findById(id).get();
		return ResponseEntity.ok(user);
	}
	
	// get cookies using name
	private String getCookie(String name) {
		Cookie[] cookie = request.getCookies();
		
		if(cookie != null) {
			for(Cookie cookies: cookie) {
				if(cookies.getName().equals(name)) {
					return cookies.getValue();
				
				}
				
			}
		}
		return "Unknown";
	}
	
	private String getFullNameByCookie() {
		String firstName = getCookie("username");
		String lastName = getCookie("userLastName");
		
		return firstName + " " + lastName;
	}

}
