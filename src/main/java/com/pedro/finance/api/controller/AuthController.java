package com.pedro.finance.api.controller;


import com.pedro.finance.api.Exception.AuthException;
import com.pedro.finance.api.dto.AuthResponseDTO;
import com.pedro.finance.api.dto.LoginDTO;
import com.pedro.finance.api.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid LoginDTO loginDTO) {

        try {

            String email = loginDTO.getEmail().toLowerCase();


            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, loginDTO.getSenha())
            );

            return new AuthResponseDTO(jwtService.gerarToken(email));

        }catch (AuthenticationException e){
            throw new AuthException("Email ou senha inválidos");
        }
    }
}
