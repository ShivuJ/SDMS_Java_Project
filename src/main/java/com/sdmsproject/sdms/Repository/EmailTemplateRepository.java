package com.sdmsproject.sdms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate , Long> {

	@Query(value="select * from email_template where email_type = :email_type;", nativeQuery = true)
	List<EmailTemplate> findByEmailType(@Param("email_type") String emailType);
}
