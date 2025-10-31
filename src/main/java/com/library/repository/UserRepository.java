package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByUseremail(String email);
	
	List<Users> findByUsername(String username);
	List<Users> findByStatus(String status);
	
	@Query("select u from Users u where u.status='Active'")
	List<Users> findActiveUsers();
	
	@Query("select count(u) from Users u where u.status='Active'")
	Long countActiveUsers();
	
}
