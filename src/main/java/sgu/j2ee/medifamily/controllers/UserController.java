package sgu.j2ee.medifamily.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@GetMapping("/me")
	public ResponseEntity<User> me() {
		return ResponseEntity.ok(userDetailsServiceImpl.getCurrentUser());
	}
}
