package com.sdmsproject.sdms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdmsproject.sdms.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {
	
	@Query(value = "SELECT * FROM user_db WHERE email = :email AND password = :password", nativeQuery = true)
	List<UserEntity> findUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
