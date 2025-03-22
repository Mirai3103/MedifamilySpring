package sgu.j2ee.medifamily.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;
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
}
