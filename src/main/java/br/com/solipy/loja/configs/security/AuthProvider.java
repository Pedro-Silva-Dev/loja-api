package br.com.solipy.loja.configs.security;

import br.com.solipy.loja.models.User;
import br.com.solipy.loja.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if(authentication.getName() != null && authentication.getCredentials() != null) {
            return authenticateAgainstThirdPartyAndGetAuthentication(authentication.getName(), authentication.getCredentials().toString(), authentication.getAuthorities());
        }
        return null;
    }

    private Authentication authenticateAgainstThirdPartyAndGetAuthentication(String name, String password, Collection<? extends GrantedAuthority> roles) {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(name, password, roles);
        return new UsernamePasswordAuthenticationToken(user, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
