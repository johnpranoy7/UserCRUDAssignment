package com.jyalla.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.config.CustomUserDetails;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.modal.JwtResponse;
import com.jyalla.demo.util.JwtUtil;


@TestMethodOrder(OrderAnnotation.class)
class JwtControllerTest extends BaseClass {

    @Autowired
    TestRestTemplate template;

    @InjectMocks
    private JwtController jwtController;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    CustomUserDetails userDetails;

    @Mock
    JwtUtil jwtUtil;

    public static Logger logger = LoggerFactory.getLogger(JwtControllerTest.class);
    static String token;

    @Test
    @Order(1)
    void getTokenController() {
        JwtRequest req = new JwtRequest("john", "john");
        ResponseEntity<JwtResponse> entity = template.postForEntity("http://localhost:8080/authenticate", req, JwtResponse.class);
        token = entity.getBody()
                .getToken();
        logger.info(token);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    void getTokenControllerMock() {
        JwtRequest req = new JwtRequest("john", "john");
        UserDetails user = new User(req.getUsername(), req.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        when(userDetails.loadUserByUsername(Mockito.anyString())).thenReturn(user);
        when(jwtUtil.generateToken(Mockito.any())).thenReturn("token");
        ResponseEntity<?> authenticateUser = jwtController.authenticateUser(req);
        logger.info("authenticateUser is {}", authenticateUser);
        assertEquals(true, authenticateUser.getStatusCode()
                .is2xxSuccessful());
    }

}
