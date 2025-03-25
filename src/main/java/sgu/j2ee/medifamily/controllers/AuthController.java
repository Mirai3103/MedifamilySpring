package sgu.j2ee.medifamily.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;
import sgu.j2ee.medifamily.dtos.LoginDTO;
import sgu.j2ee.medifamily.dtos.LoginResponseDTO;
import sgu.j2ee.medifamily.dtos.RegisterDTO;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;

@Controller("/api/auth")
@AllArgsConstructor
public class AuthController {
    private  final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ServerResponse register(@Valid @RequestBody RegisterDTO user) throws URISyntaxException {
        return ServerResponse.created(new URI("/api/users/" + userDetailsService.register(user).getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request)  {
        String username = request.getUsername();
        String password = request.getPassword();



        return ResponseEntity.ok(userDetailsService.login(username, password));
    }

    @PostMapping()
}
