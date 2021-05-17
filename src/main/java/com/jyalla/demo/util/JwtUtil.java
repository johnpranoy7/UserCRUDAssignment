package com.jyalla.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Service
public class JwtUtil {

    @Autowired
    UserService userService;

    private String secret;
    private int maxExpiry;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.maxExpiry}")
    public void setMaxExpiry(int maxExpiry) {
        this.maxExpiry = maxExpiry;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("isAdmin", authorities.contains(new SimpleGrantedAuthority("ADMIN")));
        claims.put("isUser", authorities.contains(new SimpleGrantedAuthority("USER")));
        return doGenerateToken(userDetails, claims);
    }

    public String doGenerateToken(UserDetails userDetails, Map<String, Object> claims) {

        User user = userService.findByUsername(userDetails.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + maxExpiry))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new BadCredentialsException("INVALID Credentials", e);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token is Expired " + e.getLocalizedMessage(), e);
        }
    }

    public String findUserByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<GrantedAuthority> findRolesByToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        List<GrantedAuthority> roles = new ArrayList<>();
        Boolean isAdmin = body.get("isAdmin", Boolean.class);
        Boolean isUser = body.get("isUser", Boolean.class);
        if (isAdmin == true && isUser != null)
            roles.add(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin != null && isUser == true)
            roles.add(new SimpleGrantedAuthority("USER"));
        return roles;
    }

}
