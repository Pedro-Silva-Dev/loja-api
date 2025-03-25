package br.com.solipy.loja.configs.security;

import br.com.solipy.loja.configs.HashidService;
import br.com.solipy.loja.models.User;
import br.com.solipy.loja.models.dtos.UserRequestDto;
import br.com.solipy.loja.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HashidService hashidService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filter
    ) throws ServletException, IOException {
        final String authHeader = getAuthHeader(request);
        final String jwtToken;
        final String userKey;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filter.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userKey = jwtService.getUserKey(jwtToken);
        request.setAttribute("userRequest", getUserRequest(jwtToken));

        if(userKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Long userId = hashidService.toLongUserKey(userKey);
            User user = this.userRepository.findById(userId).orElse(null);
            if(user != null && jwtService.isTokenValid(jwtToken, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filter.doFilter(request, response);
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private UserRequestDto getUserRequest(String jwtToken) {
        Claims claims = jwtService.getAllClaims(jwtToken);
        return UserRequestDto.builder()
                .setId(hashidService.toLongUserKey(claims.get("userKey").toString()))
                .setUsername(claims.getSubject())
                .build();
    }
}
