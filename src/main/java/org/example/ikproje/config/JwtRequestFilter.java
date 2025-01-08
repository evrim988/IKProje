package org.example.ikproje.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Admin;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.repository.AdminRepository;
import org.example.ikproje.repository.UserRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtManager jwtManager;
    private final AdminRepository adminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                Optional<Long> idOpt = jwtManager.validateToken(token);
                if (idOpt.isPresent()) {
                    Long id = idOpt.get();

                    Optional<Admin> adminOptional = adminRepository.findById(id);
                    if (adminOptional.isPresent()) {
                        Admin admin = adminOptional.get();
                        setAuthentication(admin, "ADMIN");
                    }
                    else {
                        Optional<User> userOptional = userRepository.findById(id);
                        if (userOptional.isPresent()) {
                            User user = userOptional.get();
                            setAuthentication(user, user.getUserRole().toString());
                        }
                    }


                }
            } catch (JWTDecodeException e) {
                logger.error("JwtDecode Exception", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Object principal, String authority) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                principal, null, List.of(new SimpleGrantedAuthority(authority))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
