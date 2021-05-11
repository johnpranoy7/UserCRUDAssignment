package com.jyalla.demo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;


@TestMethodOrder(OrderAnnotation.class)
class JwtUtilTest extends BaseClass {

    @InjectMocks
    JwtUtil jwtUtil;

    @Mock
    UserService userService;

    public static Logger logger = LoggerFactory.getLogger(JwtUtilTest.class);
    static String token;
    private String USER_NAME = "john";

    @Test
    @Order(1)
    void getStringTokenUtil() {
        jwtUtil.setSecret("evoke");
        jwtUtil.setMaxExpiry(500000000);
        JwtRequest req = new JwtRequest(USER_NAME, USER_NAME);
        UserDetails user = new User(req.getUsername(), req.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        when(userService.findByUsername(Mockito.anyString())).thenReturn(new com.jyalla.demo.modal.User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser",
                "dummy@email.com", "1234", "", true, "Admin", new Date(), "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2));
        token = jwtUtil.generateToken(user);
        logger.info(token);
        assertNotNull(token);
    }

    @Test
    @Order(2)
    void findUserByToken() {
        jwtUtil.setSecret("evoke");
        logger.info("findUserByToken() token {}", token);
        String user = jwtUtil.findUserByToken(token);
        assertEquals(USER_NAME, user);
    }

    @Test
    @Order(3)
    void findRolesByToken() {
        jwtUtil.setSecret("evoke");
        logger.info("findUserByToken() token {}", token);
        List<GrantedAuthority> user = jwtUtil.findRolesByToken(token);
        assertEquals(1, user.size());
    }

    @Test
    @Order(4)
    void validateTokenPositive() throws Exception {
        jwtUtil.setSecret("evoke");
        boolean validateToken = jwtUtil.validateToken(token);
        assertTrue(validateToken);
    }

    @Test()
    @Order(4)
    void validateTokenNegative() throws Exception {

        jwtUtil.setSecret("evoke");
        jwtUtil.setMaxExpiry(0);
        JwtRequest req = new JwtRequest(USER_NAME, USER_NAME);
        UserDetails user = new User(req.getUsername(), req.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        when(userService.findByUsername(Mockito.anyString())).thenReturn(new com.jyalla.demo.modal.User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser",
                "dummy@email.com", "1234", "", true, "Admin", new Date(), "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2));
        String currentToken = jwtUtil.generateToken(user);
        logger.info("validateTokenNegative() token is {}", currentToken);
        Thread.sleep(3000);
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(currentToken));
    }



}
