package com.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Users;
import com.library.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Users saveUser(Users u) {
		return userRepository.save(u);
	}
	// Get All User

	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<Users> getUserById(int uid) {
		return userRepository.findById(uid);
	}

	public Users updateUser(Users u, int id) {
		Optional<Users> theUser = userRepository.findById(id);

		Users newUser = theUser.get();
		newUser.setUsername(u.getUsername());
		newUser.setUseremail(u.getUseremail());
		newUser.setPhno(u.getPhno());
		newUser.setRegistrationDate(u.getRegistrationDate());
		newUser.setAddress(u.getAddress());
		newUser.setStatus(u.getStatus());

		return userRepository.save(newUser);
	}

	public void deleteuser(int id) {
		userRepository.deleteById(id);
	}

	public Optional<Users> findByEmail(String email) {
		return userRepository.findByUseremail(email);
	}

	public List<Users> findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
	// Get active users
    public List<Users> getActiveUsers() {
        return userRepository.findActiveUsers();
    }
    
    // Get users by status
    public List<Users> getUsersByStatus(String status) {
        return userRepository.findByStatus(status);
    }

	// Suspend a user

	public Users suspendUser(int userId) {
		Optional<Users> user = userRepository.findById(userId);
		if (user.isPresent()) {
			Users suspendUser = user.get();
			suspendUser.setStatus("Suspend");
			return userRepository.save(suspendUser);
		}
		return null;
	}
	// Activate a user

	public Users activateUser(int userId) {
		Optional<Users> user = userRepository.findById(userId);
		if (user.isPresent()) {
			Users suspendUser = user.get();
			suspendUser.setStatus("Active");
			return userRepository.save(suspendUser);
		}
		return null;
	}

	// Get total active users count
	public long getActiveUsersCount() {
		return userRepository.countActiveUsers();
	}
	
	
}
