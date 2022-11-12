package com.example.instazoo.security.jwt;

import com.example.instazoo.entity.User;
import com.example.instazoo.security.SecurityConstant;
import com.example.instazoo.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String jwt = getJwtFromRequest(request);
           if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
               Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
               User userDetails = customUserDetailsService.loadUserById(userId);

               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                       userDetails, null, Collections.emptyList()
               );

               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
       } catch (Exception ex) {
           log.error("Could not set user authentication");
       }
       filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearToken = request.getHeader(SecurityConstant.HEADER_STRING);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return bearToken.split(" ")[1]; // get bear token without name Bearer and space
        }
        return null;
    }

}
