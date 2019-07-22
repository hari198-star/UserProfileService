package com.user.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.management.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByEmail(String email);
}
