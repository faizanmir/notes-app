package com.notes.notesapp.user.auth.filter;

import com.notes.notesapp.user.auth.AppUserDetailsService;
import com.notes.notesapp.user.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private AppUserDetailsService appUserDetailsService;
    private JWTUtils jwtUtil;


    @Autowired
    public void setAppUserDetailsService(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Autowired
    public void setJwtUtil(JWTUtils jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader =  request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        String userName;
        String jwt = (authorizationHeader!=null &&!authorizationHeader.isEmpty())?authorizationHeader.substring(7):"";
        if(!jwt.isEmpty()) {
            userName = jwtUtil.getUserName(jwt);
            System.out.println(userName);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("Inside this");
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validate(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else {
                    System.out.println("JWT validation failed");
                }
            }
        }
        else{
            System.out.println("is JWT empty");
        }
        filterChain.doFilter(request,response);
    }
}
