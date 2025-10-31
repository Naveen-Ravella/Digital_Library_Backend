package com.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Users;
import com.library.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<Users> addUser(@RequestBody Users user) {
		try {
			Users savedUser = userService.saveUser(user);
			return ResponseEntity.ok(savedUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/getall")
	public List<Users> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/getone/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable int id) {
		Optional<Users> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Users> updateUser(@RequestBody Users user, @PathVariable int id) {
		Users updatedUser = userService.updateUser(user, id);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		try {
			userService.deleteuser(id);
			return ResponseEntity.ok("User deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error deleting user");
		}
	}

	@GetMapping("/search/email/{email}")
	public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
		Optional<Users> user = userService.findByEmail(email);
		return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/search/name/{name}")
	public List<Users> searchUsersByName(@PathVariable String name) {
		return userService.findByUserName(name);
	}

	@GetMapping("/active")
	public List<Users> getActiveUsers() {
		return userService.getActiveUsers();
	}

	@GetMapping("/status/{status}")
	public List<Users> getUsersByStatus(@PathVariable String status) {
		return userService.getUsersByStatus(status);
	}

	@PutMapping("/suspend/{id}")
	public ResponseEntity<Users> suspendUser(@PathVariable int id) {
		Users suspendedUser = userService.suspendUser(id);
		if (suspendedUser != null) {
			return ResponseEntity.ok(suspendedUser);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/activate/{id}")
	public ResponseEntity<Users> activateUser(@PathVariable int id) {
		Users activatedUser = userService.activateUser(id);
		if (activatedUser != null) {
			return ResponseEntity.ok(activatedUser);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/count/active")
	public ResponseEntity<Long> getActiveUsersCount() {
		long count = userService.getActiveUsersCount();
		return ResponseEntity.ok(count);
	}

}
