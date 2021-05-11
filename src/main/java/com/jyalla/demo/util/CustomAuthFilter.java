package com.jyalla.demo.util;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class CustomAuthFilter extends OncePerRequestFilter {

    static Logger logger = LoggerFactory.getLogger(CustomAuthFilter.class);

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("AUTHORIZATION");
        String bearer = null;
        try {
            if (StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken, "Bearer "))
                bearer = bearerToken.substring(7, bearerToken.length());

            if (StringUtils.hasText(bearer) && jwtUtil.validateToken(bearer)) {
                UserDetails user = new User(jwtUtil.findUserByToken(bearer), "", jwtUtil.findRolesByToken(bearer));
                var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            } else {
                logger.info("Cannot set Token");
            }
        } catch (ExpiredJwtException ex) {
            request.setAttribute("exception", ex);
            throw ex;
        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
            throw ex;
        } catch (Exception e) {
            request.setAttribute("exception", e);
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

}
