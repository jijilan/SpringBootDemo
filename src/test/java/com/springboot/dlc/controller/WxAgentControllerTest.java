package com.springboot.dlc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxAgentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void get() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/front/wx-agent/get/1").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                header("authorization","123456"));
        System.out.println(perform.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void find() {
    }

    @Test
    public void findAgentAndRole() {
    }
}