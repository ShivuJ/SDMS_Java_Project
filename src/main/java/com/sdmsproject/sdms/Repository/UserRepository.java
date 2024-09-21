package com.sdmsproject.sdms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {

}
