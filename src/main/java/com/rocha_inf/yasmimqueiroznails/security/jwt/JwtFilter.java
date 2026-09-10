package com.rocha_inf.yasmimqueiroznails.security.jwt;

import com.rocha_inf.yasmimqueiroznails.security.user.AuthenticatedUser;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.exception.UserNotFoundException;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizedHeader = request.getHeader("Authorization");

        if (Strings.isNotBlank(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {

            String token = authorizedHeader.substring("Bearer ".length());
            Optional<UUID> id = jwtService.validateToken(token);

            if (id.isPresent()) {
                User user = userRepository.findUserByIdAndDeletedAtIsNull(id.get()).orElseThrow(() -> new UserNotFoundException("Nenhum usuário ativo com esse id foi encontrado"));

                AuthenticatedUser authenticatedUser = new AuthenticatedUser(id.get(), user.getEmail(), user.getRole());
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + authenticatedUser.role().name());

                UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(authenticatedUser, null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);

    }
}
