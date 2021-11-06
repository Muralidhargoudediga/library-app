package com.mediga.library.jwt;

import com.mediga.library.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           String jwtToken = jwtTokenProvider.resolveToken(request);
           if(jwtToken != null && jwtTokenProvider.isValidToken(jwtToken)) {
               Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
       } catch (CustomException ex) {
           //this is very important, since it guarantees the user is not authenticated at all
           SecurityContextHolder.clearContext();
           response.sendError(ex.getHttpStatus().value(), ex.getMessage());
       }


        filterChain.doFilter(request, response);
    }
}
