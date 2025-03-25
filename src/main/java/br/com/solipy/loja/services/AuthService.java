package br.com.solipy.loja.services;

import br.com.solipy.loja.configs.security.AuthProvider;
import br.com.solipy.loja.configs.security.JwtService;
import br.com.solipy.loja.models.Role;
import br.com.solipy.loja.models.User;
import br.com.solipy.loja.models.dtos.AuthRequestDto;
import br.com.solipy.loja.models.dtos.AuthResponseDto;
import br.com.solipy.loja.models.dtos.RegisterUserDto;
import br.com.solipy.loja.repositories.RoleRepository;
import br.com.solipy.loja.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthProvider authProvider;

    public AuthResponseDto signin(AuthRequestDto authRequest) {
        var user = userRepository.findByEmail(authRequest.username()).orElseThrow();
        Boolean matches = passwordEncoder.matches(authRequest.password(), user.getPassword());
        if(matches) {
            authProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password(), user.getAuthorities()));
            var jwtToken = jwtService.generateToken(user);
            return AuthResponseDto.builder().token(jwtToken).build();
        }
        return null;
    }

    public AuthResponseDto register(RegisterUserDto registerUser) {
        var user = User
                .builder()
                .name(registerUser.name())
                .email(registerUser.email())
                .password(passwordEncoder.encode(registerUser.password()))
                .phone(registerUser.phone())
                .roles(getRoles())
                .active(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        User userSave = userRepository.save(user);
        var jwtToken = jwtService.generateToken(userSave);
        return AuthResponseDto.builder().token(jwtToken).build();
    }

    private List<Role> getRoles() {
        Optional<Role> optionalRole = roleRepository.findByRoleUser();
        return optionalRole.map(List::of).orElse(null);
    }
}
