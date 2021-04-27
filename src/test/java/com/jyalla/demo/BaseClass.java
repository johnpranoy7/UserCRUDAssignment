package com.jyalla.demo;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @ContextConfiguration(classes = {EmployeeCrudApplication.class})
public class BaseClass {

}
