package br.com.solipy.loja.controllers;

import br.com.solipy.loja.models.dtos.AuthRequestDto;
import br.com.solipy.loja.models.dtos.AuthResponseDto;
import br.com.solipy.loja.models.dtos.RegisterUserDto;
import br.com.solipy.loja.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> signin(@RequestBody @Valid RegisterUserDto registerUser) {
        AuthResponseDto auth = authService.register(registerUser);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(auth);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signin(@RequestBody @Valid AuthRequestDto authRequest) {
        AuthResponseDto auth = authService.signin(authRequest);
        return ResponseEntity.status(HttpStatus.OK.value()).body(auth);
    }

}
