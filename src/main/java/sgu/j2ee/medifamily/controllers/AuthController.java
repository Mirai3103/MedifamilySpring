package sgu.j2ee.medifamily.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;
import sgu.j2ee.medifamily.dtos.LoginRequest;
import sgu.j2ee.medifamily.dtos.LoginResponse;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.services.AuthService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;

@RestController()
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private  final AuthService authService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Void>  register(@Valid @RequestBody RegisterRequest user) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/users/" + authService.register(user).getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)  {
        String username = request.getUsername();
        String password = request.getPassword();



        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(userDetailsService.getCurrentUser());
    }
}
