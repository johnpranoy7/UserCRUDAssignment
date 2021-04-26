package com.jyalla.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TestWebApp {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before(value = "")
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testEmployee() throws Exception {
        mockMvc.perform(get("/rest/employee"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("emp1"))
                .andExpect(jsonPath("$.designation").value("manager"))
                .andExpect(jsonPath("$.empId").value("1"))
                .andExpect(jsonPath("$.salary").value(3000));

    }

}
