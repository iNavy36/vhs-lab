package com.example.lab;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = LabApplication.class)
@AutoConfigureMockMvc
class RestControllersTests {
	private static final Logger log = LoggerFactory.getLogger(VHSController.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetVhs() throws Exception {
		ResultActions result = mockMvc.perform(get("/api/vhs"));
		MvcResult mvcResult = result.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].title").value("Back To The Future 2"))
				.andExpect(jsonPath("$[0].rent").value(3.0f))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].title").value("Back To The Future 2"))
				.andExpect(jsonPath("$[1].rent").value(3.0f))
				.andExpect(jsonPath("$[2].id", is(3)))
				.andExpect(jsonPath("$[2].title").value("The Lion King"))
				.andExpect(jsonPath("$[2].rent").value(5.0f))
				.andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[3].title").value("The Lion King"))
				.andExpect(jsonPath("$[3].rent").value(5.0f))
				.andExpect(jsonPath("$[4].id", is(5)))
				.andExpect(jsonPath("$[4].title").value("The Exterminator"))
				.andExpect(jsonPath("$[4].rent").value(8.0f))
				.andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
	}

}
