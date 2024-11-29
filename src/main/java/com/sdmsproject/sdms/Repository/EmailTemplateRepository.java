package com.sdmsproject.sdms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate , Long> {

}
