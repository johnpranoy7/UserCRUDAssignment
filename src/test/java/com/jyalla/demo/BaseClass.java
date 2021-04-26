package com.jyalla.demo;


import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @ContextConfiguration(classes = {EmployeeCrudApplication.class})
public class BaseClass {
    @Autowired
    TestRestTemplate template;

    @Test
    public void GetUsers() {
        ResponseEntity<String> entity = template.getForEntity("http://localhost:8080/rest/User", String.class);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    public void GetAUser() {
        ResponseEntity<String> entity = template.getForEntity("http://localhost:8080/rest/User", String.class);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }
}
