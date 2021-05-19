package com.jyalla.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.net.URISyntaxException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClientException;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.messaging.MQUtil;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.modal.JwtResponse;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest extends BaseClass {


    private static final String HTTP_LOCALHOST_8080_REST_USER = "http://localhost:8080/rest/User/";

    static Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Autowired
    UserService userService;

    @Autowired
    MQUtil mqUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRestController userController;

    static UUID employeeId = UUID.fromString("7206d212-72a5-42ea-b472-3734c574bc33");
    static String token;

    @Test
    @Order(1)
    void getToken() {

        JwtRequest req = new JwtRequest("john", "john");
        ResponseEntity<JwtResponse> entity = template.postForEntity("http://localhost:8080/authenticate", req, JwtResponse.class);
        token = entity.getBody()
                .getToken();
        logger.info(token);

        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());

    }

    @Test
    @Order(6)
    void getUsers() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_USER, HttpMethod.GET, entity, String.class);

        // ResponseEntity<String> entityRes = template.getForEntity(HTTP_LOCALHOST_8080_REST_USER,
        // String.class);
        logger.info("getUsers() is {}", exchange);

        assertTrue(exchange.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(3)
    void getSingleUser() {
        User user = userService.findByEmail("controllertest@test.com")
                .get(0);
        employeeId = user.getId();
        logger.info("employeeId is {}", employeeId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<User> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER + employeeId.toString(), HttpMethod.GET, entity, User.class);
        logger.info("getSingleUser() is {}", entityRes.toString());
        // ResponseEntity<User> entity = template.getForEntity(HTTP_LOCALHOST_8080_REST_USER +
        // employeeId.toString(), User.class);
        assertTrue(entityRes.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    void postUser() throws RestClientException, URISyntaxException {
        User user = new User();
        user.setEmail("controllertest@test.com");
        user.setUsername("controllertest");
        user.setStatus(false);
        user.setProfilePic("");
        user.setPhoneNo("6752398751");
        user.setPassword("controllertest");
        user.setRole(2);
        logger.info("Inside postUser()");
        logger.info("user is {}", user);
        // when(userCon.saveUser(user)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // template.getRestTemplate() //.setMessageConverters(List.of(new
        // MappingJackson2HttpMessageConverter()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<User> entity = new HttpEntity(user, headers);

        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("$2a$04$YvsRDtfCJqMt/Ahxpjt9uuwZDT/dtAFpr51kX5hM/HeK2b8CEGXDW");

        ResponseEntity<User> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER, HttpMethod.POST, entity, User.class);
        logger.info("postUser() is {}", entityRes);
        // ResponseEntity<String> entity = this.template.postForEntity(new
        // URI(HTTP_LOCALHOST_8080_REST_USER), user, String.class);
        assertTrue(entityRes.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    void putUser() {
        User user = new User();
        user.setId(employeeId);
        user.setPhoneNo("8643");
        user.setEmail("controllertest@test.com");
        user.setUsername("controllertest");
        // when(userCon.updateUser(user, employeeId)).thenReturn(new
        // ResponseEntity<>(HttpStatus.OK));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(user, headers);

        ResponseEntity<String> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER + employeeId.toString(), HttpMethod.PUT, entity, String.class);
        logger.info("putUser() is {}", entityRes);
        assertTrue(entityRes.getStatusCode()
                .is2xxSuccessful());
        // template.put(HTTP_LOCALHOST_8080_REST_USER + employeeId.toString(), user);

    }

    @Test
    @Order(5)
    void deleteUser() throws RestClientException, URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER + employeeId.toString(), HttpMethod.DELETE, entity, String.class);
        logger.info("deleteUser() is {}", entityRes);
        assertTrue(entityRes.getStatusCode()
                .is2xxSuccessful());
        // this.template.delete(new URI(HTTP_LOCALHOST_8080_REST_USER + employeeId.toString()));
    }

    @Test
    @Order(7)
    void getUserNegative() {
        UUID dummyUUID = UUID.fromString("0095e3a3-eef9-4bd2-9744-da52d6dc3e68");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<User> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER + dummyUUID.toString(), HttpMethod.GET, entity, User.class);
        logger.info("getSingleUser() is {}", entityRes.toString());
        // ResponseEntity<User> entity = template.getForEntity(HTTP_LOCALHOST_8080_REST_USER +
        // employeeId.toString(), User.class);
        assertEquals(entityRes.getStatusCode()
                .value(), 404);
    }

}
