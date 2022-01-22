package com.ndirituedwin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String jwt= getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
            String username=jwtProvider.getUsernamefromjwt(jwt);
            log.info("Username from jwt",username);
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            log.info("Userdetails",userDetails);

            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            log.info("username password authentication token",authenticationToken);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);    }
        filterChain.doFilter(request,response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearertoken= request.getHeader("Authorization");
        if (StringUtils.hasText(bearertoken)&& bearertoken.startsWith("Bearer")){
            log.info("Bearer token", bearertoken.substring(7));
            return bearertoken.substring(7);
        }
        return bearertoken;
    }
}
