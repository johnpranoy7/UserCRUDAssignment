package com.jyalla.demo;

import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest extends BaseClass {

    public static Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Autowired
    UserService userService;

    static UUID employeeId;

    @Test
    @Order(1)
    public void getUsers() {
        ResponseEntity<String> entity = template.getForEntity("http://localhost:8080/rest/User", String.class);
        template.getRestTemplate()
                .setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(3)
    public void getSingleUser() {
        User user = userService.findByEmail("servicetest@test.com")
                .get(0);
        employeeId = user.getId();
        logger.info("employeeId is" + employeeId.toString());

        ResponseEntity<User> entity = template.getForEntity("http://localhost:8080/rest/User/" + employeeId.toString(), User.class);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void postUser() throws RestClientException, URISyntaxException {
        User user = new User();
        user.setEmail("servicetest@test.com");
        user.setUsername("hello");
        user.setPhoneNo("6752398751");
        logger.info("Inside postUser(), employeeId is" + employeeId);
        logger.info("user is" + user);
        // template.getRestTemplate()
        // .setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
        ResponseEntity<String> entity = this.template.postForEntity(new URI("http://localhost:8080/rest/User"), user, String.class);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    public void putUser() {
        User user = new User();
        user.setPhoneNo("8643");
        template.put("http://localhost:8080/rest/User/" + employeeId.toString(), user);
    }

    @Test
    @Order(5)
    public void deleteUser() throws RestClientException, URISyntaxException {
        User user = new User();
        template.delete(new URI("http://localhost:8080/rest/User/" + employeeId.toString()));
    }
}
