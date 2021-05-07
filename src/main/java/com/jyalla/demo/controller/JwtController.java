package com.jyalla.demo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jyalla.demo.config.CustomUserDetails;
import com.jyalla.demo.exception.ErrorDTO;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.modal.JwtResponse;
import com.jyalla.demo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
public class JwtController {

    private static Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetails userDetails;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest request) {

        try {
            logger.info("Inside authenticateUser: {}", request);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            logger.info("authenticate: {}", authenticate);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorDTO("BAD_CREDENTIALS", List.of(e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(new ErrorDTO("TOKEN EXPIRED", List.of(e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        UserDetails user = userDetails.loadUserByUsername(request.getUsername());
        logger.info("user: {}", user);
        String token = jwtUtil.generateToken(user);
        logger.info("token: {}", token);

        return new ResponseEntity<>(new JwtResponse(token), new HttpHeaders(), HttpStatus.CREATED);
    }

}
